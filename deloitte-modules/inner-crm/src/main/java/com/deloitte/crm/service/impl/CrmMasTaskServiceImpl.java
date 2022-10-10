package com.deloitte.crm.service.impl;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.RoleInfo;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.constants.SuccessInfo;
import com.deloitte.crm.domain.CrmDailyTask;
import com.deloitte.crm.domain.CrmEntityTask;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.mapper.EntityInfoMapper;
import com.deloitte.crm.service.ICrmDailyTaskService;
import com.deloitte.crm.service.ICrmDailyTaskService;
import com.deloitte.crm.service.IEntityInfoService;
import com.deloitte.crm.vo.CrmMasTaskVo;
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

    private ICrmDailyTaskService dailyTaskService;

    private IEntityInfoService iEntityInfoService;

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
    public R<List<CrmMasTaskVo>> getTaskInfo(String timeUnit, String date) {
        List<CrmMasTaskVo> res = new ArrayList<>();
        switch (timeUnit) {
            case Common.DAY:
                Date dateDay = DateUtil.parseDate(date);
                List<CrmMasTask> crmMasTasks = baseMapper.selectList(new QueryWrapper<CrmMasTask>()
                        .lambda().eq(CrmMasTask::getTaskDate, dateDay));
                crmMasTasks.forEach(row->{
                    CrmMasTaskVo crmMasTaskVo = new CrmMasTaskVo();
                    EntityInfo entityInfo = iEntityInfoService.getBaseMapper().selectOne(new QueryWrapper<EntityInfo>().lambda()
                            .eq(EntityInfo::getEntityCode, row.getEntityCode()));
                    BeanUtil.copyProperties(row,crmMasTaskVo);
                    crmMasTaskVo.setEntityName(entityInfo.getEntityName());
                    crmMasTaskVo.setCreditCode(entityInfo.getCreditCode());
                    res.add(crmMasTaskVo);
                });
                return R.ok(res, SuccessInfo.GET_SUCCESS.getInfo());
            case Common.MOUTH:
                date +="-01";
                Date dateTime = DateUtil.parseDate(date);
                Date first = DateUtil.beginOfMonth(dateTime);
                Date last = DateUtil.endOfMonth(dateTime);
                List<CrmMasTask> mouthList = baseMapper.selectCrmMasTaskListThisMouth(first, last);
                mouthList.forEach(item->{
                    CrmMasTaskVo crmMasTaskVo = new CrmMasTaskVo();
                    EntityInfo entityInfo = iEntityInfoService.getBaseMapper().selectOne(new QueryWrapper<EntityInfo>().lambda()
                            .eq(EntityInfo::getEntityCode, item.getEntityCode()));
                    BeanUtil.copyProperties(item,crmMasTaskVo);
                    crmMasTaskVo.setEntityName(entityInfo.getEntityName());
                    crmMasTaskVo.setCreditCode(entityInfo.getCreditCode());
                    res.add(crmMasTaskVo);
                });
                return R.ok(res, SuccessInfo.GET_SUCCESS.getInfo());
            default:
                return R.fail(BadInfo.PARAM_EMPTY.getInfo());
        }
    }

}
