package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.mapper.BondConvertibleInfoMapper;
import com.deloitte.crm.domain.BondConvertibleInfo;
import com.deloitte.crm.service.BondConvertibleInfoService;
import org.springframework.stereotype.Service;

/**
 * 可转债发行预案(BondConvertibleInfo)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-13 13:39:43
 */
@Service("bondConvertibleInfoService")
public class BondConvertibleInfoServiceImpl extends ServiceImpl<BondConvertibleInfoMapper, BondConvertibleInfo> implements BondConvertibleInfoService {

}
