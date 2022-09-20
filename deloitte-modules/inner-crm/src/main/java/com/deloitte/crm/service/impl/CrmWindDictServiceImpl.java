package com.deloitte.crm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.CrmWindDictMapper;
import com.deloitte.crm.domain.CrmWindDict;
import com.deloitte.crm.service.ICrmWindDictService;

/**
 * 导入的wind文件分类Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class CrmWindDictServiceImpl implements ICrmWindDictService 
{
    @Autowired
    private CrmWindDictMapper crmWindDictMapper;

    /**
     * 查询导入的wind文件分类
     * 
     * @param id 导入的wind文件分类主键
     * @return 导入的wind文件分类
     */
    @Override
    public CrmWindDict selectCrmWindDictById(Long id)
    {
        return crmWindDictMapper.selectCrmWindDictById(id);
    }

    /**
     * 查询导入的wind文件分类列表
     * 
     * @param crmWindDict 导入的wind文件分类
     * @return 导入的wind文件分类
     */
    @Override
    public List<CrmWindDict> selectCrmWindDictList(CrmWindDict crmWindDict)
    {
        return crmWindDictMapper.selectCrmWindDictList(crmWindDict);
    }

    /**
     * 新增导入的wind文件分类
     * 
     * @param crmWindDict 导入的wind文件分类
     * @return 结果
     */
    @Override
    public int insertCrmWindDict(CrmWindDict crmWindDict)
    {
        return crmWindDictMapper.insertCrmWindDict(crmWindDict);
    }

    /**
     * 修改导入的wind文件分类
     * 
     * @param crmWindDict 导入的wind文件分类
     * @return 结果
     */
    @Override
    public int updateCrmWindDict(CrmWindDict crmWindDict)
    {
        return crmWindDictMapper.updateCrmWindDict(crmWindDict);
    }

    /**
     * 批量删除导入的wind文件分类
     * 
     * @param ids 需要删除的导入的wind文件分类主键
     * @return 结果
     */
    @Override
    public int deleteCrmWindDictByIds(Long[] ids)
    {
        return crmWindDictMapper.deleteCrmWindDictByIds(ids);
    }

    /**
     * 删除导入的wind文件分类信息
     * 
     * @param id 导入的wind文件分类主键
     * @return 结果
     */
    @Override
    public int deleteCrmWindDictById(Long id)
    {
        return crmWindDictMapper.deleteCrmWindDictById(id);
    }
}
