package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.domain.EntityBaseBusiInfo;
import com.deloitte.crm.mapper.EntityBaseBusiInfoMapper;
import com.deloitte.crm.service.EntityBaseBusiInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 冉浩岑
 * @date 2022/10/13 15:15
 */
@Service
public class EntityBaseBusiInfoServiceImpl extends ServiceImpl<EntityBaseBusiInfoMapper, EntityBaseBusiInfo> implements EntityBaseBusiInfoService {
    @Autowired
    private EntityBaseBusiInfoMapper entityBaseBusiInfoMapper;


    @Override
    public EntityBaseBusiInfo getInfoByEntityCode(String entityCode) {
        return entityBaseBusiInfoMapper.selectOne(new QueryWrapper<EntityBaseBusiInfo>().lambda().eq(EntityBaseBusiInfo::getEntityCode,entityCode));
    }
}
