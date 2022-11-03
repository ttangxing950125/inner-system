package com.deloitte.crm.service.impl;

import com.alibaba.excel.util.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.EmailUtil;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.RoleInfo;
import com.deloitte.crm.constants.SuccessInfo;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.mapper.BondsListingLogMapper;
import com.deloitte.crm.mapper.CrmEntityTaskMapper;
import com.deloitte.crm.mapper.CrmMasTaskMapper;
import com.deloitte.crm.mapper.EntityCaptureSpeedMapper;
import com.deloitte.crm.service.EntityCaptureSpeedService;
import com.deloitte.crm.service.ICrmDailyTaskService;
import com.deloitte.crm.service.ICrmEntityTaskService;
import com.deloitte.crm.service.SendEmailService;
import com.deloitte.crm.vo.EmailVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    @Resource
    private EntityCaptureSpeedMapper entityCaptureSpeedMapper;
    @Resource
    private BondsListingLogMapper bondsListingLogMapper;
    @Resource
    private  EmailVo emailVo;
    /**
     * 查询角色7，根据导入的e数据新增主体的任务
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
     * @author 正杰
     * @param taskCategory 捕获渠道
     * @param date 请传入参数 yyyy-mm-dd
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @date 2022/9/22
     * @return R<List<CrmEntityTask>> 当日任务情况
     */
    @Override
    public R<Page<CrmEntityTask>> getTaskInfo(String taskCategory,String date, Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 5 : pageSize;
        QueryWrapper<CrmEntityTask> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CrmEntityTask::getTaskDate, date);

        //添加一条 关于捕获信息的 查询条件
        if(!ObjectUtils.isEmpty(taskCategory)){wrapper.lambda().eq(CrmEntityTask::getTaskCategory,taskCategory);}

        Page<CrmEntityTask> crmEntityTaskPage = baseMapper.selectPage(new Page<>(pageNum, pageSize), wrapper.lambda().orderBy(true, true, CrmEntityTask::getState));
        return R.ok(crmEntityTaskPage, SuccessInfo.GET_SUCCESS.getInfo());
    }

    /**
     * 处理当日任务
     * @param taskId 任务id
     * @param state 0-未处理 1-已有主体未关联 2-新增主体 3-已有主体已关联
     * @param entityCode 主体 code
     * @param remarks 备注信息
     * @return 操作成功与否
     *
     * @param taskId
     * @param state  0-未处理 1-已有主体未关联 2-新增主体 3-已有主体已关联
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R finishTask(Integer taskId, Integer state, String entityCode,String remarks) {
        log.info("  =>> 角色7处理当日任务开始 taskId={}>>entityCode>>>:{} <<=  ", taskId, entityCode);
        CrmEntityTask crmEntityTask = Optional.ofNullable(baseMapper.selectOne(new QueryWrapper<CrmEntityTask>().lambda().eq(CrmEntityTask::getId, taskId))).orElseThrow(() -> new ServiceException(BadInfo.VALID_EMPTY_TARGET.getInfo()));
        Assert.isTrue(crmEntityTask.getState() == 0, BadInfo.EXITS_TASK_FINISH.getInfo());

        // 为状态表中 修改当前状态表数据中的 状态 entity_capture_speed
        EntityCaptureSpeed entityCaptureSpeed = entityCaptureSpeedMapper.selectOne(new QueryWrapper<EntityCaptureSpeed>().lambda().eq(EntityCaptureSpeed::getId, crmEntityTask.getSpeedId()));
        if (ObjectUtils.isEmpty(entityCaptureSpeed)) {
            log.info("  =>> 角色7任务 {} 未查询到关联 entity_capture_speed 表 id为 {} 的数据 <<=  ", taskId, crmEntityTask.getSpeedId());
        } else {
            entityCaptureSpeed.setAdded(state).setEntityCode(entityCode);
            entityCaptureSpeedMapper.updateById(entityCaptureSpeed);
        }

        log.info("  =>> 角色7任务 将任务 {} 的状态更改至 {} <<=  ", taskId, state);
        crmEntityTask.setState(state);
        baseMapper.updateById(crmEntityTask);

        //获取当条任务日期 以及 格式化后的日期
        Date taskDate = crmEntityTask.getTaskDate();
        String date = DateUtils.format(taskDate, "yyyy-MM-dd");
        BaseMapper<CrmDailyTask> crmDailyTaskMapper = crmDailyTaskService.getBaseMapper();

        //  相同主体名称情况下 && 相同任务日期 && 任务状态为 0 对该次生成任务进行忽略
        List<CrmEntityTask> byEntityNameList = Optional.ofNullable(baseMapper.selectList(new QueryWrapper<CrmEntityTask>().lambda().eq(CrmEntityTask::getTaskDate, date).eq(CrmEntityTask::getState, 0).eq(CrmEntityTask::getEntityName, crmEntityTask.getEntityName()))).orElse(new ArrayList<>());
        //如果 entity_code 不为 null ，并且 任务中并未出现相同主体名称的任务，那么就是新增，便给角色 2 新增一条任务
        if (!ObjectUtils.isEmpty(entityCode) && byEntityNameList.size() == 0) {
            //向 crm_mas_task中添加任务
            crmMasTaskMapper.insert(new CrmMasTask().setEntityCode(entityCode).setSourceName(crmEntityTask.getTaskCategory()).setState(0).setSpeedId(crmEntityTask.getSpeedId()).setDetails(crmEntityTask.getDetails()).setRemarks(remarks).setTaskDate(new Date()));
            CrmDailyTask crmDailyTask = crmDailyTaskMapper.selectOne(new QueryWrapper<CrmDailyTask>().lambda().eq(CrmDailyTask::getTaskDate, date).eq(CrmDailyTask::getTaskRoleType,4));
            //向每日任务列表添加任务 角色4 crm_daily_task 中 task_role_type = 4 , task_status = 2
            if (ObjectUtils.isEmpty(crmDailyTask)) {
                crmDailyTaskMapper.insert(new CrmDailyTask().setTaskRoleType("4").setTaskStatus(2).setTaskDate(new Date()));
            } else {
                crmDailyTaskMapper.updateById(crmDailyTask.setTaskStatus(2));
            }
        }

        List<CrmEntityTask> unFinish = Optional.ofNullable(baseMapper.selectList(new QueryWrapper<CrmEntityTask>().lambda().eq(CrmEntityTask::getTaskDate, date).eq(CrmEntityTask::getState, 0))).orElse(new ArrayList<>());
        if (unFinish.size() == 0) {
            log.info("  =>> 角色7任务 {} 的任务已经完成，进行发送邮件以及更改任务状态操作  <<=  ", date);
            //查询日任务 角色7对应的 task_role_type 为 8
            CrmDailyTask crmDailyTask = Optional.ofNullable(crmDailyTaskMapper.selectOne(new QueryWrapper<CrmDailyTask>().lambda().eq(CrmDailyTask::getTaskDate, date).eq(CrmDailyTask::getTaskRoleType, 8))).orElseThrow(() -> new ServiceException(BadInfo.EMPTY_TASK_TABLE.getInfo()));
            // 当日任务处理完毕 状态码为 3
            crmDailyTaskMapper.updateById(crmDailyTask.setTaskStatus(3));

            // 当日是新增主体的数量 状态码为2 代表是新增主体
            List<CrmEntityTask> crmEntityTasks = baseMapper.selectList(new QueryWrapper<CrmEntityTask>().lambda().eq(CrmEntityTask::getState, 2).eq(CrmEntityTask::getTaskDate, date));

            if (crmEntityTasks.size() != 0) {
                //发送邮件 角色2 的 role ID 固定为 4
                asyncSendEmailService(4, crmEntityTasks.size(), date);
            }

            return R.ok(SuccessInfo.SUCCESS.getInfo());
        }
        return R.ok(SuccessInfo.SUCCESS.getInfo());
    }

    @Async
    public void asyncSendEmailService(Integer roleId, Integer taskCount, String date) {
        log.info(">>>>异步发送邮件开始,RoleId:{}新增主体个数：{}", roleId, taskCount);
        sendEmailService.email(roleId, taskCount, date);
    }

    /**
     * 创建任务
     * 测试暂存区
     *
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
        captureSpeed.setCapture(1);
        entityCaptureSpeedService.save(captureSpeed);

        crmEntityTask.setSpeedId(captureSpeed.getId());
        crmEntityTaskMapper.insert(crmEntityTask);

        //修改今天角色6的任务为有任务未处理
        crmDailyTaskService.updateToUnhandled(taskDate, RoleInfo.ROLE6);

        return crmEntityTask;
    }

    /**
     * 发送邮件(今日新发债和股票情况)
     *
     * @return String
     * @author penTang
     * @date 2022/11/2 11:37
     */
    @Override
    public String sendEmail() {
        String result ="";
        try {
            List<CrmEntityTask> list = list();
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
            String date = DateUtil.getDate();
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

            result="邮件发送失败";

        }
        return result;
    }

}
