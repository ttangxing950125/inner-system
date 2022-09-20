package com.deloitte.crm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.CrmSupplyTaskMapper;
import com.deloitte.crm.domain.CrmSupplyTask;
import com.deloitte.crm.service.ICrmSupplyTaskService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class CrmSupplyTaskServiceImpl implements ICrmSupplyTaskService 
{
    @Autowired
    private CrmSupplyTaskMapper crmSupplyTaskMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public CrmSupplyTask selectCrmSupplyTaskById(Long id)
    {
        return crmSupplyTaskMapper.selectCrmSupplyTaskById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param crmSupplyTask 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<CrmSupplyTask> selectCrmSupplyTaskList(CrmSupplyTask crmSupplyTask)
    {
        return crmSupplyTaskMapper.selectCrmSupplyTaskList(crmSupplyTask);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param crmSupplyTask 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertCrmSupplyTask(CrmSupplyTask crmSupplyTask)
    {
        return crmSupplyTaskMapper.insertCrmSupplyTask(crmSupplyTask);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param crmSupplyTask 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateCrmSupplyTask(CrmSupplyTask crmSupplyTask)
    {
        return crmSupplyTaskMapper.updateCrmSupplyTask(crmSupplyTask);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCrmSupplyTaskByIds(Long[] ids)
    {
        return crmSupplyTaskMapper.deleteCrmSupplyTaskByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCrmSupplyTaskById(Long id)
    {
        return crmSupplyTaskMapper.deleteCrmSupplyTaskById(id);
    }
}
