package com.deloitte.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.CrmSupplyTask;
import com.deloitte.crm.dto.TaskDto;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface CrmSupplyTaskMapper extends BaseMapper<CrmSupplyTask>
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public CrmSupplyTask selectCrmSupplyTaskById(Long id);

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
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteCrmSupplyTaskById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrmSupplyTaskByIds(Long[] ids);


    TaskDto selctCrmCount(String taskDate);
}
