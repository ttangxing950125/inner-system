package com.deloitte.crm.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.crm.domain.CrmEntityTask;
import com.deloitte.crm.vo.CrmEntityTaskVo;

/**
 * 角色7，根据导入的数据新增主体的任务Service接口
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public interface ICrmEntityTaskService extends IService<CrmEntityTask>
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
     * @param date 请传入参数 yyyy-mm-dd
     * @param pageNum
     * @param pageSize
     * @date 2022/9/22
     * @return R<List<CrmEntityTask>> 当日任务情况
     */
    R<Page<CrmEntityTaskVo>> getTaskInfo(Date date, Integer pageNum, Integer pageSize);

    /**
     * 创建任务
     * @param crmEntityTask
     * @return
     */
    CrmEntityTask createTask(CrmEntityTask crmEntityTask);

    /**
     * 处理当日任务
     * @param taskId
     * @param state
     * @return
     */
    R finishTask(Integer taskId,Integer state);
}
