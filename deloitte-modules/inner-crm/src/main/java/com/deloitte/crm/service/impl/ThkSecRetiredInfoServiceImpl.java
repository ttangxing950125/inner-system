package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.mapper.ThkSecRetiredInfoMapper;
import com.deloitte.crm.domain.ThkSecRetiredInfo;
import com.deloitte.crm.service.ThkSecRetiredInfoService;
import org.springframework.stereotype.Service;

/**
 * 证券发行-港股-已退市证券一览(ThkSecRetiredInfo)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-01 11:45:42
 */
@Service("thkSecRetiredInfoService")
public class ThkSecRetiredInfoServiceImpl extends ServiceImpl<ThkSecRetiredInfoMapper, ThkSecRetiredInfo> implements ThkSecRetiredInfoService {

}
