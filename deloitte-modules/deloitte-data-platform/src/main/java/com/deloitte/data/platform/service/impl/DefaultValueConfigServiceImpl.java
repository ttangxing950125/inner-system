package com.deloitte.data.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.datasource.annotation.Master_1;
import com.deloitte.data.platform.domian.DefaultValueConfig;
import com.deloitte.data.platform.mapper.DefaultValueConfigMapper;
import com.deloitte.data.platform.service.IDefaultValueConfigService;
import org.springframework.stereotype.Service;

/**
 * 科目默认值配置表  服务实现类
 *
 * @author fangliu
 * @date 2022/11/10 17:40:39
 */
@Service
@Master_1
public class DefaultValueConfigServiceImpl extends ServiceImpl<DefaultValueConfigMapper, DefaultValueConfig> implements IDefaultValueConfigService {

}
