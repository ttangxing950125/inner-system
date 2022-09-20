package com.deloitte.crm.mapper;

import java.util.List;
import com.deloitte.crm.domain.CrmWindTask;

/**
 * 角色1的每日任务，导入wind文件的任务Mapper接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface CrmWindTaskMapper 
{
    /**
     * 查询角色1的每日任务，导入wind文件的任务
     * 
     * @param id 角色1的每日任务，导入wind文件的任务主键
     * @return 角色1的每日任务，导入wind文件的任务
     */
    public CrmWindTask selectCrmWindTaskById(Long id);

    /**
     * 查询角色1的每日任务，导入wind文件的任务列表
     * 
     * @param crmWindTask 角色1的每日任务，导入wind文件的任务
     * @return 角色1的每日任务，导入wind文件的任务集合
     */
    public List<CrmWindTask> selectCrmWindTaskList(CrmWindTask crmWindTask);

    /**
     * 新增角色1的每日任务，导入wind文件的任务
     * 
     * @param crmWindTask 角色1的每日任务，导入wind文件的任务
     * @return 结果
     */
    public int insertCrmWindTask(CrmWindTask crmWindTask);

    /**
     * 修改角色1的每日任务，导入wind文件的任务
     * 
     * @param crmWindTask 角色1的每日任务，导入wind文件的任务
     * @return 结果
     */
    public int updateCrmWindTask(CrmWindTask crmWindTask);

    /**
     * 删除角色1的每日任务，导入wind文件的任务
     * 
     * @param id 角色1的每日任务，导入wind文件的任务主键
     * @return 结果
     */
    public int deleteCrmWindTaskById(Long id);

    /**
     * 批量删除角色1的每日任务，导入wind文件的任务
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrmWindTaskByIds(Long[] ids);
}
