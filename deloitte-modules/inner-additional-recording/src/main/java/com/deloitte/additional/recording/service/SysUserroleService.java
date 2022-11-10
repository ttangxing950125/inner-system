package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.SysUserRole;

/**
 * (SysUserrole)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:13
 */
public interface SysUserroleService extends IService<SysUserRole> {

    /**
     * 批量插入
     * @param userId id
     * @param roleIds 角色ids
     */
    boolean insertList(Integer userId, Integer[] roleIds);
}
