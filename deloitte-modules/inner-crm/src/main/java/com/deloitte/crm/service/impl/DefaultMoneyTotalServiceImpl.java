package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.mapper.DefaultMoneyTotalMapper;
import com.deloitte.crm.domain.DefaultMoneyTotal;
import com.deloitte.crm.service.DefaultMoneyTotalService;
import org.springframework.stereotype.Service;

/**
 * (DefaultMoneyTotal)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-09 10:50:32
 */
@Service("defaultMoneyTotalService")
public class DefaultMoneyTotalServiceImpl extends ServiceImpl<DefaultMoneyTotalMapper, DefaultMoneyTotal> implements DefaultMoneyTotalService {

}
