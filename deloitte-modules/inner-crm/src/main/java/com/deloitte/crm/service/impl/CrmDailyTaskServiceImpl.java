package com.deloitte.crm.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.RoleInfo;
import com.deloitte.crm.domain.CrmDailyTask;
import com.deloitte.crm.domain.CrmSupplyTask;
import com.deloitte.crm.mapper.CrmDailyTaskMapper;
import com.deloitte.crm.mapper.CrmSupplyTaskMapper;
import com.deloitte.crm.service.ICrmDailyTaskService;
import com.deloitte.system.api.RoleService;
import com.deloitte.system.api.domain.SysRole;
import com.deloitte.system.api.model.LoginUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 每日生产角色任务的service业务实现层
 *
 * @author PenTang
 * @date 2022/09/22 15:35
 */
@Service
@AllArgsConstructor
@Slf4j
public class CrmDailyTaskServiceImpl extends ServiceImpl<CrmDailyTaskMapper, CrmDailyTask> implements ICrmDailyTaskService {

    private RoleService roleService;

    @Resource
    private CrmSupplyTaskMapper crmSupplyTaskMapper;

    @Resource
    private CrmDailyTaskMapper crmDailyTaskMapper;

    /**
     * 更新状态为 2-有任务未全部处理完
     *
     * @param timeNow
     * @param roleInfo
     * @return
     */
    @Override
    public boolean updateToUnhandled(Date timeNow, RoleInfo roleInfo) {

        return this.update(Wrappers.<CrmDailyTask>lambdaUpdate()
                .eq(CrmDailyTask::getTaskDate, timeNow)
                .eq(CrmDailyTask::getTaskRoleType, roleInfo.getId())
                .set(CrmDailyTask::getTaskStatus, 2));
    }

    /***
     *根据指定日期查询当月的任务
     *
     * @param TaskDate
     * @return List<CrmWindTask>
     * @author penTang
     * @date 2022/9/22 10:46
     */
    @Override
    public List<CrmDailyTask> selectCrmDailyTaskListByDate(String TaskDate) {
        String startDate = TaskDate + "-01";
        LocalDate today = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDay = today.with(TemporalAdjusters.lastDayOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String endTime = endDay.format(formatter);
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Set<String> roles = loginUser.getRoles();
        ArrayList<String> roleKey = new ArrayList<String>();
        roleKey.add("role1");
        roleKey.add("role2");
        roleKey.add("role3");
        roleKey.add("role4");
        roleKey.add("role5");
        roleKey.add("role6");
        List<CrmDailyTask> crmDailyTasks = new ArrayList<>();
        roles.forEach(o -> {
            boolean contains = roleKey.contains(o);
            if (contains) {
                SysRole sysRole = roleService.selectRoleList().stream()
                        .filter(row -> row.getRoleKey().equals(o))
                        .collect(Collectors.toList())
                        .get(0);
                crmDailyTasks.addAll(baseMapper.selectCrmDailyTaskListByDate(startDate, endTime, sysRole.getRoleId().intValue()));
            }
        });
        return crmDailyTasks;
    }

    /**
     * 保存每日的角色相关任务
     *
     * @param tasks
     * @return Boolean
     * @author penTang
     * @date 2022/9/22 20:24
     */
    @Override
    public Boolean saveCrmDailyTask(List<CrmDailyTask> tasks) {
        return saveBatch(tasks);
    }

    /**
     * 根据当天时间和角色1的type更新成-有任务未完成(2)
     *
     * @param dateTime
     * @return Boolean
     * @author penTang
     * @date 2022/9/22 20:24
     */
    @Override
    public Boolean updateByType(Date dateTime) {
        return update(new LambdaUpdateWrapper<CrmDailyTask>()
                .eq(CrmDailyTask::getTaskRoleType, 3)
                .eq(CrmDailyTask::getTaskDate, dateTime)
                .set(CrmDailyTask::getTaskStatus, 2)

        );
    }

    /**
     * 指定日期查询各角色当月任务完成情况
     *
     * @return R
     * @author penTang
     * @editeBy 正杰
     * @date 2022/9/21 18:06
     * @editeDate 2022/9/29
     */
    @Override
    public R<List<CrmDailyTask>> queryDailyTask(String taskDate, Long userId) {
        Date date;
        try {
            date = DateUtil.parseDate(taskDate);
        } catch (Exception e) {
            return R.fail(BadInfo.ERROR_PARAM_DATE.getInfo());
        }

        Date begin = DateUtil.beginOfMonth(date);
        Date end = DateUtil.endOfMonth(date);

        return null;
    }


    /**
     * 传入对应参数 当月任务列表进行新增或修改
     *
     * @param taskRoleType
     * @param taskStatus
     * @param date
     * @author 正杰
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTask(Integer taskRoleType, Integer taskStatus, String date) {
        if (date == null) {
            log.info("  =>> 角色 id 为" + taskRoleType + " 新增 " + DateUtil.now() + " 任务:状态为 " + taskStatus + " <<=");
            baseMapper.insert(new CrmDailyTask().setTaskRoleType(taskRoleType.toString()).setTaskStatus(taskStatus).setTaskDate(new Date()));
        } else {
            log.info("  =>> 角色 id 为" + taskRoleType + " 修改 " + date + " 任务:状态为 " + taskStatus + " <<=");
            CrmDailyTask crmDailyTask = Optional.ofNullable(baseMapper.selectOne(new QueryWrapper<CrmDailyTask>().lambda().eq(CrmDailyTask::getTaskDate, date).eq(CrmDailyTask::getTaskRoleType, taskStatus))).orElseThrow(() -> new ServiceException(BadInfo.EMPTY_TASK_TABLE.getInfo()));
            baseMapper.updateById(crmDailyTask.setTaskStatus(taskStatus));
        }
    }

    @Override
    public void checkDailyTask(CrmSupplyTask crmSupplyTask) {
        Long roleId = crmSupplyTask.getRoleId();
        Date taskDate = crmSupplyTask.getTaskDate();
        log.info("  >>>> 完成任务后根据roleId检查并修改每日任务状态,roleId=[{}],taskDate=[{}] <<<<  ",roleId,taskDate);
        List<CrmSupplyTask> crmSupplyTasks = crmSupplyTaskMapper.selectList(new QueryWrapper<CrmSupplyTask>().lambda()
                .eq(CrmSupplyTask::getRoleId, roleId).eq(CrmSupplyTask::getTaskDate, taskDate));
        if (CollectionUtils.isEmpty(crmSupplyTasks)) {
            return;
        }
        List<CrmSupplyTask> collect = crmSupplyTasks.stream().filter(o -> ObjectUtils.isEmpty(o.getState()) || 1 != o.getState()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collect)) {
            return;
        }
        CrmDailyTask crmDailyTask = crmDailyTaskMapper.selectOne(new QueryWrapper<CrmDailyTask>().lambda()
                .eq(CrmDailyTask::getTaskDate, taskDate).eq(CrmDailyTask::getTaskRoleType, roleId).last(" limit 1"));
        crmDailyTask.setTaskStatus(3).setUpdated(new Date());
        crmDailyTaskMapper.updateById(crmDailyTask);
    }


}
