package com.deloitte.crm.service;

import java.util.List;
import com.deloitte.crm.domain.CrmEntityTask;

/**
 * 角色7，根据导入的数据新增主体的任务Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface ICrmEntityTaskService 
{
    /**
     * 查询角色7，根据导入的数据新增主体的任务
     * 
     * @param id 角色7，根据导入的数据新增主体的任务主键
     * @return 角色7，根据导入的数据新增主体的任务
     */
    public CrmEntityTask selectCrmEntityTaskById(Long id);

    /**
     * 查询角色7，根据导入的数据新增主体的任务列表
     * 
     * @param crmEntityTask 角色7，根据导入的数据新增主体的任务
     * @return 角色7，根据导入的数据新增主体的任务集合
     */
    public List<CrmEntityTask> selectCrmEntityTaskList(CrmEntityTask crmEntityTask);

    /**
     * 新增角色7，根据导入的数据新增主体的任务
     * 
     * @param crmEntityTask 角色7，根据导入的数据新增主体的任务
     * @return 结果
     */
    public int insertCrmEntityTask(CrmEntityTask crmEntityTask);

    /**
     * 修改角色7，根据导入的数据新增主体的任务
     * 
     * @param crmEntityTask 角色7，根据导入的数据新增主体的任务
     * @return 结果
     */
    public int updateCrmEntityTask(CrmEntityTask crmEntityTask);

    /**
     * 批量删除角色7，根据导入的数据新增主体的任务
     * 
     * @param ids 需要删除的角色7，根据导入的数据新增主体的任务主键集合
     * @return 结果
     */
    public int deleteCrmEntityTaskByIds(Long[] ids);

    /**
     * 删除角色7，根据导入的数据新增主体的任务信息
     * 
     * @param id 角色7，根据导入的数据新增主体的任务主键
     * @return 结果
     */
    public int deleteCrmEntityTaskById(Long id);
}
