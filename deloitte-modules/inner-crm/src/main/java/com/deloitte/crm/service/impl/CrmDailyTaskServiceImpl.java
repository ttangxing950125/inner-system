package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.domain.CrmDailyTask;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.mapper.CrmDailyTaskMapper;
import com.deloitte.crm.service.ICrmDailyTaskService;
import com.deloitte.system.api.RemoteUserService;
import com.deloitte.system.api.RoleService;
import com.deloitte.system.api.domain.SysDictData;
import com.deloitte.system.api.domain.SysRole;
import com.deloitte.system.api.model.LoginUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.role;

/**
 * 每日生产角色任务的service业务实现层
 *
 * @author PenTang
 * @date 2022/09/22 15:35
 */
@Service
public class CrmDailyTaskServiceImpl extends ServiceImpl<CrmDailyTaskMapper, CrmDailyTask> implements ICrmDailyTaskService {

    @Resource
    private CrmDailyTaskMapper mapper;
    @Resource
    public RoleService roleService;


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
        roles.forEach(o -> {
            boolean contains = roleKey.contains(o);
            if (contains) {
                SysRole sysRole = roleService.selectRoleList().stream()
                        .filter(row -> row.getRoleKey().equals(o))
                        .collect(Collectors.toList())
                        .get(0);
                mapper.selectCrmDailyTaskListByDate(startDate, endTime, sysRole.getRoleId().intValue());

            }

        });
        return null;
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
     * 根据当天时间和角色1的type更新成有任务未完成(2)
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

}
