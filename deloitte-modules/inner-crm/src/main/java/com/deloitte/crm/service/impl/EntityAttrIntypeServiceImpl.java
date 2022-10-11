package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
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

    /**
     * 通过 id 查询 wind 一二级 类型 一级attr_id为92 二级为85
     * @author 正杰
     * @date 2022/10/11
     * @param id
     * @return
     */
    @Override
    public R<List<EntityAttrIntype>> getWindBondType(Integer id) {
        if (id == null) {
            // 一级wind行业的 attr_id 为 92
            return R.ok(entityAttrIntypeMapper.selectList(new QueryWrapper<EntityAttrIntype>().lambda().eq(EntityAttrIntype::getAttrId, 92)));
        }else{
            return R.ok(entityAttrIntypeMapper.selectList(new QueryWrapper<EntityAttrIntype>().lambda().eq(EntityAttrIntype::getPId,id)));
        }
    }
}
