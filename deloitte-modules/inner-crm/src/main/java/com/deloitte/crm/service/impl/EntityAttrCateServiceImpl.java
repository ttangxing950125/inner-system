package com.deloitte.crm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.EntityAttrCateMapper;
import com.deloitte.crm.domain.EntityAttrCate;
import com.deloitte.crm.service.IEntityAttrCateService;

/**
 * 企业属性分类Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class EntityAttrCateServiceImpl implements IEntityAttrCateService 
{
    @Autowired
    private EntityAttrCateMapper entityAttrCateMapper;

    /**
     * 查询企业属性分类
     * 
     * @param id 企业属性分类主键
     * @return 企业属性分类
     */
    @Override
    public EntityAttrCate selectEntityAttrCateById(Long id)
    {
        return entityAttrCateMapper.selectEntityAttrCateById(id);
    }

    /**
     * 查询企业属性分类列表
     * 
     * @param entityAttrCate 企业属性分类
     * @return 企业属性分类
     */
    @Override
    public List<EntityAttrCate> selectEntityAttrCateList(EntityAttrCate entityAttrCate)
    {
        return entityAttrCateMapper.selectEntityAttrCateList(entityAttrCate);
    }

    /**
     * 新增企业属性分类
     * 
     * @param entityAttrCate 企业属性分类
     * @return 结果
     */
    @Override
    public int insertEntityAttrCate(EntityAttrCate entityAttrCate)
    {
        return entityAttrCateMapper.insertEntityAttrCate(entityAttrCate);
    }

    /**
     * 修改企业属性分类
     * 
     * @param entityAttrCate 企业属性分类
     * @return 结果
     */
    @Override
    public int updateEntityAttrCate(EntityAttrCate entityAttrCate)
    {
        return entityAttrCateMapper.updateEntityAttrCate(entityAttrCate);
    }

    /**
     * 批量删除企业属性分类
     * 
     * @param ids 需要删除的企业属性分类主键
     * @return 结果
     */
    @Override
    public int deleteEntityAttrCateByIds(Long[] ids)
    {
        return entityAttrCateMapper.deleteEntityAttrCateByIds(ids);
    }

    /**
     * 删除企业属性分类信息
     * 
     * @param id 企业属性分类主键
     * @return 结果
     */
    @Override
    public int deleteEntityAttrCateById(Long id)
    {
        return entityAttrCateMapper.deleteEntityAttrCateById(id);
    }
}
