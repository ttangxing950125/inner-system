package com.deloitte.crm.service;

import java.util.List;
import com.deloitte.crm.domain.EntityAttrCate;

/**
 * 企业属性分类Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface IEntityAttrCateService 
{
    /**
     * 查询企业属性分类
     * 
     * @param id 企业属性分类主键
     * @return 企业属性分类
     */
    public EntityAttrCate selectEntityAttrCateById(Long id);

    /**
     * 查询企业属性分类列表
     * 
     * @param entityAttrCate 企业属性分类
     * @return 企业属性分类集合
     */
    public List<EntityAttrCate> selectEntityAttrCateList(EntityAttrCate entityAttrCate);

    /**
     * 新增企业属性分类
     * 
     * @param entityAttrCate 企业属性分类
     * @return 结果
     */
    public int insertEntityAttrCate(EntityAttrCate entityAttrCate);

    /**
     * 修改企业属性分类
     * 
     * @param entityAttrCate 企业属性分类
     * @return 结果
     */
    public int updateEntityAttrCate(EntityAttrCate entityAttrCate);

    /**
     * 批量删除企业属性分类
     * 
     * @param ids 需要删除的企业属性分类主键集合
     * @return 结果
     */
    public int deleteEntityAttrCateByIds(Long[] ids);

    /**
     * 删除企业属性分类信息
     * 
     * @param id 企业属性分类主键
     * @return 结果
     */
    public int deleteEntityAttrCateById(Long id);
}
