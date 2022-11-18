package com.deloitte.additional.recording.application.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.SysGroup;
import com.deloitte.additional.recording.domain.SysGroupUser;
import com.deloitte.additional.recording.domain.SysUser;
import com.deloitte.additional.recording.domain.SysUserRole;
import com.deloitte.additional.recording.service.*;
import com.deloitte.additional.recording.vo.group.SysGroupInfoVo;
import com.deloitte.additional.recording.vo.user.SysUserVO;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.core.utils.bean.BeanUtils;
import com.deloitte.common.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.deloitte.additional.recording.util.StrListUtil.srtToIntArray;
import static com.deloitte.common.core.constant.Constants.DEFAULT_SEPARATOR;

/**
 * @创建人 tangx
 * @创建时间 2022/11/10
 * @描述 group user role 公用service
 */
@Service
@Slf4j
public class SysGroupUserRoleServiceImpl implements SysGroupUserRoleService {

    @Resource(name = "sysUserService")
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
    public void addSave(String name, String email, String sex, String status, String groupid, String roles, Date validTime) {
        //验证用户是否存在 根据邮箱验证
        SysUser user = userService.getByEmail(email);
        if (user != null) {
            log.info("邮箱已经存在，请重试。[email:{}]", email);
            throw new ServiceException("邮箱已经存在，请重试", HttpStatus.FOUND.value());
        }
        //初始化用户
        SysUser init = new SysUser().init(name, email, sex, validTime, SecurityUtils.getUserId() == null ? null : Integer.valueOf(SecurityUtils.getUserId().toString()));
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
    public void modify(SysUser user, String groupid, String roles) {
        Integer userId = user.getId();
        SysUser sysUser = userService.getById(userId);
        if (sysUser == null) {
            log.info("更新用户信息失败，用户不存在。[userId:{}]", userId);
            throw new ServiceException("更新用户信息失败，用户不存在", HttpStatus.NOT_FOUND.value());
        }
        //更新用户信息
        userService.updateById(user);
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
        List<SysUserRole> userRoles = userRoleService.lambdaQuery().in(roleIds!=null,SysUserRole::getRoleId, roleIds).list();
        Set<Integer> userIds = new HashSet<>();
        //将userIds添加到set中
        userIds.addAll(userRoles.stream().map(SysUserRole::getUserId).collect(Collectors.toSet()));
        //查询用户分组
        List<SysGroupUser> groupUsers = groupUserService.lambdaQuery().in(groupIds!=null,SysGroupUser::getGroupId, groupIds).list();
        userIds.addAll(groupUsers.stream().map(SysGroupUser::getUserId).collect(Collectors.toSet()));
        Page<SysUser> userPage = new Page<>(page, pagesize);
        userPage = userService.selectPage(name, nickname, status, userIds, userPage);
        //转为响应体
        Page<SysUserVO> userVOPage = new Page<>(page, pagesize);
        userVOPage.setTotal(userPage.getTotal());
        userVOPage.setRecords(BeanUtils.copy(userPage.getRecords(), SysUserVO.class));
        return userVOPage;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void addGroup(String groupName, Integer groupLeader, String groupMember) {
        //分组名称校验
        SysGroup sysGroup = groupService.getByName(groupName);
        if (sysGroup != null) {
            throw new ServiceException("小组名称已经存在，请重试");
        }
        //初始化
        SysGroup group = new SysGroup().createBy(groupName, groupLeader);
        groupService.save(group);
        Integer groupId = group.getId();
        //保存组员信息
        Integer[] userIds = srtToIntArray(groupMember);
        //保存
        groupUserService.insertList(userIds, groupId);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void updateGroup(Integer groupId, String groupName, Integer groupLeader, String groupMember) {

        SysGroup group = groupService.getById(groupId);
        if (group == null) {
            throw new ServiceException("小组信息不存在", HttpStatus.NOT_FOUND.value());
        }
        //验证分组名称
        SysGroup sysGroup = groupService.getByName(groupName);
        if (sysGroup != null && !group.getId().equals(sysGroup.getId())) {
            throw new ServiceException("小组名称已经存在，请重试", HttpStatus.FOUND.value());
        }
        group.setGroupLeader(groupLeader);
        group.setGroupName(groupName);
        groupService.updateById(group);
        //先删除 再插入
        Map<String, Object> deleteMap = new HashMap<>();
        deleteMap.put("group_id", groupId);
        groupUserService.removeByMap(deleteMap);
        //批量插入
        //保存组员信息
        Integer[] userIds = srtToIntArray(groupMember);
        //保存
        groupUserService.insertList(userIds, groupId);
    }

    @Override
    public SysGroupInfoVo getGroupInfo(Integer groupId) {

        SysGroup group = groupService.getById(groupId);
        if (group == null) {
            throw new ServiceException("小组信息查询失败，请重试", HttpStatus.NOT_FOUND.value());
        }
        SysGroupInfoVo groupInfoVo = BeanUtils.copyEntity(group, SysGroupInfoVo.class);
        groupInfoVo.setLeaderId(group.getGroupLeader());
        //查询组员
        List<SysGroupUser> groupUsers = groupUserService.findByGroupId(groupId);
        if (groupUsers != null) {
            List<Integer> userIds = groupUsers.stream().map(SysGroupUser::getUserId).collect(Collectors.toList());
            String groupUser = StrUtil.listToString(userIds, DEFAULT_SEPARATOR);
            groupInfoVo.setGrouMember(groupUser);
        }
        return groupInfoVo;
    }


}
