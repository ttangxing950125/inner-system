package com.deloitte.crm.mapper;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.CrmEntityTask;
import com.deloitte.crm.dto.TaskDto;
import org.apache.ibatis.annotations.Param;

/**
 * 角色7，根据导入的数据新增主体的任务Mapper接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface CrmEntityTaskMapper extends BaseMapper<CrmEntityTask>
{
    /**
     * 查询角色7，根据导入的数据新增主体的任务
     * 
     * @param id 角色7，根据导入的数据新增主体的任务主键
     * @return 角色7，根据导入的数据新增主体的任务
     */
    public CrmEntityTask selectCrmEntityTaskById(Integer id);

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
     * 删除角色7，根据导入的数据新增主体的任务
     * 
     * @param id 角色7，根据导入的数据新增主体的任务主键
     * @return 结果
     */
    public int deleteCrmEntityTaskById(Integer id);

    /**
     * 批量删除角色7，根据导入的数据新增主体的任务
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrmEntityTaskByIds(Integer[] ids);

    /**
     * 查询本月内的任务情况
     * @param first 起始日
     * @param last 结束日
     * @return
     */
    List<CrmEntityTask> selectCrmEntityTaskListThisMouth(@Param("first") Date first, @Param("last") Date last);

    List<TaskDto> selctCrmEntityTaskCount(String taskDate);
}
