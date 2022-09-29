package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.domain.EntityAttrIntype;
import com.deloitte.crm.mapper.EntityAttrIntypeMapper;
import com.deloitte.crm.service.EntityAttrIntypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 冉浩岑
 * @date 2022/09/29 17:51
 */
@Service
public class EntityAttrIntypeServiceImpl extends ServiceImpl<EntityAttrIntypeMapper, EntityAttrIntype> implements EntityAttrIntypeService {

    @Autowired
    private EntityAttrIntypeMapper entityAttrIntypeMapper;

    @Override
    public List<EntityAttrIntype> getTypeByAttrId(Integer attrId) {
        QueryWrapper<EntityAttrIntype> intypeQuery = new QueryWrapper<>();
        List<EntityAttrIntype> attrIntypes = entityAttrIntypeMapper.selectList(intypeQuery.lambda().eq(EntityAttrIntype::getAttrId, attrId));
        return attrIntypes;
    }
}
