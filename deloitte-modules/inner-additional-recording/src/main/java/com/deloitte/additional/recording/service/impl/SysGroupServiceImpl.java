package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.mapper.SysGroupMapper;
import com.deloitte.additional.recording.domain.SysGroup;
import com.deloitte.additional.recording.service.SysGroupService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (SysGroup)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:29
 */
@Service("sysGroupService")
public class SysGroupServiceImpl extends ServiceImpl<SysGroupMapper, SysGroup> implements SysGroupService {


    @Override
    public SysGroup getByName(String groupName) {


        return lambdaQuery().eq(SysGroup::getGroupName,groupName).one();
    }
}
