package com.deloitte.crm.mapper;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.CrmEntityTask;
import com.deloitte.crm.domain.CrmMasTask;
import com.deloitte.crm.dto.TaskDto;
import org.apache.ibatis.annotations.Param;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface CrmMasTaskMapper extends BaseMapper<CrmMasTask>
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
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteCrmMasTaskById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrmMasTaskByIds(Long[] ids);

    /**
     * 查询当月任务接口
     * @param first
     * @param last
     * @return
     */
    List<CrmMasTask> selectCrmMasTaskListThisMouth(@Param("first") Date first, @Param("last") Date last);

    /**
     *查询当前角色任务完成信息
     *
     * @param taskDate
     * @return List<TaskDto>
     * @author penTang
     * @date 2022/10/11 19:40
    */
    List<TaskDto> selectCrmMasTaskCount(String taskDate);

}
