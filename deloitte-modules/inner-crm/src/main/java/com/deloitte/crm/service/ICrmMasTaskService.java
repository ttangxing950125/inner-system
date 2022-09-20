package com.deloitte.crm.service;

import java.util.List;
import com.deloitte.crm.domain.CrmMasTask;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface ICrmMasTaskService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public CrmMasTask selectCrmMasTaskById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param crmMasTask 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<CrmMasTask> selectCrmMasTaskList(CrmMasTask crmMasTask);

    /**
     * 新增【请填写功能名称】
     * 
     * @param crmMasTask 【请填写功能名称】
     * @return 结果
     */
    public int insertCrmMasTask(CrmMasTask crmMasTask);

    /**
     * 修改【请填写功能名称】
     * 
     * @param crmMasTask 【请填写功能名称】
     * @return 结果
     */
    public int updateCrmMasTask(CrmMasTask crmMasTask);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteCrmMasTaskByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteCrmMasTaskById(Long id);
}
