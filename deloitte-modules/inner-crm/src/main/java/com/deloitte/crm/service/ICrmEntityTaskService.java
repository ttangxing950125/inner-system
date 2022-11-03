package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.CrmEntityTask;

import java.util.List;

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
     * @param taskCategory 捕获渠道
     * @param date 请传入参数 yyyy-mm-dd
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @date 2022/9/22
     * @return R<List<CrmEntityTask>> 当日任务情况
     */
    R<Page<CrmEntityTask>> getTaskInfo(String taskCategory,String date, Integer pageNum, Integer pageSize);

    /**
     * 创建任务
     * @param crmEntityTask
     * @return
     */
    CrmEntityTask createTask(CrmEntityTask crmEntityTask);

    /**
     * 处理当日任务
     * @param taskId 任务id
     * @param state 0-未处理 1-已有主体未关联 2-新增主体 3-已有主体已关联
     * @param entityCode 主体 code
     * @param remarks 备注信息
     * @return 操作成功与否
     */
    R finishTask(Integer taskId,Integer state,String entityCode,String remarks);


    String sendEmail();
}
