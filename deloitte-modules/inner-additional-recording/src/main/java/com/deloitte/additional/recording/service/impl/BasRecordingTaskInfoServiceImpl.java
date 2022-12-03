package com.deloitte.additional.recording.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.constants.Common;
import com.deloitte.additional.recording.domain.BasRecordingLableEntity;
import com.deloitte.additional.recording.domain.BasRecordingTaskInfo;
import com.deloitte.additional.recording.domain.EntityInfo;
import com.deloitte.additional.recording.domain.SysUser;
import com.deloitte.additional.recording.dto.RecordingTaskInfoGetInfoReqDto;
import com.deloitte.additional.recording.dto.RecordingTaskSubmitDto;
import com.deloitte.additional.recording.mapper.*;
import com.deloitte.additional.recording.service.BasRecordingTaskInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.service.SysUserService;
import com.deloitte.additional.recording.vo.recording.ChooseDistributionPeriodCollocterVo;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.system.api.domain.SysDictData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * <p>
 * 中间补录层任务表 服务实现类
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-21
 */
@Slf4j
@Service
public class BasRecordingTaskInfoServiceImpl extends ServiceImpl<BasRecordingTaskInfoMapper, BasRecordingTaskInfo> implements BasRecordingTaskInfoService {
    @Resource
    private BasRecordingTaskInfoMapper basRecordingTaskInfoMapper;
    @Resource
    private EntityInfoMapper entityInfoMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysDictDataMapper sysDictDataMapper;
    @Resource
    private BasRecordingLableEntityMapper basRecordingLableEntityMapper;
    @Resource(name = "commonTaskExecutor")
    private ThreadPoolTaskExecutor commonTaskExecutor;


    /**
     * 获取补录任务列表
     *
     * @param requestParam RecordingTaskInfoGetInfoReqDto
     * @return Page<BasRecordingTaskInfo>
     * @see RecordingTaskInfoGetInfoReqDto
     */
    @Override
    public Page<BasRecordingTaskInfo> getTaskInfoByParamPage(RecordingTaskInfoGetInfoReqDto requestParam) {
        Page<BasRecordingTaskInfo> page = new Page<BasRecordingTaskInfo>(requestParam.getPageNum(), requestParam.getPagesize());
        IPage<BasRecordingTaskInfo> listPage = basRecordingTaskInfoMapper.getTaskInfoByParamPage(page, requestParam);
        List<BasRecordingTaskInfo> records = listPage.getRecords();
        if (CollUtil.isEmpty(records)) {
            return page;
        }
        List<BasRecordingTaskInfo> collect = records.stream().map(e -> {
            e.setEntityName(Optional.ofNullable(entityInfoMapper.selectOne(new LambdaQueryWrapper<EntityInfo>()
                    .eq(EntityInfo::getEntityCode, e.getEntityCode())))
                    .map(EntityInfo::getEntityName).orElse(""));
            e.setPeriodApproverName(Optional.ofNullable(sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                    .eq(SysUser::getId, e.getPeriodApprover())))
                    .map(SysUser::getName).orElse(""));
            e.setPeriodCollocterName(Optional.ofNullable(sysUserMapper.selectById(e.getPeriodCollocter()))
                    .map(SysUser::getName).orElse(""));
            e.setPeriodReportStatusName(Optional.of(sysDictDataMapper.selectOne(new LambdaQueryWrapper<SysDictData>()
                    .eq(SysDictData::getDictType, Common.SYS_DICT_TYPE_COLLSTAT)
                    .eq(SysDictData::getDictValue, e.getPeriodReportStatus())))
                    .map(SysDictData::getDictLabel).orElse(""));
            return e;
        }).collect(Collectors.toList());
        listPage.setRecords(collect);
        return page;
    }

    /**
     * 选择人员
     *
     * @return
     */
    @Override
    public Object chooseDistribution() {
        Map<String, Object> maps = new HashMap<>();
        List<ChooseDistributionPeriodCollocterVo> periodCollocterVoList = sysUserMapper.chooseDistributionPeriodCollocter("补录人员");
        log.info("查询补录人员条数 >>>>:{}",periodCollocterVoList.size());
        if (CollUtil.isNotEmpty(periodCollocterVoList)) {
            long start = System.currentTimeMillis();
            log.info("统计补录人员任务数 开始 >>>>:{}",start);
            final List<ChooseDistributionPeriodCollocterVo> newCollect = periodCollocterVoList.stream().map(e->CompletableFuture.supplyAsync(()->{
                    /**
                     * {@link BasRecordingTaskInfo#getPeriodReportStatus()}
                     */
                    List<BasRecordingTaskInfo> periodCollocterTaskCountList = basRecordingTaskInfoMapper.selectList(new LambdaQueryWrapper<BasRecordingTaskInfo>().
                            eq(BasRecordingTaskInfo::getPeriodReportStatus, 1)
                            .eq(BasRecordingTaskInfo::getPeriodCollocter, e.getSysUserId()));
                    List<BasRecordingTaskInfo> PeriodApproverTaskCountList = basRecordingTaskInfoMapper.selectList(new LambdaQueryWrapper<BasRecordingTaskInfo>().
                            eq(BasRecordingTaskInfo::getPeriodReportStatus, 3)
                            .eq(BasRecordingTaskInfo::getPeriodApprover, e.getSysUserId()));
                    e.setPeriodCollocterTaskCount(periodCollocterTaskCountList.size());
                    e.setPeriodApproverTaskCount(PeriodApproverTaskCountList.size());
                    return e;
            },commonTaskExecutor)).map(CompletableFuture::join).collect(Collectors.toList());

            log.info("统计补录人员任务数 结束 耗时 >>>>:{} ms", (System.currentTimeMillis()-start));
            maps.put("periodCollocter", newCollect);
        }
        List<ChooseDistributionPeriodCollocterVo> periodApproverVoList = sysUserMapper.chooseDistributionPeriodCollocter("审核人员");
        log.info("查询审核人员条数 >>>>:{}",periodApproverVoList.size());
        if (CollUtil.isNotEmpty(periodApproverVoList)) {
            long start = System.currentTimeMillis();
            log.info("统计审核人员任务数 开始 >>>>:{}",start);
            final List<ChooseDistributionPeriodCollocterVo> newCollect = periodApproverVoList.stream().map(e -> CompletableFuture.supplyAsync(()->{
                    /**
                     * {@link BasRecordingTaskInfo#getPeriodReportStatus()}
                     */
                    List<BasRecordingTaskInfo> periodCollocterTaskCountList = basRecordingTaskInfoMapper.selectList(new LambdaQueryWrapper<BasRecordingTaskInfo>().
                            eq(BasRecordingTaskInfo::getPeriodReportStatus, 1)
                            .eq(BasRecordingTaskInfo::getPeriodCollocter, e.getSysUserId()));
                    List<BasRecordingTaskInfo> PeriodApproverTaskCountList = basRecordingTaskInfoMapper.selectList(new LambdaQueryWrapper<BasRecordingTaskInfo>().
                            eq(BasRecordingTaskInfo::getPeriodReportStatus, 3)
                            .eq(BasRecordingTaskInfo::getPeriodApprover, e.getSysUserId()));
                    e.setPeriodCollocterTaskCount(periodCollocterTaskCountList.size());
                    e.setPeriodApproverTaskCount(PeriodApproverTaskCountList.size());
                    return e;
            },commonTaskExecutor)).map(CompletableFuture::join).collect(Collectors.toList());
            log.info("统计审核人员任务数 结束 耗时 >>>>:{} ms", (System.currentTimeMillis()-start));
            maps.put("periodApprover", newCollect);
        }
        return maps;

    }

    /**
     * 提交分配
     * @param submitDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public R taskSubmit(List<RecordingTaskSubmitDto> lists) {
        log.info("==> 提交分配 请求参数:{}", JSON.toJSONString(lists));
        for (RecordingTaskSubmitDto submitDto : lists) {
            Integer taskId = submitDto.getTaskId();
            BasRecordingTaskInfo taskInfo = basRecordingTaskInfoMapper.selectOne(new LambdaQueryWrapper<BasRecordingTaskInfo>().eq(BasRecordingTaskInfo::getId, taskId));
            log.info("==> 根据任务Id:{} 查询任务详情-->:{}", JSON.toJSONString(taskInfo));
            if (taskInfo == null || taskInfo.getPeriodReportStatus() == 3 || taskInfo.getPeriodReportStatus() == 4) {
                throw new ServiceException("任务ID为:" + taskId + "不存在或者状态已经为提交审核");
            }

            taskInfo.setPeriodApprover(submitDto.getPeriodApprover());
            taskInfo.setPeriodCollocter(submitDto.getPeriodCollocter());
            taskInfo.setExpectedEndTime(submitDto.getExpectedEndTime());
            taskInfo.setPeriodReportStatus(1);

            basRecordingTaskInfoMapper.updateById(taskInfo);

            final BasRecordingLableEntity basRecordingLableEntity = basRecordingLableEntityMapper.selectOne(new LambdaQueryWrapper<BasRecordingLableEntity>()
                    .eq(BasRecordingLableEntity::getEntityCode, taskInfo.getEntityCode())
                    .eq(BasRecordingLableEntity::getLableCode, taskInfo.getLableCode()));
            if(basRecordingLableEntity==null){
                BasRecordingLableEntity entity=new BasRecordingLableEntity();
                entity.setEntityCode(taskInfo.getEntityCode());
                entity.setLableCode(taskInfo.getLableCode());
                entity.setReportTime(taskInfo.getPeriodReportTime()==null?null:DateUtil.format(taskInfo.getPeriodReportTime(),DatePattern.NORM_DATE_PATTERN) );
                basRecordingLableEntityMapper.insert(entity);
            }
        }
        return R.ok();
    }
}
