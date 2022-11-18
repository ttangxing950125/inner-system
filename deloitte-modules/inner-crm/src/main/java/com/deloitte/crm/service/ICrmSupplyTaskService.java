package com.deloitte.crm.service;

import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.CrmSupplyTask;
import com.deloitte.crm.domain.TaskStatistics;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface ICrmSupplyTaskService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public CrmSupplyTask selectCrmSupplyTaskById(Integer id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param crmSupplyTask 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<CrmSupplyTask> selectCrmSupplyTaskList(CrmSupplyTask crmSupplyTask);

    /**
     * 新增【请填写功能名称】
     * 
     * @param crmSupplyTask 【请填写功能名称】
     * @return 结果
     */
    public int insertCrmSupplyTask(CrmSupplyTask crmSupplyTask);

    /**
     * 修改【请填写功能名称】
     * 
     * @param crmSupplyTask 【请填写功能名称】
     * @return 结果
     */
    public int updateCrmSupplyTask(CrmSupplyTask crmSupplyTask);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteCrmSupplyTaskByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteCrmSupplyTaskById(Long id);

    R getRoleSupplyTask(String taskDate, Integer pageNum,Integer pageSize);


    TaskStatistics getTaskStatistics();

    void completeTaskById(Integer id);
}
