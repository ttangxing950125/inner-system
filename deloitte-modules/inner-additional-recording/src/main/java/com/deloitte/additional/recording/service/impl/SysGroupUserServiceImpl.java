package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.mapper.SysGroupUserMapper;
import com.deloitte.additional.recording.domain.SysGroupUser;
import com.deloitte.additional.recording.service.SysGroupUserService;
import org.springframework.stereotype.Service;

/**
 * (SysGroupUser)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:31
 */
@Service("sysGroupUserService")
public class SysGroupUserServiceImpl extends ServiceImpl<SysGroupUserMapper, SysGroupUser> implements SysGroupUserService {

}
