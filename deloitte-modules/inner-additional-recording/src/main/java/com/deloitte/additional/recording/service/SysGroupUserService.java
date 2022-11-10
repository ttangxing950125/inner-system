package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.SysGroupUser;

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

}
