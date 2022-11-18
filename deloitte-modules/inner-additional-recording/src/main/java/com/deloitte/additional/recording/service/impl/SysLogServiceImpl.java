package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.mapper.SysLogMapper;
import com.deloitte.additional.recording.domain.SysLog;
import com.deloitte.additional.recording.service.SysLogService;
import org.springframework.stereotype.Service;

/**
 * (SysLog)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:26
 */
@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

}
