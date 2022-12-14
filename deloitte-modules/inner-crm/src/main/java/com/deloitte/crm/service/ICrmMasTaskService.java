package com.deloitte.crm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.CrmMasTask;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.vo.CrmMasTaskVo;

import java.util.Date;
import java.util.List;

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
    public CrmMasTask selectCrmMasTaskById(Integer id);

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
     * @return R<Page<CrmMasTaskVo>> 当日任务
     * @param date 请传入参数 yyyy-MM-dd
     * @param sourceName 来源
     * @param pageNum 页码
     * @param pageSize 页数
     */
    R<Page<CrmMasTaskVo>> getTaskInfo(String date,String sourceName, Integer pageNum, Integer pageSize);

    /**
     * 角色2 完成任务
     * @param taskId
     * @param username
     */
    Date finishTask(Integer taskId,String username);

}
