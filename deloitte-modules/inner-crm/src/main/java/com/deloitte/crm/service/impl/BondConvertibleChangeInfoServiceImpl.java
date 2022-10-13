package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.mapper.BondConvertibleChangeInfoMapper;
import com.deloitte.crm.domain.BondConvertibleChangeInfo;
import com.deloitte.crm.service.BondConvertibleChangeInfoService;
import org.springframework.stereotype.Service;

/**
 * 可交换转债发行预案(BondConvertibleChangeInfo)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-13 13:39:43
 */
@Service("bondConvertibleChangeInfoService")
public class BondConvertibleChangeInfoServiceImpl extends ServiceImpl<BondConvertibleChangeInfoMapper, BondConvertibleChangeInfo> implements BondConvertibleChangeInfoService {

}
