package com.deloitte.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.RoleInfo;
import com.deloitte.crm.constants.SuccessInfo;
import com.deloitte.crm.domain.CrmMasTask;
import com.deloitte.crm.domain.EntityCaptureSpeed;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.domain.EntityMaster;
import com.deloitte.crm.mapper.CrmMasTaskMapper;
import com.deloitte.crm.mapper.EntityCaptureSpeedMapper;
import com.deloitte.crm.mapper.EntityMasterMapper;
import com.deloitte.crm.service.ICrmDailyTaskService;
import com.deloitte.crm.service.ICrmMasTaskService;
import com.deloitte.crm.service.IEntityInfoService;
import com.deloitte.crm.vo.CrmMasTaskVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Service
@AllArgsConstructor
@Slf4j
public class CrmMasTaskServiceImpl extends ServiceImpl<CrmMasTaskMapper, CrmMasTask> implements ICrmMasTaskService {
    private final CrmMasTaskMapper crmMasTaskMapper;

    private ICrmDailyTaskService dailyTaskService;

    private IEntityInfoService iEntityInfoService;

    private EntityMasterMapper entityMasterMapper;

    private EntityCaptureSpeedMapper entityCaptureSpeedMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public CrmMasTask selectCrmMasTaskById(Integer id) {
        return crmMasTaskMapper.selectCrmMasTaskById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param crmMasTask 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<CrmMasTask> selectCrmMasTaskList(CrmMasTask crmMasTask) {
        return crmMasTaskMapper.selectCrmMasTaskList(crmMasTask);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param crmMasTask 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertCrmMasTask(CrmMasTask crmMasTask) {
        return crmMasTaskMapper.insertCrmMasTask(crmMasTask);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param crmMasTask 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateCrmMasTask(CrmMasTask crmMasTask) {
        return crmMasTaskMapper.updateCrmMasTask(crmMasTask);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCrmMasTaskByIds(Long[] ids) {
        return crmMasTaskMapper.deleteCrmMasTaskByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCrmMasTaskById(Long id) {
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
        if (CollUtil.isEmpty(entityInfos)) {
            return false;
        }

        List<CrmMasTask> masTasks = entityInfos.stream()
                .filter((item -> {
                    EntityMaster entityMaster = entityMasterMapper.selectOne(
                            Wrappers.<EntityMaster>lambdaQuery()
                                    .eq(EntityMaster::getEntityCode, item.getEntityCode())
                    );

                    return entityMaster == null;
                })).map(item -> {

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
     *
     * @param date 请传入参数 yyyy-MM
     * @return R<Page < CrmMasTaskVo>> 当日任务
     * @author 正杰
     * @date 2022/9/27
     */
    @Override
    public R<Page<CrmMasTaskVo>> getTaskInfo(String date, Integer pageNum, Integer pageSize) {
        log.info("  =>> 角色2 " + date + " 查询 <<=  ");
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 5 : pageSize;
        List<CrmMasTaskVo> res = new ArrayList<>();
        Date dateDay = DateUtil.parseDate(date);
        Page<CrmMasTask> crmMasTaskPage = baseMapper.selectPage(new Page<>(pageNum, pageSize), new QueryWrapper<CrmMasTask>()
                .lambda().eq(CrmMasTask::getTaskDate, dateDay).orderBy(true,true,CrmMasTask::getState));
        List<CrmMasTask> crmMasTasks = crmMasTaskPage.getRecords();

        Page<CrmMasTaskVo> result = new Page<>(pageNum, pageSize, crmMasTaskPage.getTotal());
        ArrayList<CrmMasTaskVo> crmMasTaskVos = new ArrayList<>();
        crmMasTasks.forEach(row -> {
            CrmMasTaskVo crmMasTaskVo = new CrmMasTaskVo();
            BeanUtil.copyProperties(row, crmMasTaskVo);
            if (row.getEntityCode() != null) {
                EntityInfo entityInfo = iEntityInfoService.getBaseMapper().selectOne(new QueryWrapper<EntityInfo>().lambda()
                        .eq(EntityInfo::getEntityCode, row.getEntityCode()));
                crmMasTaskVo.setEntityName(entityInfo.getEntityName());
                crmMasTaskVo.setCreditCode(entityInfo.getCreditCode());
            } else {
                log.warn("  =>> 角色2 出现无效信息 taskId = " + row.getId() + " entity_code 为空  <<=  !!!");
            }
            crmMasTaskVos.add(crmMasTaskVo);
        });
        result.setRecords(crmMasTaskVos);
        log.info("  =>> 角色2 查询结束  <<=  ");
        return R.ok(result, SuccessInfo.GET_SUCCESS.getInfo());

    }

    /**
     * 角色2 完成任务
     *
     * @param taskId
     * @param username
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Date finishTask(Integer taskId, String username) {
        CrmMasTask crmMasTask = Optional.ofNullable(baseMapper.selectCrmMasTaskById(taskId)).orElseThrow(() -> new ServiceException(BadInfo.EMPTY_TASK_TABLE.getInfo()));
        Assert.isTrue(crmMasTask.getState() == 0, BadInfo.EXITS_TASK_FINISH.getInfo());
        crmMasTask.setState(1).setHandleUser(username);
        baseMapper.updateById(crmMasTask);
        // 为状态表中 修改当前状态表数据中的 状态 entity_capture_speed
        EntityCaptureSpeed entityCaptureSpeed = entityCaptureSpeedMapper.selectOne(new QueryWrapper<EntityCaptureSpeed>().lambda().eq(EntityCaptureSpeed::getId, crmMasTask.getSpeedId()));
        if(ObjectUtils.isEmpty(entityCaptureSpeed)){
            log.info("  =>> 角色2任务 {} 未查询到关联 entity_capture_speed 表 id为 {} 的数据",taskId,crmMasTask.getSpeedId());
        }else{
            entityCaptureSpeed.setDivide(1);
            entityCaptureSpeedMapper.insert(entityCaptureSpeed);
        }
        return crmMasTask.getTaskDate();
    }
}
