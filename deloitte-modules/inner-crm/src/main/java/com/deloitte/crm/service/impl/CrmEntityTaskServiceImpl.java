package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.RoleInfo;
import com.deloitte.crm.constants.SuccessInfo;
import com.deloitte.crm.domain.CrmDailyTask;
import com.deloitte.crm.domain.CrmEntityTask;
import com.deloitte.crm.domain.CrmMasTask;
import com.deloitte.crm.domain.EntityCaptureSpeed;
import com.deloitte.crm.mapper.CrmEntityTaskMapper;
import com.deloitte.crm.mapper.CrmMasTaskMapper;
import com.deloitte.crm.service.EntityCaptureSpeedService;
import com.deloitte.crm.service.ICrmDailyTaskService;
import com.deloitte.crm.service.ICrmEntityTaskService;
import com.deloitte.crm.service.SendEmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 角色7，根据导入的数据新增主体的任务Service业务层处理
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Service
@AllArgsConstructor
@Slf4j
public class CrmEntityTaskServiceImpl extends ServiceImpl<CrmEntityTaskMapper, CrmEntityTask> implements ICrmEntityTaskService {
    @Resource
    private CrmEntityTaskMapper crmEntityTaskMapper;

    @Resource
    private ICrmDailyTaskService crmDailyTaskService;

    @Resource
    private CrmMasTaskMapper crmMasTaskMapper;

    @Resource
    private SendEmailService sendEmailService;

    @Resource
    private EntityCaptureSpeedService entityCaptureSpeedService;

    /**
     * 查询角色7，根据导入的数据新增主体的任务
     *
     * @param id 角色7，根据导入的数据新增主体的任务主键
     * @return 角色7，根据导入的数据新增主体的任务
     */
    @Override
    public CrmEntityTask selectCrmEntityTaskById(Integer id) {
        return crmEntityTaskMapper.selectCrmEntityTaskById(id);
    }

    /**
     * 查询角色7，根据导入的数据新增主体的任务列表
     *
     * @param crmEntityTask 角色7，根据导入的数据新增主体的任务
     * @return 角色7，根据导入的数据新增主体的任务
     */
    @Override
    public List<CrmEntityTask> selectCrmEntityTaskList(CrmEntityTask crmEntityTask) {
        return crmEntityTaskMapper.selectCrmEntityTaskList(crmEntityTask);
    }

    /**
     * 新增角色7，根据导入的数据新增主体的任务
     *
     * @param crmEntityTask 角色7，根据导入的数据新增主体的任务
     * @return 结果
     */
    @Override
    public int insertCrmEntityTask(CrmEntityTask crmEntityTask) {
        return crmEntityTaskMapper.insertCrmEntityTask(crmEntityTask);
    }

    /**
     * 修改角色7，根据导入的数据新增主体的任务
     *
     * @param crmEntityTask 角色7，根据导入的数据新增主体的任务
     * @return 结果
     */
    @Override
    public int updateCrmEntityTask(CrmEntityTask crmEntityTask) {
        return crmEntityTaskMapper.updateCrmEntityTask(crmEntityTask);
    }

    /**
     * 批量删除角色7，根据导入的数据新增主体的任务
     *
     * @param ids 需要删除的角色7，根据导入的数据新增主体的任务主键
     * @return 结果
     */
    @Override
    public int deleteCrmEntityTaskByIds(Integer[] ids) {
        return crmEntityTaskMapper.deleteCrmEntityTaskByIds(ids);
    }

    /**
     * 删除角色7，根据导入的数据新增主体的任务信息
     *
     * @param id 角色7，根据导入的数据新增主体的任务主键
     * @return 结果
     */
    @Override
    public int deleteCrmEntityTaskById(Integer id) {
        return crmEntityTaskMapper.deleteCrmEntityTaskById(id);
    }

    /**
     * 角色7今日运维模块
     *
     * @param date     请传入参数 yyyy-mm-dd
     * @param pageNum
     * @param pageSize
     * @return R<List < CrmEntityTask>> 当日任务情况
     * @author 正杰
     * @date 2022/9/22
     */
    @Override
    public R<Page<CrmEntityTask>> getTaskInfo(String date, Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 5 : pageSize;
        Page<CrmEntityTask> crmEntityTaskPage = baseMapper.selectPage(new Page<>(pageNum, pageSize), new QueryWrapper<CrmEntityTask>()
                .lambda().eq(CrmEntityTask::getTaskDate, date));
        return R.ok(crmEntityTaskPage, SuccessInfo.GET_SUCCESS.getInfo());
    }

    /**
     * 处理当日任务
     *
     * @param taskId
     * @param state 0-未处理 1-已有主体未关联 2-新增主体 3-已有主体已关联
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R finishTask(Integer taskId, Integer state, String entityCode) {
        log.info(">>>>>>处理当日任务开始 taskId={}>>entityCode>>>:{}", taskId, entityCode);
        CrmEntityTask crmEntityTask = Optional.ofNullable(baseMapper.selectOne(new QueryWrapper<CrmEntityTask>().lambda().eq(CrmEntityTask::getId, taskId))).orElseThrow(() -> new ServiceException(BadInfo.VALID_EMPTY_TARGET.getInfo()));
        Assert.isTrue(crmEntityTask.getState() == 0, BadInfo.EXITS_TASK_FINISH.getInfo());
        crmEntityTask.setState(state);
        baseMapper.updateById(crmEntityTask);
        //如果 entity_code 不为 null 那么就是新增，便给角色 2 新增一条任务
        if (!ObjectUtils.isEmpty(entityCode)) {
            crmMasTaskMapper.insert(new CrmMasTask().setEntityCode(entityCode).setSourceName(crmEntityTask.getTaskCategory()).setState(0).setTaskDate(new Date()));
            CrmDailyTask crmDailyTask = crmDailyTaskService.getBaseMapper().selectOne(new QueryWrapper<CrmDailyTask>().lambda().eq(CrmDailyTask::getTaskDate, DateUtil.format(new Date(), "yyyy-MM-dd")).eq(CrmDailyTask::getTaskRoleType,4));
            if(ObjectUtils.isEmpty(crmDailyTask)){
                crmDailyTaskService.getBaseMapper().insert(new CrmDailyTask().setTaskRoleType("4").setTaskStatus(2).setTaskDate(new Date()));
            }else{
                crmDailyTask.setTaskStatus(2);
                crmDailyTaskService.getBaseMapper().updateById(crmDailyTask);
            }
        }
        Date taskDate = crmEntityTask.getTaskDate();
        List<CrmEntityTask> unFinish = baseMapper
                .selectList(new QueryWrapper<CrmEntityTask>().lambda()
                        .like(CrmEntityTask::getTaskDate, DateUtil.format(taskDate, "yyyy-MM-dd"))
                        .eq(CrmEntityTask::getState, 0));
        if (unFinish.size() == 0) {
            //查询日任务 角色7对应的 task_role_type 为 8
            CrmDailyTask crmDailyTask = Optional.ofNullable(crmDailyTaskService.getBaseMapper().selectOne(new QueryWrapper<CrmDailyTask>().lambda().like(CrmDailyTask::getTaskDate, DateUtil.format(taskDate, "yyyy-MM-dd")).eq(CrmDailyTask::getTaskRoleType, 8))).orElseThrow(() -> new ServiceException(BadInfo.EMPTY_TASK_TABLE.getInfo()));
            // 当日任务处理完毕 状态码为 3
            crmDailyTask.setTaskStatus(3);
            crmDailyTaskService.getBaseMapper().updateById(crmDailyTask);

            // 当日是新增主体的数量 状态码为2 代表是新增主体
            List<CrmEntityTask> crmEntityTasks = baseMapper.selectList(new QueryWrapper<CrmEntityTask>().lambda().eq(CrmEntityTask::getState, 2));
            CrmDailyTask role2DailyTask = new CrmDailyTask().setTaskRoleType("4").setTaskDate(new Date());

            if (crmEntityTasks.size() != 0) {
                //发送邮件 角色2 的 role ID 固定为 4
                asycSendEmailService(4, crmEntityTasks.size());
            }

            return R.ok(SuccessInfo.SUCCESS.getInfo());
        }
        return R.ok(SuccessInfo.SUCCESS.getInfo());
    }

    @Async
    public void asycSendEmailService(Integer roleId, Integer taskCount) {
        log.info(">>>>异步发送邮件开始,RoleId:{}新增主体个数：{}", roleId, taskCount);
        sendEmailService.email(roleId, taskCount);
    }

    /**
     * 创建任务
     * 测试暂存区
     * @param crmEntityTask
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CrmEntityTask createTask(CrmEntityTask crmEntityTask) {
        //当前日期
        Date taskDate = crmEntityTask.getTaskDate();

        //生成entity_capture_speed数据
        EntityCaptureSpeed captureSpeed = new EntityCaptureSpeed();
        captureSpeed.setSource(crmEntityTask.getTaskCategory());
        captureSpeed.setEntityName(crmEntityTask.getEntityName());
        captureSpeed.setCaptureTime(new Date());

        entityCaptureSpeedService.save(captureSpeed);
        crmEntityTaskMapper.insert(crmEntityTask);

        //修改今天角色6的任务为有任务未处理
        crmDailyTaskService.updateToUnhandled(taskDate, RoleInfo.ROLE6);

        return crmEntityTask;
    }

}
