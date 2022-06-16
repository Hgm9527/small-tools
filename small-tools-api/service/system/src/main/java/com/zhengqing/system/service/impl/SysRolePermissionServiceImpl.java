package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.system.entity.SysRolePermission;
import com.zhengqing.system.mapper.SysRolePermissionMapper;
import com.zhengqing.system.model.dto.SysRoleMenuBtnSaveDTO;
import com.zhengqing.system.model.vo.SysRoleMenuBtnListVO;
import com.zhengqing.system.service.ISysRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 系统管理-角色关联权限 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:31
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission>
        implements ISysRolePermissionService {

    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public List<SysRoleMenuBtnListVO> listRoleMenuBtn() {
        return this.sysRolePermissionMapper.selectRoleMenuBtns();
    }

    @Override
    public List<Integer> getPermissionBtnsByRoleIdAndMenuId(Integer roleId, Integer menuId) {
        return this.sysRolePermissionMapper.selectBtnsByRoleIdAndMenuId(roleId, menuId);
    }

    @Override
    public void deleteBtnsByRoleId(Integer roleId) {
        this.sysRolePermissionMapper.deleteBtnsByRoleId(roleId);
    }

    @Override
    public void deleteBtnsByRoleIdAndMenuId(Integer roleId, Integer menuId) {
        this.sysRolePermissionMapper.deleteBtnsByRoleIdAndMenuId(roleId, menuId);
    }

    @Override
    public void saveRoleMenuBtnIds(SysRoleMenuBtnSaveDTO params) {
        Integer roleId = params.getRoleId();
        Integer menuId = params.getMenuId();
        List<Integer> btnIdList = params.getBtnIdList();
        // ① 先删除
        this.deleteBtnsByRoleIdAndMenuId(roleId, menuId);
        // ② 再保存
        List<SysRolePermission> itemList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(btnIdList)) {
            btnIdList.forEach(btnId -> {
                SysRolePermission item = new SysRolePermission();
                item.setRoleId(roleId);
                item.setMenuId(menuId);
                item.setBtnId(btnId);
                itemList.add(item);
            });
            this.saveBatch(itemList);
        }
    }

}