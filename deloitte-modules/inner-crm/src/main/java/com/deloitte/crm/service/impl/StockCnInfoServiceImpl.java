package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.mapper.StockCnInfoMapper;
import com.deloitte.crm.domain.StockCnInfo;
import com.deloitte.crm.service.StockCnInfoService;
import org.springframework.stereotype.Service;

/**
 * a股信息表，大陆股票(StockCnInfo)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:28:35
 */
@Service("stockCnInfoService")
public class StockCnInfoServiceImpl extends ServiceImpl<StockCnInfoMapper, StockCnInfo> implements StockCnInfoService {

}
