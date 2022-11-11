package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.SysMenuRole;
import com.deloitte.additional.recording.domain.SysRole;
import com.deloitte.additional.recording.mapper.SysMenuRoleMapper;
import com.deloitte.additional.recording.service.SysMenuService;
import com.deloitte.additional.recording.service.SysMenuroleService;
import com.deloitte.additional.recording.service.SysRoleService;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.core.utils.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.deloitte.additional.recording.util.StrListUtil.srtToIntArray;

/**
 * (SysMenurole)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:25
 */
@Service("sysMenuroleService")
public class SysMenuroleServiceImpl extends ServiceImpl<SysMenuRoleMapper, SysMenuRole> implements SysMenuroleService {

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysMenuService menuService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addMenRole(String name, String status, String menunodes) {
        //新增角色 -验证角色名称
        SysRole role = roleService.getByName(name);
        if (role != null) {
            throw new ServiceException("角色名称已经存在，请重试", HttpStatus.FOUND.value());
        }
        role = new SysRole().createBy(name, status);
        roleService.save(role);
        //保存角色菜单关联信息
        //解析菜单id
        if (StrUtil.isNotBlank(menunodes)) {
            Integer roleId = role.getId();
            Integer[] menuIds = srtToIntArray(menunodes);
            List<SysMenuRole> list = new ArrayList<>();
            for (Integer menuId : menuIds) {
                list.add(new SysMenuRole().createBy(menuId, roleId));
            }
            //保存
            saveBatch(list);
        }
    }

    @Override
    public void roleMenuSetting(Integer id, String menunodes) {

        SysRole role = roleService.getById(id);
        if (role == null) {
            throw new ServiceException("权限设置失败,角色信息有误", HttpStatus.NOT_FOUND.value());
        }
        //解析字符
        if (StringUtils.isNotBlank(menunodes)) {
            //先删除原有的 再新增
            HashMap<String, Object> deleteMap = new HashMap<>();
            deleteMap.put("role_id", id);
            removeByMap(deleteMap);
            Integer[] menuIds = srtToIntArray(menunodes);
            List<SysMenuRole> list = new ArrayList<>();
            for (Integer menuId : menuIds) {
                list.add(new SysMenuRole().createBy(menuId, role.getId()));
            }
            //保存
            saveBatch(list);
        }
    }
}
