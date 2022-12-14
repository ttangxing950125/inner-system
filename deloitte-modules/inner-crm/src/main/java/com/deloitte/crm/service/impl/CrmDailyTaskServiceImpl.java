package com.deloitte.crm.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.core.utils.EmailUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.RoleInfo;
import com.deloitte.crm.domain.BondsListingLog;
import com.deloitte.crm.domain.CrmDailyTask;
import com.deloitte.crm.domain.CrmEntityTask;
import com.deloitte.crm.domain.CrmSupplyTask;
import com.deloitte.crm.mapper.BondsListingLogMapper;
import com.deloitte.crm.mapper.CrmDailyTaskMapper;
import com.deloitte.crm.mapper.CrmEntityTaskMapper;
import com.deloitte.crm.mapper.CrmSupplyTaskMapper;
import com.deloitte.crm.service.ICrmDailyTaskService;
import com.deloitte.crm.utils.TimeFormatUtil;
import com.deloitte.crm.vo.EmailVo;
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

    @Resource
    private CrmEntityTaskMapper crmEntityTaskMapper;

    @Resource
    private BondsListingLogMapper bondsListingLogMapper;

    @Resource
    private EmailVo emailVo;

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

    /**
     * 检验是否更新每日任务表以及是否发送邮件
     *
     * @param crmSupplyTask
     * @return void
     * @author 冉浩岑
     * @date 2022/11/3 16:58
     */
    @Override
    public void checkDailyTask(CrmSupplyTask crmSupplyTask) {

        //完成任务前先检查一下角色3任务是否全部完成，如果都完成，则不需要修改每日任务状态
        QueryWrapper<CrmSupplyTask> query = new QueryWrapper<>();
        query.lambda().eq(CrmSupplyTask::getTaskDate, crmSupplyTask.getTaskDate()).eq(CrmSupplyTask::getRoleId, crmSupplyTask.getRoleId()).and(wrapper -> wrapper.eq(CrmSupplyTask::getState, 0).or().isNull(CrmSupplyTask::getState));
        Long count = crmSupplyTaskMapper.selectCount(query);
        //完成任务前先检查一下角色3.4.5任务是否全部完成，如果都完成，则不需要发送邮件
        query.clear();
        query.lambda().eq(CrmSupplyTask::getTaskDate, crmSupplyTask.getTaskDate()).and(wrapper -> wrapper.eq(CrmSupplyTask::getState, 0).or().isNull(CrmSupplyTask::getState));
        Long allCount = crmSupplyTaskMapper.selectCount(query);

        completeTaskById(crmSupplyTask.getId());

        //如果当前角色已经完成过一次所有任务，则更新每日任务表
        if (count < 1) {
            Long roleId = crmSupplyTask.getRoleId();
            Date taskDate = crmSupplyTask.getTaskDate();
            log.info("  >>>> 完成任务后根据roleId检查并修改每日任务状态,roleId=[{}],taskDate=[{}] <<<<  ", roleId, taskDate);
            query.clear();
            query.lambda().eq(CrmSupplyTask::getRoleId, roleId).eq(CrmSupplyTask::getTaskDate, taskDate);
            List<CrmSupplyTask> crmSupplyTasks = crmSupplyTaskMapper.selectList(query);
            if (CollectionUtils.isEmpty(crmSupplyTasks)) {
                return;
            }
            List<CrmSupplyTask> collect = crmSupplyTasks.stream().filter(o -> ObjectUtils.isEmpty(o.getState()) || 1 != o.getState()).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)) {
                return;
            }
            //修改每日任务进度
            CrmDailyTask crmDailyTask = crmDailyTaskMapper.selectOne(new QueryWrapper<CrmDailyTask>().lambda()
                    .eq(CrmDailyTask::getTaskDate, taskDate).eq(CrmDailyTask::getTaskRoleType, roleId).last(" limit 1"));
            crmDailyTask.setTaskStatus(3).setUpdated(new Date());
            crmDailyTaskMapper.updateById(crmDailyTask);
        }

        //发送邮件
        if (allCount < 0) {
            query.clear();
            query.lambda().eq(CrmSupplyTask::getTaskDate, crmSupplyTask.getTaskDate()).and(wrapper -> wrapper.eq(CrmSupplyTask::getState, 0).or().isNull(CrmSupplyTask::getState));
            allCount = crmSupplyTaskMapper.selectCount(query);
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public void completeTaskById(Integer id) {
        log.info("  >>>> 修改任务，任务ID:  "+id+" ,为完成状态  <<<<  ");
        CrmSupplyTask crmSupplyTask = new CrmSupplyTask();
        crmSupplyTask.setId(id);
        crmSupplyTask.setState(1);
        crmSupplyTask.setHandleUser(SecurityUtils.getUsername());
        crmSupplyTask.setUpdated(new Date());
        crmSupplyTaskMapper.updateById(crmSupplyTask);
    }

    /**
     * 发送邮件(今日新发债和股票情况)
     *
     * @return String
     * @author penTang
     * @date 2022/11/2 11:37
     */
    @Override
    public String sendEmail(String date) {
        String result ="";
        try {
            List<CrmEntityTask> list = crmEntityTaskMapper.selectList(new QueryWrapper<>());
            //新增主体
            List<CrmEntityTask> addEntity = list.stream().filter(row -> row.getState() == 2).collect(Collectors.toList());
            //a股检测
            List<CrmEntityTask> collectA = list.stream().filter(row -> row.getState() == 2 && row.getSourceType() == 3).collect(Collectors.toList());
            //港股券
            List<CrmEntityTask> collectG = list.stream().filter(row -> row.getState() == 2 && row.getSourceType() == 2).collect(Collectors.toList());
            //债券
            List<CrmEntityTask> collectZ = list.stream().filter(row -> row.getState() == 2 && row.getSourceType() == 1).collect(Collectors.toList());
            //标题
            String title = "今日平台新增主体" + addEntity.size() + "个，其中a股检测到" + collectA.size() + "个，港股检测到" + collectG.size() + "个，债券检测到" + collectZ.size() + "个";
            LambdaQueryWrapper<BondsListingLog> qw = new LambdaQueryWrapper<BondsListingLog>().eq(BondsListingLog::getRecordTime, date);
            List<BondsListingLog> bondsListingLogs = bondsListingLogMapper.selectList(qw);
            List<BondsListingLog> bonds = bondsListingLogs.stream().filter(row -> row.getSourceType() == 1).collect(Collectors.toList());
            List<BondsListingLog> stocks = bondsListingLogs.stream().filter(row -> row.getSourceType() != 1).collect(Collectors.toList());
            //画表格
            StringBuffer content= new StringBuffer();
            if (bonds.size() != 0) {
                content.append("<h2>今日新发债情况如下:</h2>");
                content.append("<table border=\"1\" style=\"border:solid 1px #E8F2F9;font-size=12px;;font-size:18px;\">");
                content.append("<tr style=\"background-color: #428BCA; color:#ffffff\"><th>债券简称</th><th>债券全称</th><th>债券code</th><th>发行日期</th><th>发行人</th></tr>");
                for (BondsListingLog row : bonds) {
                    content.append("<tr>");
                    //债券简称
                    content.append("<td align=\"center\">" + row.getName() + "</td>");
                    //债券全称
                    content.append("<td align=\"center\">" + row.getShortName() + "</td>");
                    //债券code
                    content.append("<td align=\"center\">" + row.getCode() + "</td>");
                    //发行日期
                    content.append("<td align=\"center\">" + row.getIssueDate() + "</td>");
                    //发行人
                    content.append("<td align=\"center\">" + row.getPublisher() + "</td>");
                    content.append("</tr>");
                }
                content.append("</table>");
            } else {
                content.append("<h2 style=\" font-size: 14px;\">今日新发债情况如下:暂无数据</h2>");
            }

            if (stocks.size() != 0) {
                content.append("<h2>今日股票情况如下:</h2>");
                content.append("<table border=\"1\" style=\"border:solid 1px #E8F2F9;font-size=12px;;font-size:18px;\">");
                content.append("<tr style=\"background-color: #428BCA; color:#ffffff\"><th>股票简称</th><th>股票全称</th><th>股票code</th><th>发行日期</th><th>发行人</th><th>股票类型</th></tr>");
                for (BondsListingLog stock : stocks) {
                    content.append("<tr>");
                    //股票简称
                    content.append("<td align=\"center\">" + stock.getName() + "</td>");
                    //股票全称
                    content.append("<td align=\"center\">" + stock.getShortName() + "</td>");
                    //股票code
                    content.append("<td align=\"center\">" + stock.getCode() + "</td>");
                    //发行日期
                    content.append("<td align=\"center\">" + stock.getIpoDate() + "</td>");
                    //发行人
                    content.append("<td align=\"center\">" + stock.getPublisher() + "</td>");
                    //股票类型
                    String s =  stock.getSourceType()==2 ? "港股":"A股";
                    content.append("<td align=\"center\">" + s + "</td>");
                    content.append("</tr>");
                }
                content.append("</table>");
            } else {
                content.append("<h2 style=\" font-size: 14px;\">今日股票情况如下:暂无数据</h2>");

            }

            List<String> passwords = emailVo.getPasswords();

            for (String s : passwords) {
                EmailUtil.sendTemplateEmail(title, content.toString(), s);
            }
            result ="邮件发送成功";

        }catch(Exception e){
            e.printStackTrace();
            result="邮件发送失败";

        }
        return result;
    }

}
