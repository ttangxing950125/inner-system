package com.deloitte.additional.recording.application.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.SysGroupUser;
import com.deloitte.additional.recording.domain.SysUser;
import com.deloitte.additional.recording.domain.SysUserRole;
import com.deloitte.additional.recording.service.*;
import com.deloitte.additional.recording.vo.user.SysUserVO;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.core.utils.bean.BeanUtils;
import com.deloitte.common.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @创建人 tangx
 * @创建时间 2022/11/10
 * @描述 group user role 公用service
 */
@Service
@Slf4j
public class SysGroupUserRoleServiceImpl implements SysGroupUserRoleService {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysGroupService groupService;

    @Autowired
    private SysGroupUserService groupUserService;

    @Autowired
    private SysUserroleService userRoleService;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void addSave(String name, String email, String sex, String status, String groupid, String roles) {
        //验证用户是否存在 根据邮箱验证
        SysUser user = userService.getByEmail(email);
        if (user != null) {
            log.info("邮箱已经存在，请重试。[email:{}]", email);
            throw new ServiceException("邮箱已经存在，请重试", HttpStatus.FOUND.value());
        }
        //初始化用户
        SysUser init = new SysUser().init(name, email, sex, SecurityUtils.getUserId() == null ? null : Integer.valueOf(SecurityUtils.getUserId().toString()));
        userService.save(init);
        Integer userId = init.getId();
        //绑定角色信息  可对应多个角色
        //批量插入
        boolean userRoleSaveBoo = userRoleService.insertList(userId, srtToIntArray(roles));
        boolean groupUserSaveBoo = groupUserService.insertList(userId, srtToIntArray(groupid));
        if (!(userRoleSaveBoo && groupUserSaveBoo)) {
            throw new ServiceException("新增用户失败，请重试");
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void modify(Integer userId, String name, String email, String sex, String status, String groupid, String roles) {
        SysUser sysUser = userService.getById(userId);
        if (sysUser == null) {
            log.info("更新用户信息失败，用户不存在。[userId:{}]", userId);
            throw new ServiceException("更新用户信息失败，用户不存在", HttpStatus.NOT_FOUND.value());
        }
        //绑定角色信息    可对应多个角色 先删除用户-角色绑定信息
        Map<String, Object> deleteMap = new HashMap<>();
        deleteMap.put("user_id", userId);
        userRoleService.removeByMap(deleteMap);
        //再更新
        userRoleService.insertList(userId, srtToIntArray(roles));
        //批量删除用户分组关联信息
        groupUserService.removeByMap(deleteMap);
        groupUserService.insertList(userId, srtToIntArray(groupid));
    }

    @Override
    public Page<SysUserVO> page(String name, String nickname, String status, String groups, String role, Integer page, Integer pagesize) {
        //解析字符串得到ids
        Integer[] roleIds = srtToIntArray(role);
        Integer[] groupIds = srtToIntArray(groups);
        //查询用户id
        List<SysUserRole> userRoles = userRoleService.lambdaQuery().in(SysUserRole::getRoleId, roleIds).list();
        Set<Integer> userIds = new HashSet<>();
        //将userIds添加到set中
        userIds.addAll(userRoles.stream().map(SysUserRole::getUserId).collect(Collectors.toSet()));
        //查询用户分组
        List<SysGroupUser> groupUsers = groupUserService.lambdaQuery().in(SysGroupUser::getGroupId, groupIds).list();
        userIds.addAll(groupUsers.stream().map(SysGroupUser::getUserId).collect(Collectors.toSet()));
        Page<SysUser> userPage = new Page<>(page, pagesize);

        userPage = userService.selectPage(name, nickname, status, userIds, userPage);

        //转为响应体
        Page<SysUserVO> userVOPage = new Page<>(page, pagesize);
        userVOPage.setTotal(userPage.getTotal());
        userVOPage.setRecords(BeanUtils.copy(userPage.getRecords(), SysUserVO.class));
        return userVOPage;
    }


    private Integer[] srtToIntArray(String roles) {
        try {
            String[] roleIds = roles.split(",");
            return (Integer[]) ConvertUtils.convert(roleIds, Integer.class);
        } catch (Exception e) {
            log.info("字符串转Int数组失败 [异常信息：{}]", e.getMessage());
            throw new ServiceException("字符串不符合转换格式");
        }

    }


}
