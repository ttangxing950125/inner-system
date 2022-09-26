package com.deloitte.crm.service;

import java.util.Date;
import java.util.List;

import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.web.domain.AjaxResult;
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
     * 批量删除角色7，根据导入的数据新增主体的任务
     * 
     * @param ids 需要删除的角色7，根据导入的数据新增主体的任务主键集合
     * @return 结果
     */
    public int deleteCrmEntityTaskByIds(Integer[] ids);

    /**
     * 删除角色7，根据导入的数据新增主体的任务信息
     * 
     * @param id 角色7，根据导入的数据新增主体的任务主键
     * @return 结果
     */
    public int deleteCrmEntityTaskById(Integer id);

    /**
     * 角色7今日运维模块
     * @author 正杰
     * @date 2022/9/22
     * @param timeUnit 请传入时间单位常量 MOUTH || DAY
     * @param date 请传入具体日期: yyyy-mm-dd
     * @return R<List<CrmEntityTask>> 当月或者当日的任务情况
     */
    R<List<CrmEntityTask>> getTaskInfo(String timeUnit, Date date);

    /**
     * 确认该任务的主体是新增或是忽略
     * @author 正杰
     * @date 2022/9/22
     * @param id 传入 id
     * @param state 传入 状态 1是忽略 2是新增
     * @return 操作成功与否
     */
    R changeState(Integer id,Integer state);

    /**
     * 创建任务
     * @param crmEntityTask
     * @return
     */
    CrmEntityTask createTask(CrmEntityTask crmEntityTask);
}
