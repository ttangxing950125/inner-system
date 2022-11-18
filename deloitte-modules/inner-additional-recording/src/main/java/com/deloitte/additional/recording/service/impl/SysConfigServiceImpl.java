package com.deloitte.additional.recording.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.mapper.SysConfigMapper;
import com.deloitte.additional.recording.domain.SysConfig;
import com.deloitte.additional.recording.service.SysConfigService;
import org.springframework.stereotype.Service;

/**
 * (SysConfig)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:34
 */
@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

}
