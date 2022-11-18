package com.deloitte.crm.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.crm.domain.CrmWindDict;

/**
 * 导入的wind文件分类Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface ICrmWindDictService  extends IService<CrmWindDict>
{
    List<CrmWindDict> selectAll();

    /**
     * 查询导入的wind文件分类
     * 
     * @param id 导入的wind文件分类主键
     * @return 导入的wind文件分类
     */
    public CrmWindDict selectCrmWindDictById(Long id);

    /**
     * 查询导入的wind文件分类列表
     * 
     * @param crmWindDict 导入的wind文件分类
     * @return 导入的wind文件分类集合
     */
    public List<CrmWindDict> selectCrmWindDictList(CrmWindDict crmWindDict);

    /**
     * 新增导入的wind文件分类
     * 
     * @param crmWindDict 导入的wind文件分类
     * @return 结果
     */
    public int insertCrmWindDict(CrmWindDict crmWindDict);

    /**
     * 修改导入的wind文件分类
     * 
     * @param crmWindDict 导入的wind文件分类
     * @return 结果
     */
    public int updateCrmWindDict(CrmWindDict crmWindDict);

    /**
     * 批量删除导入的wind文件分类
     * 
     * @param ids 需要删除的导入的wind文件分类主键集合
     * @return 结果
     */
    public int deleteCrmWindDictByIds(Long[] ids);

    /**
     * 删除导入的wind文件分类信息
     * 
     * @param id 导入的wind文件分类主键
     * @return 结果
     */
    public int deleteCrmWindDictById(Long id);
}
