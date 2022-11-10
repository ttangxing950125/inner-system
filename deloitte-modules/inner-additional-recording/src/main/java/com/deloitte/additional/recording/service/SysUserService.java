package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.SysMenu;
import com.deloitte.additional.recording.domain.SysUser;

import java.util.List;
import java.util.Set;

/**
 * (SysUser)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:27
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 登录
     * @param username
     * @param password
     * w吴鹏
     * @return
     */
    SysUser login(String username, String password);

    /**
     * 查询用户的菜单
     * 谁有空重写一下，之前的可能会空指针
     * @// TODO: 2022/11/10  唐星
     * 吴鹏鹏
     * @param u
     * @return
     */
    SysMenu findmenubyuser(SysUser u);

    /**
     * 查询角色列表
     * @param u
     * @return
     */
    List<String> getRoleList(SysUser u);


    /**
     * 根据Email查询永华
     * @param email 邮箱地址
     * @return SysUer
     */
    SysUser getByEmail(String email);

    /**
     * 重置密码
     * @param userId 用户id
     */
    void resetPassword(Integer userId);

    /**
     * 禁用用户账号
     * @param userId 用户id
     */
    void disableUser(Integer userId);

    /**
     * 条件分页查询用户列表
     * @param name 登录名
     * @param nickname 昵称
     * @param status 状态
     * @param userIds 用户ids
     * @param userPage 分页对象
     * @return
     */
    Page<SysUser> selectPage(String name, String nickname, String status, Set<Integer> userIds, Page<SysUser> userPage);
}
