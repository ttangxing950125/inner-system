package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.SysGroup;

import java.util.List;

/**
 * (SysGroup)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:29
 */
public interface SysGroupService extends IService<SysGroup> {

     List<SysGroup> getSysGroupList();


    /**
     * 根据名称校验
     * @param groupName 名称
     * @return
     */
    SysGroup getByName(String groupName);


}
