package com.deloitte.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.constants.RoleInfo;
import com.deloitte.crm.constants.SuccessInfo;
import com.deloitte.crm.domain.CrmDailyTask;
import com.deloitte.crm.domain.CrmEntityTask;
import com.deloitte.crm.mapper.CrmEntityTaskMapper;
import com.deloitte.crm.service.ICrmDailyTaskService;
import com.deloitte.crm.service.ICrmEntityTaskService;
import com.deloitte.crm.vo.CrmEntityTaskVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class CrmEntityTaskServiceImpl extends ServiceImpl<CrmEntityTaskMapper, CrmEntityTask> implements ICrmEntityTaskService {
    @Resource
    private CrmEntityTaskMapper crmEntityTaskMapper;

    @Resource
    private ICrmDailyTaskService crmDailyTaskService;

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
     * @param date
     * @return R<List < CrmEntityTask>> 当月任务情况
     * @author 正杰
     * @date 2022/9/22
     */
    @Override
    public R<List<CrmEntityTaskVo>> getTaskInfo(String timeUnit, String date) {
        switch (timeUnit) {
            case Common.DAY:
                Date dateDay = DateUtil.parseDate(date);
                List<CrmEntityTask> res = baseMapper.selectList(new QueryWrapper<CrmEntityTask>()
                        .lambda().eq(CrmEntityTask::getTaskDate, dateDay));

                List<CrmEntityTaskVo> result = new ArrayList<>();
                res.forEach(row -> {
                    String dataShow = row.getDataShow();
                    //dataShow中的数据 固定格式 以 ，拼接 公司名称以及代码
                    String[] split = dataShow.split(", ");
                    //每个值 中间以 : 隔开 例如 dataShow = 公司中文名称:新疆格瑞迪斯石油技术股份有限公司, 代码:A15202.SZ
                    String bondName = split[0].split(":")[1];
//                    String bondCode = split[1].split(":")[1];

                    CrmEntityTaskVo crmEntityTaskVo = new CrmEntityTaskVo();
                    BeanUtil.copyProperties(row, crmEntityTaskVo);
                    crmEntityTaskVo.setBondFullName(bondName);
//                    crmEntityTaskVo.setCreditCode(entityName);
                    result.add(crmEntityTaskVo);
                });
                return R.ok(result, SuccessInfo.GET_SUCCESS.getInfo());
            case Common.MOUTH:
                date += "-01";
                Date dateTime = DateUtil.parseDate(date);
                Date first = DateUtil.beginOfMonth(dateTime);
                Date last = DateUtil.endOfMonth(dateTime);
                List<CrmEntityTask> mouth = crmEntityTaskMapper.selectCrmEntityTaskListThisMouth(first, last);
                List<CrmEntityTaskVo> mouthList = new ArrayList<>();
                mouth.forEach(row -> {
                    CrmEntityTaskVo crmEntityTaskVo = new CrmEntityTaskVo();
                    BeanUtil.copyProperties(row, crmEntityTaskVo);
                    mouthList.add(crmEntityTaskVo);
                });
                return R.ok(mouthList, SuccessInfo.GET_SUCCESS.getInfo());
            default:
                return R.fail(BadInfo.PARAM_EMPTY.getInfo());
        }
    }

    /**
     * 处理当日任务
     *
     * @param taskId
     * @param state
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R finishTask(Integer taskId, Integer state) {
        CrmEntityTask crmEntityTask = baseMapper.selectOne(new QueryWrapper<CrmEntityTask>().lambda().eq(CrmEntityTask::getId, taskId));
        if (crmEntityTask == null) {
            return R.fail(BadInfo.VALID_EMPTY_TARGET.getInfo());
        } else if (crmEntityTask.getState() != 0) {
            return R.fail(BadInfo.EXITS_TASK_FINISH.getInfo());
        }
        crmEntityTask.setState(state);
        baseMapper.updateById(crmEntityTask);
        Date taskDate = crmEntityTask.getTaskDate();

        List<CrmEntityTask> unFinish = baseMapper
                .selectList(new QueryWrapper<CrmEntityTask>().lambda()
                        .eq(CrmEntityTask::getTaskDate, taskDate)
                        .eq(CrmEntityTask::getState, 0));
        if (unFinish.size() == 0) {
            //查询日任务 角色7对应的 task_role_type 为 8
            CrmDailyTask crmDailyTask = crmDailyTaskService.getBaseMapper().selectOne(new QueryWrapper<CrmDailyTask>()
                    .lambda().eq(CrmDailyTask::getTaskDate, taskDate).eq(CrmDailyTask::getTaskRoleType, 8));
            if (crmDailyTask == null) {
                return R.fail(BadInfo.EMPTY_TASK_TABLE.getInfo());
            }
            // 当日任务处理完毕 状态码为 3
            crmDailyTask.setTaskStatus(3);
            crmDailyTaskService.getBaseMapper().updateById(crmDailyTask);
            return R.ok(SuccessInfo.SUCCESS.getInfo());
        }
        return R.ok(SuccessInfo.SUCCESS.getInfo());
    }


    /**
     * 创建任务
     *
     * @param crmEntityTask
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CrmEntityTask createTask(CrmEntityTask crmEntityTask) {
        //当前日期
        Date taskDate = crmEntityTask.getTaskDate();

        crmEntityTaskMapper.insert(crmEntityTask);


        //修改今天角色6的任务为有任务未处理
        crmDailyTaskService.updateToUnhandled(taskDate, RoleInfo.ROLE6);

        return crmEntityTask;
    }

}
