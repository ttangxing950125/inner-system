package com.deloitte.crm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.CrmEntityTaskMapper;
import com.deloitte.crm.domain.CrmEntityTask;
import com.deloitte.crm.service.ICrmEntityTaskService;

/**
 * 角色7，根据导入的数据新增主体的任务Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class CrmEntityTaskServiceImpl implements ICrmEntityTaskService 
{
    @Autowired
    private CrmEntityTaskMapper crmEntityTaskMapper;

    /**
     * 查询角色7，根据导入的数据新增主体的任务
     * 
     * @param id 角色7，根据导入的数据新增主体的任务主键
     * @return 角色7，根据导入的数据新增主体的任务
     */
    @Override
    public CrmEntityTask selectCrmEntityTaskById(Long id)
    {
        return crmEntityTaskMapper.selectCrmEntityTaskById(id);
    }

    /**
     * 查询角色7，根据导入的数据新增主体的任务列表
     * 
     * @param crmEntityTask 角色7，根据导入的数据新增主体的任务
     * @return 角色7，根据导入的数据新增主体的任务
     */
    @Override
    public List<CrmEntityTask> selectCrmEntityTaskList(CrmEntityTask crmEntityTask)
    {
        return crmEntityTaskMapper.selectCrmEntityTaskList(crmEntityTask);
    }

    /**
     * 新增角色7，根据导入的数据新增主体的任务
     * 
     * @param crmEntityTask 角色7，根据导入的数据新增主体的任务
     * @return 结果
     */
    @Override
    public int insertCrmEntityTask(CrmEntityTask crmEntityTask)
    {
        return crmEntityTaskMapper.insertCrmEntityTask(crmEntityTask);
    }

    /**
     * 修改角色7，根据导入的数据新增主体的任务
     * 
     * @param crmEntityTask 角色7，根据导入的数据新增主体的任务
     * @return 结果
     */
    @Override
    public int updateCrmEntityTask(CrmEntityTask crmEntityTask)
    {
        return crmEntityTaskMapper.updateCrmEntityTask(crmEntityTask);
    }

    /**
     * 批量删除角色7，根据导入的数据新增主体的任务
     * 
     * @param ids 需要删除的角色7，根据导入的数据新增主体的任务主键
     * @return 结果
     */
    @Override
    public int deleteCrmEntityTaskByIds(Long[] ids)
    {
        return crmEntityTaskMapper.deleteCrmEntityTaskByIds(ids);
    }

    /**
     * 删除角色7，根据导入的数据新增主体的任务信息
     * 
     * @param id 角色7，根据导入的数据新增主体的任务主键
     * @return 结果
     */
    @Override
    public int deleteCrmEntityTaskById(Long id)
    {
        return crmEntityTaskMapper.deleteCrmEntityTaskById(id);
    }
}
