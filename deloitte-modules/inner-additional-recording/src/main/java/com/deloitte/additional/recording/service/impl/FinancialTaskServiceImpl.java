package com.deloitte.additional.recording.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.constants.Common;
import com.deloitte.additional.recording.domain.*;
import com.deloitte.additional.recording.dto.EntityInfoByFinDto;
import com.deloitte.additional.recording.mapper.*;
import com.deloitte.additional.recording.service.FinancialTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.vo.CreateFinTaskMetaDataVo;
import com.deloitte.additional.recording.vo.EntityInfoByFinVo;
import com.deloitte.additional.recording.vo.FinancialTaskGetTaskDetailVo;
import com.deloitte.additional.recording.vo.GetTaskDataDetailByParamVo;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.system.api.domain.SysDictData;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-26
 */
@Service
@Slf4j
public class FinancialTaskServiceImpl extends ServiceImpl<FinancialTaskMapper, FinancialTask> implements FinancialTaskService {
    @Resource
    private FinancialTableNoteMapper financialTableNoteMapper;
    @Resource
    private SysDictDataMapper sysDictDataMapper;
    @Resource
    private PrsModelQualMapper prsModelQualMapper;
    @Resource
    private FinancialTaskMapper financialTaskMapper;
    @Resource
    private FinancialDataConfigMapper financialDataConfigMapper;
    @Resource
    private BaseFinDataRecordingMapper baseFinDataRecordingMapper;
    @Resource
    private BaseStructuredNotesMapper baseStructuredNotesMapper;
    @Resource
    private EntityInfoMapper entityInfoMapper;
    @Resource
    private  SysUserMapper sysUserMapper;

    /**
     * 三表的数据 生成FinancialTask
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Object cretateFinancialTaskByFinDataRecording() {
        final List<CreateFinTaskMetaDataVo> lists = financialDataConfigMapper.getFinancialDataCreateTask();
        if(CollUtil.isEmpty(lists)){
            log.warn("数据为空不执行FinancialTask任务的生成！！！！");
            return null;
        }
        lists.stream().forEach(e -> {
             FinancialTask financialTask = financialTaskMapper.selectOne(new LambdaQueryWrapper<FinancialTask>()
                    .eq(FinancialTask::getEntityCode, e.getEntityCode())
                    .eq(FinancialTask::getReportTime, e.getReportDate())
                    .eq(FinancialTask::getTableNodeCode, e.getDataTypeCode()));
            if (financialTask == null) {
                /**
                 *  DataType 1-三表 2-结构化附注 3-非结构化附注
                 */
                FinancialTask task = new FinancialTask();
                task.setEntityCode(e.getEntityCode());
                task.setTableNodeCode(e.getDataTypeCode());
                task.setReportTime(DateUtil.format(e.getReportDate(), DatePattern.NORM_DATE_PATTERN));
                task.setTimeValue(e.getYear());
                task.setDataType(1);
                financialTaskMapper.insert(task);
                baseFinDataRecordingMapper.update(null, new LambdaUpdateWrapper<BaseFinDataRecording>()
                                .eq(BaseFinDataRecording::getId,e.getId()).set(BaseFinDataRecording::getIsCreateRecording,1));
            }
        });
        return null;
    }

    /***
     * 结构化附注表 生成FinancialTask
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Object cretateFinancialTaskByStructuredNotes() {
        final List<CreateFinTaskMetaDataVo> lists = financialDataConfigMapper.getStructuredNotesCreateTask();
        if (CollUtil.isEmpty(lists)) {
            log.warn("数据为空不执行FinancialTask任务的生成！！！！");
            return null;
        }
        lists.stream().forEach(e -> {
            FinancialTask financialTask = financialTaskMapper.selectOne(new LambdaQueryWrapper<FinancialTask>()
                    .eq(FinancialTask::getEntityCode, e.getEntityCode())
                    .eq(FinancialTask::getReportTime, e.getReportDate())
                    .eq(FinancialTask::getTableNodeCode, e.getDataTypeCode()));
            if (financialTask == null) {
                /**
                 *  DataType 1-三表 2-结构化附注 3-非结构化附注
                 */
                FinancialTask task = new FinancialTask();
                task.setEntityCode(e.getEntityCode());
                task.setTableNodeCode(e.getDataTypeCode());
                task.setReportTime(DateUtil.format(e.getReportDate(), DatePattern.NORM_DATE_PATTERN));
                task.setTimeValue(e.getYear());
                task.setDataType(2);

                financialTaskMapper.insert(task);

                baseStructuredNotesMapper.update(null, new LambdaUpdateWrapper<BaseStructuredNotes>()
                        .eq(BaseStructuredNotes::getId, e.getId()).set(BaseStructuredNotes::getIsCreateRecording, 1));
            }
        });
        return null;
    }

    /**
     *
     * @param taskId
     * @param code
     * @param reportDate
     * @param dataTypeCode
     * @return
     */
    @Override
    public R getTaskDetail(Integer taskId, String entityCode, String reportDate, String dataTypeCode) {
        FinancialTaskGetTaskDetailVo resultVo=new FinancialTaskGetTaskDetailVo();
        final FinancialTask task = this.financialTaskMapper.selectOne(new LambdaQueryWrapper<FinancialTask>().eq(FinancialTask::getId, taskId));
        Optional.ofNullable(task).orElseThrow(() -> new ServiceException("任务Id为" + taskId + "的任务不存在"));
        resultVo.setTask(task);
        final EntityInfo entityInfo = this.entityInfoMapper.selectOne(new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getEntityCode, task.getEntityCode()));
        Optional.ofNullable(entityInfo).orElseThrow(() -> new ServiceException("主体编码为:" + task.getEntityCode() + "主体信息不存在"));
        java.lang.String[] strings = {Common.DICTTYPE_REPORTTYPE, Common.DICTTYPE_RDATAFREQUENCY};
        List<SysDictData> sysDictDataList = sysDictDataMapper.selectList(new LambdaQueryWrapper<SysDictData>().in(SysDictData::getDictType, strings));
        //reportType & dataFrequency
        final Map<String, List<SysDictData>> groupByDictType = sysDictDataList.stream().collect(Collectors.groupingBy(SysDictData::getDictType));
        resultVo.setGroupByDictType(groupByDictType);

        List<FinancialTableNote> filterCollRuleNotEmptyList = financialTableNoteMapper.selectList(null).stream().filter(e -> StringUtils.isNotEmpty(e.getCollRule())).collect(Collectors.toList());

        if (CollUtil.isNotEmpty(filterCollRuleNotEmptyList)) {
            List<PrsModelQual> collRuleList = filterCollRuleNotEmptyList.stream().map(e -> CompletableFuture.supplyAsync(() -> {
                final PrsModelQual prsModelQual = this.prsModelQualMapper.selectOne(new LambdaQueryWrapper<PrsModelQual>().eq(PrsModelQual::getQualCode, e.getCollRule()));
                return prsModelQual;
            })).map(CompletableFuture::join).collect(Collectors.toList());
            resultVo.setCollRuleList(collRuleList);
        }
        //查询合并报表，年度数据
        List<GetTaskDataDetailByParamVo> taskDataDetailByParam = financialDataConfigMapper.getTaskDataDetailByParam(entityCode, reportDate, dataTypeCode);
        resultVo.setTaskDataDetailByPara(taskDataDetailByParam);
        return R.ok(resultVo);
    }

    @Override
    public R getDataYear() {
        LambdaQueryWrapper<SysDictData> dataYear = new LambdaQueryWrapper<SysDictData>().eq(SysDictData::getDictType, "dataYear");
        List<SysDictData> sysDictData = sysDictDataMapper.selectList(dataYear);
        return R.ok(sysDictData);
    }


    @Override
    public R getFinalEntityView(EntityInfoByFinDto entityInfoByFinDto){
        Page<EntityInfoByFinVo> Page = new Page<>(entityInfoByFinDto.getPageNum(), entityInfoByFinDto.getPageSize());
        List<EntityInfoByFinVo> entityFinalView = financialTaskMapper.getEntityFinalView(Page,entityInfoByFinDto);
        for (EntityInfoByFinVo entityInfoByFinVo : entityFinalView) {
            SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getId, entityInfoByFinVo.getCollId()));
            entityInfoByFinVo.setCollName(sysUser.getName());
            SysUser sysUser1 = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getId, entityInfoByFinVo.getApproverId()));
            entityInfoByFinVo.setApprover(sysUser1.getName());
        }
        return  R.ok(entityFinalView);

    }
}
