package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.SysGroupUser;

import java.util.List;

/**
 * (SysGroupUser)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:30
 */
public interface SysGroupUserService extends IService<SysGroupUser> {

    /**
     * 批量插入
     * @param userId 用户id
     * @param groupIds 分组ids
     * @return
     */
    boolean insertList(Integer userId, Integer[] groupIds);

    /**
     * 批量插入
     * @param userIds 用户集合
     * @param groupId 分组id
     */
    boolean insertList(Integer[] userIds, Integer groupId);

    /**
     * 通过分组id查询
     * @param groupId 分组id
     * @return
     */
    List<SysGroupUser> findByGroupId(Integer groupId);
}
