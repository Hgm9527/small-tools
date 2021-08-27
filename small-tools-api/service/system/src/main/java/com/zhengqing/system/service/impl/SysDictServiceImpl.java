package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.constant.MybatisConstant;
import com.zhengqing.common.context.ContextHandler;
import com.zhengqing.common.enums.YesNoEnum;
import com.zhengqing.common.util.RedisUtil;
import com.zhengqing.system.constant.SystemConstant;
import com.zhengqing.system.entity.SysDict;
import com.zhengqing.system.entity.SysDictType;
import com.zhengqing.system.mapper.SysDictMapper;
import com.zhengqing.system.model.dto.SysDictSaveBatchDTO;
import com.zhengqing.system.model.dto.SysDictSaveDTO;
import com.zhengqing.system.model.vo.SysDictTypeListVO;
import com.zhengqing.system.model.vo.SysDictVO;
import com.zhengqing.system.service.ISysDictService;
import com.zhengqing.system.service.ISysDictTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据字典-服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:51
 */
@Slf4j
@Service
@Validated
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;

    @Lazy
    @Autowired
    private ISysDictTypeService sysDictTypeService;

    @Override
    public List<SysDictVO> listByCode(String code) {
        return this.sysDictMapper.selectDictListByCode(null, Lists.newArrayList(code));
    }

    @Override
    public Map<String, List<SysDictVO>> listByOpenCode(List<String> codeList) {
        Map<String, List<SysDictVO>> dictDataMap = this.listFromCacheByCode(codeList);
        if (CollectionUtils.isEmpty(dictDataMap)) {
            // 如果缓存数据为空，则从db获取
            return this.listFromDbByOpenCode(codeList);
        }
        return dictDataMap;
    }

    @Override
    public Map<String, List<SysDictVO>> listFromDbByOpenCode(List<String> codeList) {
        Map<String, List<SysDictVO>> dictDataMap = Maps.newHashMap();
        List<SysDictVO> dictDataList = this.sysDictMapper.selectDictListByCode(YesNoEnum.是.getValue(), codeList);
        if (CollectionUtils.isEmpty(dictDataList)) {
            return dictDataMap;
        }
        for (SysDictVO dictItem : dictDataList) {
            dictDataMap.computeIfAbsent(dictItem.getCode(), k -> new LinkedList<>()).add(dictItem);
        }
        return dictDataMap;
    }

    @Override
    public Map<String, List<SysDictVO>> listFromCacheByCode(List<String> codeList) {
        Map<String, List<SysDictVO>> dictDataMap = Maps.newHashMap();
        codeList.forEach(codeItem -> {
            String dictListStr = RedisUtil.get(SystemConstant.CACHE_SYS_DICT_PREFIX + codeItem);
            if (StringUtils.isNotBlank(dictListStr)) {
                dictDataMap.put(codeItem, JSONArray.parseArray(dictListStr, SysDictVO.class));
            }
        });
        return dictDataMap;
    }

    @Override
    public SysDict detail(Integer dictId) {
        SysDict sysDict = this.sysDictMapper.selectById(dictId);
        Assert.notNull(sysDict, "字典不存在！");
        return sysDict;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrUpdateData(SysDictSaveDTO params) {
        String code = params.getCode();
        SysDictType dictTypeData = this.sysDictTypeService.detailByCode(code);
        Integer id = params.getId();
        String name = params.getName();
        String value = params.getValue();
        Integer sort = params.getSort();
        String remark = params.getRemark();

        // 校验名称是否重复
        SysDict sysDictOldByName = this.sysDictMapper.selectOne(new LambdaQueryWrapper<SysDict>()
                .eq(SysDict::getCode, code)
                .eq(SysDict::getName, name)
                .last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(sysDictOldByName == null || sysDictOldByName.getId().equals(id), "字典名称重复，请重新输入！");

        // 校验Value是否重复
        SysDict sysDictOldByValue = this.sysDictMapper.selectOne(new LambdaQueryWrapper<SysDict>()
                .eq(SysDict::getCode, code)
                .eq(SysDict::getValue, value)
                .last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(sysDictOldByValue == null || sysDictOldByValue.getId().equals(id), "字典名称值重复，请重新输入！");

        // 保存数据
        SysDict sysDict = SysDict.builder()
                .id(id)
                .dictTypeId(dictTypeData.getId())
                .code(code)
                .name(name)
                .value(value)
                .status(YesNoEnum.是.getValue())
                .sort(sort)
                .remark(remark)
                .build();
        if (params.getId() == null) {
            this.sysDictMapper.insert(sysDict);
        } else {
            // 校验该数据是否存在
            this.detail(id);
            this.sysDictMapper.updateById(sysDict);
        }
        // 更新缓存
        this.updateCache(code);
        return sysDict.getId();
    }

    @Override
    public void updateBatch(Map<String, List<SysDictSaveBatchDTO>> dictDataMap) {
        List<SysDictSaveBatchDTO> saveList = Lists.newArrayList();
        List<String> codeList = Lists.newLinkedList();
        dictDataMap.forEach((code, dictListItem) -> codeList.add(code));
        Map<String, Integer> dictTypeIdMap = this.sysDictTypeService.getDictTypeIdMap(codeList);
        dictDataMap.forEach((code, dictListItem) -> {
            Integer dictTypeId = dictTypeIdMap.get(code);
            Assert.notNull(dictTypeId, String.format("数据字典[%s]丢失，请联系系统管理员!", code));
            dictListItem.forEach(item -> {
                item.setDictTypeId(dictTypeId);
                item.setCode(code);
                item.setCurrentUserId(ContextHandler.getUserId());
            });
            saveList.addAll(dictListItem);
        });
        this.sysDictMapper.batchInsertOrUpdate(saveList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictById(Integer id) {
        SysDict sysDict = this.sysDictMapper.selectById(id);
        if (sysDict == null) {
            return;
        }
        this.sysDictMapper.deleteById(id);
        // 更新缓存
        this.updateCache(sysDict.getCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictByCode(String code) {
        this.sysDictMapper.deleteByCode(code);
    }

    @Override
    public void updateCache(String code) {
        if (StringUtils.isBlank(code)) {
            return;
        }
        String key = SystemConstant.CACHE_SYS_DICT_PREFIX + code;
        // 加入||更新 缓存
        if (RedisUtil.hasKey(key)) {
            RedisUtil.delete(key);
            log.info("数据字典[{}] 更新之前删除缓存" + key);
        }
        List<SysDictVO> dictList = this.listFromDbByOpenCode(Lists.newArrayList(code)).get(code);
        if (!CollectionUtils.isEmpty(dictList)) {
            RedisUtil.set(key, JSON.toJSONString(dictList));
            log.info("数据字典[{}] 加入缓存" + key);
        }
    }

    @Override
    public void initCache() {
        List<SysDictTypeListVO> sysDictTypeList = this.sysDictTypeService.listByOpen();
        if (!CollectionUtils.isEmpty(sysDictTypeList)) {
            List<String> codeList = sysDictTypeList.stream().map(SysDictTypeListVO::getCode).collect(Collectors.toList());
            Map<String, List<SysDictVO>> dictDataMap = this.listFromDbByOpenCode(codeList);
            dictDataMap.forEach((code, dictDataList) -> RedisUtil.set(SystemConstant.CACHE_SYS_DICT_PREFIX + code, JSON.toJSONString(dictDataList)));
        }
        log.info("初始化数据字典缓存成功!");
    }

}
