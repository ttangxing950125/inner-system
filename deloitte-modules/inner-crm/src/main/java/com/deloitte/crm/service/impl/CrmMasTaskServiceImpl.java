package com.deloitte.crm.service.impl;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.crm.constants.RoleInfo;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.constants.SuccessInfo;
import com.deloitte.crm.domain.CrmDailyTask;
import com.deloitte.crm.domain.CrmEntityTask;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.service.ICrmDailyTaskService;
import com.deloitte.crm.service.ICrmDailyTaskService;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deloitte.crm.mapper.CrmMasTaskMapper;
import com.deloitte.crm.domain.CrmMasTask;
import com.deloitte.crm.service.ICrmMasTaskService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import org.springframework.util.Assert;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
@AllArgsConstructor
public class CrmMasTaskServiceImpl extends ServiceImpl<CrmMasTaskMapper, CrmMasTask> implements ICrmMasTaskService
{
    private final CrmMasTaskMapper crmMasTaskMapper;

    private final ICrmDailyTaskService iCrmDailyTaskService;

//    @Resource
    private ICrmDailyTaskService dailyTaskService;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public CrmMasTask selectCrmMasTaskById(Long id)
    {
        return crmMasTaskMapper.selectCrmMasTaskById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param crmMasTask 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<CrmMasTask> selectCrmMasTaskList(CrmMasTask crmMasTask)
    {
        return crmMasTaskMapper.selectCrmMasTaskList(crmMasTask);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param crmMasTask 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertCrmMasTask(CrmMasTask crmMasTask)
    {
        return crmMasTaskMapper.insertCrmMasTask(crmMasTask);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param crmMasTask 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateCrmMasTask(CrmMasTask crmMasTask)
    {
        return crmMasTaskMapper.updateCrmMasTask(crmMasTask);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCrmMasTaskByIds(Long[] ids)
    {
        return crmMasTaskMapper.deleteCrmMasTaskByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCrmMasTaskById(Long id)
    {
        return crmMasTaskMapper.deleteCrmMasTaskById(id);
    }

    /**
     * 创建敞口划分任务
     *
     * @param entityInfos  主体信息
     * @param taskCategory 数据来源
     * @param taskDate     任务日期
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createTasks(List<EntityInfo> entityInfos, String taskCategory, Date taskDate) {
        if (CollUtil.isEmpty(entityInfos)){
            return false;
        }

        List<CrmMasTask> masTasks = entityInfos.stream().map(item -> {
            CrmMasTask masTask = new CrmMasTask();
            masTask.setSourceName(taskCategory);
            masTask.setEntityCode(item.getEntityCode());
            masTask.setTaskDate(taskDate);
            return masTask;
        }).collect(Collectors.toList());

        //保存进库
        this.saveBatch(masTasks);

        //修改当天任务状态
        dailyTaskService.updateToUnhandled(taskDate, RoleInfo.ROLE2);

        return true;
    }

    /**
     * 角色2今日运维模块
     * @author 正杰
     * @date 2022/9/27
     * @param timeUnit 请传入时间单位常量 MOUTH || DAY
     * @param date 请传入具体日期: yyyy/mm/dd
     * @return R<List<CrmMasTask>> 当月或者当日的任务情况
     */
    @Override
    public R<List<CrmMasTask>> getTaskInfo(String timeUnit, Date date) {
        switch (timeUnit) {
            case Common.DAY:
                List<CrmMasTask> res = baseMapper.selectList(new QueryWrapper<CrmMasTask>()
                        .lambda().eq(CrmMasTask::getTaskDate, date));
                return R.ok(res, SuccessInfo.GET_SUCCESS.getInfo());
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
                    return R.fail();
                }
                List<CrmMasTask> mouthList = baseMapper.selectCrmMasTaskListThisMouth(first, last);
                return R.ok(mouthList, SuccessInfo.GET_SUCCESS.getInfo());
            default:
                return R.fail(BadInfo.PARAM_EMPTY.getInfo());
        }
    }

    /**
     * 完成任务后修改状态 值为1
     */
    private final Integer FINISH_STATE = 1;

    private final Integer UN_FINISH_STATE = 0;

    private final Integer FINISH_DAILY_STATE = 3;

    private final Integer TASK_ROLE_TWO = 4;

    /**
     * 确认该任务已完成,修改数据库任务状态
     * @author 正杰
     * @date 2022/9/27
     * @param id 传入 id
     * @return 操作成功与否
     */
    @Override
    public R changeState(Integer id) {
        // 校验参数
        CrmMasTask crmMasTask = baseMapper.selectOne(new QueryWrapper<CrmMasTask>()
                .lambda().eq(CrmMasTask::getId,id));
        Assert.notNull(crmMasTask,BadInfo.VALID_EMPTY_TARGET.getInfo());
        Assert.isTrue(UN_FINISH_STATE.equals(crmMasTask.getState()),BadInfo.EXITS_TASK_FINISH.getInfo());
        // 修改状态
        baseMapper.update(crmMasTask,new UpdateWrapper<CrmMasTask>()
                .lambda().eq(CrmMasTask::getId,id)
                .set(CrmMasTask::getState,FINISH_STATE));
        //TODO 查看当日任务情况
        List<CrmMasTask> crmMasTasks = baseMapper.selectList(new QueryWrapper<CrmMasTask>()
                .lambda().eq(CrmMasTask::getTaskDate, crmMasTask.getTaskDate())
                .eq(CrmMasTask::getState, UN_FINISH_STATE));
        if(crmMasTasks.size()==0){
            CrmDailyTask crmDailyTask = iCrmDailyTaskService.getBaseMapper()
                    .selectOne(new QueryWrapper<CrmDailyTask>()
                            .lambda().eq(CrmDailyTask::getTaskDate, crmMasTask.getTaskDate())
                            .eq(CrmDailyTask::getTaskRoleType,TASK_ROLE_TWO));
            Assert.notNull(crmDailyTask,BadInfo.EMPTY_TASK_TABLE.getInfo());
            iCrmDailyTaskService.getBaseMapper().update(crmDailyTask,new UpdateWrapper<CrmDailyTask>()
                    .lambda().eq(CrmDailyTask::getTaskDate, crmMasTask.getTaskDate())
                    .eq(CrmDailyTask::getTaskRoleType,TASK_ROLE_TWO)
                    .set(CrmDailyTask::getTaskStatus,FINISH_DAILY_STATE));
        }
        return R.ok(SuccessInfo.SUCCESS.getInfo());
    }


}
