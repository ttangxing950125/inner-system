package com.deloitte.additional.recording.application.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.vo.group.SysGroupInfoVo;
import com.deloitte.additional.recording.vo.user.SysUserVO;

/**
 * @创建人 tangx
 * @创建时间 2022/11/10
 * @描述 group user role 公用service
 */

public interface SysGroupUserRoleService {

    /**
     * 新增用户
     *
     * @param name    昵称
     * @param email   邮箱
     * @param sex     性别
     * @param status  状态
     * @param groupid 分组id ，隔开
     * @param roles   角色id ，隔开
     */
    void addSave(String name, String email, String sex, String status, String groupid, String roles);

    /**
     * 更新用户
     *
     * @param name    昵称
     * @param email   邮箱
     * @param sex     性别
     * @param status  状态
     * @param groupid 分组id ，隔开
     * @param roles   角色id ，隔开
     */
    void modify(Integer userId, String name, String email, String sex, String status, String groupid, String roles);

    /**
     * 分页条件查询用户列表
     *
     * @param name     登录名
     * @param nickname 昵称
     * @param status   状态
     * @param groups   分组id 用,隔开
     * @param role     角色id 用,隔开
     * @param page     当前页码
     * @param pagesize 页面数量大小
     * @return
     */
    Page<SysUserVO> page(String name, String nickname, String status, String groups, String role, Integer page, Integer pagesize);

    /**
     * 新增小组
     * @param groupName 小组名称
     * @param groupLeader 小组长 id
     * @param groupMember 组员id 用,隔开
     */
    void addGroup(String groupName, Integer groupLeader, String groupMember);

    /**
     * 更新分组信息
     * @param groupId 分组id
     * @param groupName 分组名称
     * @param groupLeader 组长id
     * @param groupMember 组员id 用,隔开
     */
    void updateGroup(Integer groupId, String groupName, Integer groupLeader, String groupMember);

    /**
     * 查询小组信息详情
     * @param groupId 小组id
     * @return
     */
    SysGroupInfoVo getGroupInfo(Integer groupId);
}
