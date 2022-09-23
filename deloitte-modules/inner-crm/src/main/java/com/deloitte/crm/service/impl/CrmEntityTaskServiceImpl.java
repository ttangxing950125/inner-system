package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.web.domain.AjaxResult;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.constants.SuccessInfo;
import com.deloitte.crm.domain.CrmEntityTask;
import com.deloitte.crm.mapper.CrmEntityTaskMapper;
import com.deloitte.crm.service.ICrmEntityTaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

/**
 * 角色7，根据导入的数据新增主体的任务Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
@AllArgsConstructor
public class CrmEntityTaskServiceImpl extends ServiceImpl<CrmEntityTaskMapper,CrmEntityTask> implements ICrmEntityTaskService
{
    private CrmEntityTaskMapper crmEntityTaskMapper;

    /**
     * 查询角色7，根据导入的数据新增主体的任务
     * 
     * @param id 角色7，根据导入的数据新增主体的任务主键
     * @return 角色7，根据导入的数据新增主体的任务
     */
    @Override
    public CrmEntityTask selectCrmEntityTaskById(Integer id)
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
    public int deleteCrmEntityTaskByIds(Integer[] ids)
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
    public int deleteCrmEntityTaskById(Integer id)
    {
        return crmEntityTaskMapper.deleteCrmEntityTaskById(id);
    }


    /**
     * 角色7今日运维模块
     * @author 正杰
     * @date 2022/9/22
     * @param timeUnit 请传入时间单位常量 MOUTH || DAY
     * @param date 请传入具体日期: yyyy-mm-dd
     * @return 当月或者当日的任务情况
     */
    @Override
    public AjaxResult getTaskInfo(String timeUnit, Date date) {

        CrmEntityTask crmEntityTask = new CrmEntityTask();
        switch (timeUnit){
            case Common.DAY:
                crmEntityTask.setTaskDate(date);
                CrmEntityTask dayList = baseMapper.selectOne(new QueryWrapper<CrmEntityTask>().eq("task_date", date));
                return AjaxResult.success(SuccessInfo.GET_SUCCESS.getInfo(),dayList);
            case Common.MOUTH:

                Date first = null;
                Date last = null;
                try {
                    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    ParsePosition pos = new ParsePosition(8);

                    first = Date.from(localDate.with(TemporalAdjusters.firstDayOfMonth())
                            .atTime(LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant());
                    String firstString = formatter.format(first);
                    first = formatter.parse(firstString);

                    last = Date.from(localDate.with(TemporalAdjusters.lastDayOfMonth())
                            .atTime(LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant());
                    String lastString = formatter.format(last);
                    last = formatter.parse(lastString);
                } catch (ParseException e) {
                    return AjaxResult.error();
                }
                List<CrmEntityTask> mouthList = crmEntityTaskMapper.selectCrmEntityTaskListThisMouth(first,last);
                return AjaxResult.success(SuccessInfo.GET_SUCCESS.getInfo(),mouthList);
            default:
                return AjaxResult.error(BadInfo.PARAM_EMPTY.getInfo());
        }

    }


    /**
     * 确认该任务的主体是新增或是忽略
     * @author 正杰
     * @date 2022/9/22
     * @param id 传入 id
     * @param state 传入 状态 1是忽略 2是新增
     * @return 操作成功与否
     */
    @Override
    public AjaxResult changeState(Integer id,Integer state) {
        try {
            CrmEntityTask crmEntityTask = new CrmEntityTask();
            crmEntityTask.setId(id);
            UpdateWrapper<CrmEntityTask> udWapper = new UpdateWrapper();
            udWapper.lambda().eq(CrmEntityTask::getId,id)
                    .set(CrmEntityTask::getState,state);
            baseMapper.update(crmEntityTask,udWapper);
        } catch (Exception e) {
            return AjaxResult.error();
        }
        return AjaxResult.success(SuccessInfo.SUCCESS.getInfo());
    }
}
