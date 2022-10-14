package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.mapper.ImplementStInfoMapper;
import com.deloitte.crm.domain.ImplementStInfo;
import com.deloitte.crm.service.ImplementStInfoService;
import org.springframework.stereotype.Service;

/**
 * 实施ST(带帽)(ImplementStInfo)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-14 17:50:05
 */
@Service("implementStInfoService")
public class ImplementStInfoServiceImpl extends ServiceImpl<ImplementStInfoMapper, ImplementStInfo> implements ImplementStInfoService {

}
