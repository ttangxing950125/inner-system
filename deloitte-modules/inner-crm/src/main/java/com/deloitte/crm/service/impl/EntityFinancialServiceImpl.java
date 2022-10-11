package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.mapper.EntityFinancialMapper;
import com.deloitte.crm.domain.EntityFinancial;
import com.deloitte.crm.service.EntityFinancialService;
import org.springframework.stereotype.Service;

/**
 * (EntityFinancial)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-11 17:11:57
 */
@Service("entityFinancialService")
public class EntityFinancialServiceImpl extends ServiceImpl<EntityFinancialMapper, EntityFinancial> implements EntityFinancialService {

}
