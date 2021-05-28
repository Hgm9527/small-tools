package com.zhengqing.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.enums.IsValidEnum;
import com.zhengqing.demo.entity.Demo;
import com.zhengqing.demo.mapper.DemoMapper;
import com.zhengqing.demo.model.dto.DemoListDTO;
import com.zhengqing.demo.model.dto.DemoSaveDTO;
import com.zhengqing.demo.model.vo.DemoListVO;
import com.zhengqing.demo.service.IDemoService;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 测试demo 服务实现类
 * </p>
 *
 * @author: zhengqing
 * @description:
 * @date: 2021/01/13 10:11
 */
@Slf4j
@Service
// @Transactional(rollbackFor = Exception.class)
public class DemoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements IDemoService {

    @Autowired
    private DemoMapper demoMapper;

    // @Transactional(rollbackFor = Exception.class)
    @Transactional
    @Override
    public void testTransactional() {
        Demo demo = Demo.builder().username("admin").password("123456").build();
        demo.insert();
        Integer id = demo.getId();
        log.debug("主键id： 【{}】", id);

        log.debug("可能异常启动...");
        this.handleData();
        log.debug("无异常...");
    }

    private void handleData() {
        int a = 1 / 0;
    }

    @Override
    public IPage<DemoListVO> listPage(DemoListDTO params) {
        IPage<DemoListVO> result = demoMapper.selectDataList(new Page<>(), params);
        List<DemoListVO> list = result.getRecords();
        this.handleResultData(list);
        return result;
    }

    @Override
    public List<DemoListVO> list(DemoListDTO params) {
        List<DemoListVO> list = demoMapper.selectDataList(params);
        this.handleResultData(list);
        return list;
    }

    /**
     * 处理数据
     *
     * @param list: 数据
     * @return: void
     * @author : zhengqing
     * @date : 2021/01/13 10:11
     */
    private void handleResultData(List<DemoListVO> list) {

    }

    @Override
    public Integer addOrUpdateData(DemoSaveDTO params) {
        Integer id = params.getId();
        String username = params.getUsername();
        String password = params.getPassword();

        Demo demo = new Demo();
        demo.setId(id);
        demo.setUsername(username);
        demo.setPassword(password);

        if (id == null) {
            demo.insert();
        } else {
            demo.updateById();
        }
        return demo.getId();
    }

    @Override
    public void addBatchData() {
        // 测试插入数据量
        int addSum = 1000000;
        LocalDateTime saveBeforeDateTime = LocalDateTime.now();
        this.insertData03(addSum);
        LocalDateTime saveAfterDateTime = LocalDateTime.now();
        Duration duration = Duration.between(saveBeforeDateTime, saveAfterDateTime);
        log.info("测试插入100w数据用时 :【{} s】", duration.toMillis());
    }

    /**
     * 方式一：for循环中单条插入 1000条数据耗时:8s
     */
    private void insertData01(int addSum) {
        for (int i = 0; i < addSum; i++) {
            Demo.builder().username("insertData01 - " + i).password("123456").build().insert();
        }
    }

    /**
     * 方式二：mybatis api 批量插入 1000条数据耗时:5s
     */
    private void insertData02(int addSum) {
        List<Demo> demoList = Lists.newLinkedList();
        for (int i = 0; i < addSum; i++) {
            demoList.add(Demo.builder().username("insertData02 - " + i).password("123456").build());
        }
        this.saveBatch(demoList);
    }

    /**
     * 方式三：手写sql 批量插入 1000条数据耗时:1s
     */
    private void insertData03(int addSum) {
        List<Demo> demoList = Lists.newLinkedList();
        Date now = new Date();
        for (int i = 0; i < addSum; i++) {
            Demo item = new Demo();
            item.setUsername("insertData03 - " + i);
            item.setPassword("123456");
            item.setCreateBy(1);
            item.setCreateTime(now);
            item.setUpdateBy(1);
            item.setUpdateTime(now);
            item.setIsValid(IsValidEnum.有效.getValue());
            demoList.add(item);
        }
        demoMapper.insertBatch(demoList);
    }

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 方式四：批处理 1000条数据耗时:5s
     */
    private void insertData04(int addSum) {
        List<Demo> demoList = Lists.newLinkedList();
        Date now = new Date();
        for (int i = 0; i < addSum; i++) {
            Demo item = new Demo();
            item.setUsername("insertData04 - " + i);
            item.setPassword("123456");
            item.setCreateBy(1);
            item.setCreateTime(now);
            item.setUpdateBy(1);
            item.setUpdateTime(now);
            item.setIsValid(IsValidEnum.有效.getValue());
            demoList.add(item);
        }
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        DemoMapper demoMapperNew = sqlSession.getMapper(DemoMapper.class);
        demoList.stream().forEach(demoMapperNew::insert);
        sqlSession.commit();
        sqlSession.clearCache();
    }

}
