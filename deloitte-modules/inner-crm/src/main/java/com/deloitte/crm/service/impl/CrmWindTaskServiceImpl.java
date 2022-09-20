package com.deloitte.crm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.CrmWindTaskMapper;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.service.ICrmWindTaskService;

/**
 * 角色1的每日任务，导入wind文件的任务Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class CrmWindTaskServiceImpl implements ICrmWindTaskService 
{
    @Autowired
    private CrmWindTaskMapper crmWindTaskMapper;

    /**
     * 查询角色1的每日任务，导入wind文件的任务
     * 
     * @param id 角色1的每日任务，导入wind文件的任务主键
     * @return 角色1的每日任务，导入wind文件的任务
     */
    @Override
    public CrmWindTask selectCrmWindTaskById(Long id)
    {
        return crmWindTaskMapper.selectCrmWindTaskById(id);
    }

    /**
     * 查询角色1的每日任务，导入wind文件的任务列表
     * 
     * @param crmWindTask 角色1的每日任务，导入wind文件的任务
     * @return 角色1的每日任务，导入wind文件的任务
     */
    @Override
    public List<CrmWindTask> selectCrmWindTaskList(CrmWindTask crmWindTask)
    {
        return crmWindTaskMapper.selectCrmWindTaskList(crmWindTask);
    }

    /**
     * 新增角色1的每日任务，导入wind文件的任务
     * 
     * @param crmWindTask 角色1的每日任务，导入wind文件的任务
     * @return 结果
     */
    @Override
    public int insertCrmWindTask(CrmWindTask crmWindTask)
    {
        return crmWindTaskMapper.insertCrmWindTask(crmWindTask);
    }

    /**
     * 修改角色1的每日任务，导入wind文件的任务
     * 
     * @param crmWindTask 角色1的每日任务，导入wind文件的任务
     * @return 结果
     */
    @Override
    public int updateCrmWindTask(CrmWindTask crmWindTask)
    {
        return crmWindTaskMapper.updateCrmWindTask(crmWindTask);
    }

    /**
     * 批量删除角色1的每日任务，导入wind文件的任务
     * 
     * @param ids 需要删除的角色1的每日任务，导入wind文件的任务主键
     * @return 结果
     */
    @Override
    public int deleteCrmWindTaskByIds(Long[] ids)
    {
        return crmWindTaskMapper.deleteCrmWindTaskByIds(ids);
    }

    /**
     * 删除角色1的每日任务，导入wind文件的任务信息
     * 
     * @param id 角色1的每日任务，导入wind文件的任务主键
     * @return 结果
     */
    @Override
    public int deleteCrmWindTaskById(Long id)
    {
        return crmWindTaskMapper.deleteCrmWindTaskById(id);
    }
}
