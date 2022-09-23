package com.deloitte.crm.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.CrmWindDict;

/**
 * 导入的wind文件分类Mapper接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface CrmWindDictMapper extends BaseMapper<CrmWindDict>
{
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
     * 删除导入的wind文件分类
     * 
     * @param id 导入的wind文件分类主键
     * @return 结果
     */
    public int deleteCrmWindDictById(Long id);

    /**
     * 批量删除导入的wind文件分类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrmWindDictByIds(Long[] ids);

    /**
     *查询所有未关闭的导入wind的底稿数据
     *
     * @return List<CrmWindDict>
     * @author penTang
     * @date 2022/9/22 14:56
    */
    List<CrmWindDict> selectAll();
}
