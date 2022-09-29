package com.deloitte.crm.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.CrmMasTask;
import com.deloitte.crm.domain.EntityInfo;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface ICrmMasTaskService extends IService<CrmMasTask>
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

    /**
     * 创建敞口划分任务
     * @param entityInfos 主体信息
     * @param taskCategory 数据来源
     * @param taskDate 任务日期
     * @return
     */
    boolean createTasks(List<EntityInfo> entityInfos, String taskCategory, Date taskDate);

    /**
     * 角色2今日运维模块
     * @author 正杰
     * @date 2022/9/27
     * @param timeUnit 请传入时间单位常量 MOUTH || DAY
     * @param date 请传入具体日期: yyyy/mm/dd
     * @return R<List<CrmMasTask>> 当月或者当日的任务情况
     */
    R<List<CrmMasTask>> getTaskInfo(String timeUnit, String date);

    /**
     * 确认该任务已完成,修改数据库任务状态
     * @author 正杰
     * @date 2022/9/27
     * @param id 传入 id
     * @return 操作成功与否
     */
    R changeState(Integer id);
}
