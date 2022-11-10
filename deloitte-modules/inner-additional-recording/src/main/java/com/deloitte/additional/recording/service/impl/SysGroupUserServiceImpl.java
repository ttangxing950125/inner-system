package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.SysGroupUser;
import com.deloitte.additional.recording.mapper.SysGroupUserMapper;
import com.deloitte.additional.recording.service.SysGroupUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * (SysGroupUser)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:31
 */
@Service("sysGroupUserService")
public class SysGroupUserServiceImpl extends ServiceImpl<SysGroupUserMapper, SysGroupUser> implements SysGroupUserService {

    @Override
    public boolean insertList(Integer userId, Integer[] groupIds) {

        List<SysGroupUser> list = new ArrayList<>();

        for (Integer groupId : groupIds) {
            list.add(new SysGroupUser().init(userId, groupId));
        }
        return saveBatch(list);
    }

    @Override
    public boolean insertList(Integer[] userIds, Integer groupId) {
        List<SysGroupUser> list = new ArrayList<>();

        for (Integer userId : userIds) {
            list.add(new SysGroupUser().init(userId, groupId));
        }
        return saveBatch(list);
    }

    @Override
    public List<SysGroupUser> findByGroupId(Integer groupId) {

        return lambdaQuery().eq(SysGroupUser::getGroupId, groupId).list();
    }
}
