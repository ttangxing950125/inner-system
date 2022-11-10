package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.SysUserRole;
import com.deloitte.additional.recording.mapper.SysUserroleMapper;
import com.deloitte.additional.recording.service.SysUserroleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * (SysUserrole)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:23
 */
@Service("sysUserroleService")
public class SysUserroleServiceImpl extends ServiceImpl<SysUserroleMapper, SysUserRole> implements SysUserroleService {

    @Override
    public boolean insertList(Integer userId, Integer[] roleIds) {

        List<SysUserRole> list = new ArrayList<>();

        for (Integer roleId : roleIds) {
            list.add(new SysUserRole().init(userId, roleId));
        }
        return saveBatch(list);
    }
}
