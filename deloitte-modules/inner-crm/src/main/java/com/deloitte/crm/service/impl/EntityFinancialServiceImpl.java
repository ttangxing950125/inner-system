package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.domain.EntityFinancial;
import com.deloitte.crm.mapper.EntityFinancialMapper;
import com.deloitte.crm.service.EntityFinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (EntityFinancial)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-11 17:11:57
 */
@Service("entityFinancialService")
public class EntityFinancialServiceImpl extends ServiceImpl<EntityFinancialMapper, EntityFinancial> implements EntityFinancialService {

    @Autowired
    private EntityFinancialMapper financialMapper;


    /**
     * 金融机构根据entityCode补充录入副表信息
     *
     * @param entityFinancial
     * @return R
     * @author 冉浩岑
     * @date 2022/10/12 9:10
     */
    @Override
    public void addFinEntitySubtableMsg(EntityFinancial entityFinancial) {
        QueryWrapper<EntityFinancial> financialQuery = new QueryWrapper<>();
        int update = financialMapper.update(entityFinancial, financialQuery.lambda().eq(EntityFinancial::getEntityCode, entityFinancial.getEntityCode()));
        if (update<1){
            financialMapper.insert(entityFinancial);
        }
    }
}
