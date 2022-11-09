package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.SysMenu;
import com.deloitte.additional.recording.domain.SysUser;

import java.util.List;

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
}
