package com.deloitte.additional.recording.service.impl;

import cn.hutool.core.date.DateTime;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.domain.*;
import com.deloitte.additional.recording.dto.*;
import com.deloitte.additional.recording.enums.TaskStatus;
import com.deloitte.additional.recording.mapper.*;
import com.deloitte.additional.recording.service.BasEvdTaskInfoService;
import com.deloitte.additional.recording.service.BasEvdTaskLogService;
import com.deloitte.additional.recording.service.KpiWorkScoreService;
import com.deloitte.additional.recording.vo.CollectionDetailsVO;
import com.deloitte.additional.recording.vo.EvidenceDistributionVo;
import com.deloitte.additional.recording.vo.ExcelBatchCallBackVo;
import com.deloitte.additional.recording.vo.ModelMasterCountVo;
import com.deloitte.additional.recording.vo.kpi.BasEvdTaskINfoVo;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.core.utils.DateUtil;
import com.google.common.base.Objects;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @创建人 tangx
 * @创建时间 2022/11/29
 * @描述 BasEvdTaskInfo实体 业务处理层
 */
@Service
public class BasEvdTaskInfoServiceImpl extends ServiceImpl<BasEvdTaskInfoMapper, BasEvdTaskInfo> implements BasEvdTaskInfoService {

    @Resource
    private BasEvdTaskInfoMapper basEvdTaskInfoMapper;


    @Resource
    private BasEvdTaskLogService basEvdTaskLogService;
    @Resource
    private BasEvdDataMapper basEvdDataMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysGroupMapper sysGroupMapper;
    @Resource
    private EntityInfoMapper entityInfoMapper;
    @Resource
    private KpiWorkScoreService kpiWorkScoreService;

    /**
     * evidence批量分配
     *
     * @param evidenceBatchDto
     * @return
     */
    @Override
    public Integer taskCount(String qualCode, String dataYear) {
        return this.baseMapper.taskCount(qualCode, dataYear);
    }

    @Transactional
    public R updatEevidenceBatch(EvidenceBatchDto evidenceBatchDto) {

        //查询任务信息
        List<BasEvdTaskInfo> list = getBasEvdTaskInfoList(evidenceBatchDto);

        //过滤出可以修改为补录中的状态
        List<Integer> collect1 = list.stream().filter(d -> d.getStatus() != 3 && d.getStatus() != 4).map(BasEvdTaskInfo::getId).collect(Collectors.toList());
        LambdaUpdateWrapper<BasEvdTaskInfo> updateWrapper = new LambdaUpdateWrapper<>();
        //更新任务为补录中
        updateWrapper.set(BasEvdTaskInfo::getCollocter, evidenceBatchDto.getCollocterId())
                .set(BasEvdTaskInfo::getColTime, new Date())
                .set(BasEvdTaskInfo::getStatus, 1);
        updateWrapper.in(BasEvdTaskInfo::getId, collect1);
        boolean update = this.update(updateWrapper);
        if (update) {
            List<BasEvdTaskLog> basEvdTaskLogList = new ArrayList<>();
            list.stream().filter(d -> d.getStatus() != 3 && d.getStatus() != 4).forEach(d -> {
                BasEvdTaskLog basEvdTaskLog = new BasEvdTaskLog();
                basEvdTaskLog.setTaskId(d.getId());
                basEvdTaskLog.setActUser(d.getCollocter());
                basEvdTaskLog.setActType(2);
                basEvdTaskLog.setRemark("批量分配");
                basEvdTaskLogList.add(basEvdTaskLog);

            });
            basEvdTaskLogService.saveBatch(basEvdTaskLogList);
        }


        //过滤审核中和审核通过
        List<BasEvdTaskInfo> collect = list.stream().filter(b -> b.getStatus() == 3 || b.getStatus() == 4).collect(Collectors.toList());
        //把审核中和审核通过的数据返回给前端
        List<String> strings = getStrings(collect, 1);


        if (CollectionUtils.isNotEmpty(strings)) {
            return R.fail(strings);
        }

        return R.ok();
    }

    /**
     * evidence批量通过
     *
     * @param evidenceBatchDto
     * @return
     */
    @Override
    public R updatEevidenceThrough(EvidenceBatchDto evidenceBatchDto) {
        if (ObjectUtils.isEmpty(evidenceBatchDto.getCollocterId())) {
            throw new ServiceException("请上传补录人员");
        }

        if (ObjectUtils.isEmpty(evidenceBatchDto.getTaskInfoIds())) {
            throw new ServiceException("请上传任务");
        }
        //查询任务信息
        List<BasEvdTaskInfo> list = getBasEvdTaskInfoList(evidenceBatchDto);
        //过滤出非审核中的状态
        List<BasEvdTaskInfo> collect = list.stream().filter(b -> b.getStatus() != 3).collect(Collectors.toList());
        List<String> strings = getStrings(collect, 2);

        //过滤出可以修改为审核中的状态
        List<Integer> collect1 = list.stream().filter(d -> d.getStatus() == 3).map(BasEvdTaskInfo::getId).collect(Collectors.toList());
        LambdaUpdateWrapper<BasEvdTaskInfo> updateWrapper = new LambdaUpdateWrapper<>();
        //更新任务为审核通过
        updateWrapper.set(BasEvdTaskInfo::getCollocter, evidenceBatchDto.getCollocterId())
                .set(BasEvdTaskInfo::getStatus, 4);
        updateWrapper.in(BasEvdTaskInfo::getId, collect1);
        boolean update = this.update(updateWrapper);
        List<KpiWorkScore> kpiWorkScoreList = new ArrayList<>();

        if (update) {
            List<BasEvdTaskLog> basEvdTaskLogList = new ArrayList<>();
            EvidenceBatchDto batchDto = new EvidenceBatchDto();
            batchDto.setTaskInfoIds(collect1);
            //查询出状态为4审核通过的数据
            List<BasEvdTaskInfo> basEvdTaskInfoList = getBasEvdTaskInfoList(evidenceBatchDto);
            basEvdTaskInfoList.forEach(d -> {//添加日志
                BasEvdTaskLog basEvdTaskLog = new BasEvdTaskLog();
                basEvdTaskLog.setTaskId(d.getId());
                basEvdTaskLog.setActUser(d.getCollocter());
                basEvdTaskLog.setActType(2);
                basEvdTaskLog.setRemark("批量通过");
                basEvdTaskLogList.add(basEvdTaskLog);

                //截取sub_time的年月日
                String format = cn.hutool.core.date.DateUtil.format(d.getSubTime(), "yyyy-MM-dd");
                //截取sbt_time的几点钟
                int hour = cn.hutool.core.date.DateUtil.hour(d.getSubTime(), true);
                List<KpiWorkScore> kpipiWorkScoreList = getKpiWorkScoreList(d.getSubTime(), d.getCollocter());
                if (CollectionUtils.isNotEmpty(kpipiWorkScoreList)) { //如果存在repulseTotal+1

                    kpipiWorkScoreList.forEach(k -> {
                        KpiWorkScore kpiWorkScore = new KpiWorkScore();
                        kpiWorkScore.setId(k.getId());
                        kpiWorkScore.setRecordTotal(k.getRecordTotal() + 1);
                        kpiWorkScoreList.add(kpiWorkScore);
                    });
                } else { //不存在新增
                    KpiWorkScore kpiWorkScore = new KpiWorkScore();
                    kpiWorkScore.setUserId(d.getCollocter());
                    kpiWorkScore.setWorkDay(cn.hutool.core.date.DateUtil.parse(format));
                    kpiWorkScore.setHours(hour);
                    kpiWorkScore.setRecordTotal(1);
                    kpiWorkScoreList.add(kpiWorkScore);
                }

            });
            Boolean flag = CollectionUtils.isNotEmpty(kpiWorkScoreList) ? kpiWorkScoreService.saveOrUpdateBatch(kpiWorkScoreList) : null;

            basEvdTaskLogService.saveBatch(basEvdTaskLogList);
        }

        if (CollectionUtils.isNotEmpty(strings)) {
            return R.fail(strings);
        }

        return R.ok();
    }


    @Override
    public void excelBatchCallBack(MultipartFile file, HttpServletResponse response) throws IOException {
        List<ExcelBatchCallBackVo> list = EasyExcel.read(file.getInputStream())
                .head(ExcelBatchCallBackVo.class)
                .sheet()
                .doReadSync();
        if (CollectionUtils.isEmpty(list)) {
            throw new ServiceException("excel解析失败");
        }
        //过滤补录人员
        List<String> collectionNameList = list.stream().map(ExcelBatchCallBackVo::getCollectionName).collect(Collectors.toList());
        //过滤主体名称
        List<String> entityNameList = list.stream().map(ExcelBatchCallBackVo::getEntityName).collect(Collectors.toList());
        //过滤数据年份
        List<String> timeValueList = list.stream().map(ExcelBatchCallBackVo::getTimeValue).collect(Collectors.toList());
        //过滤evidence
        List<String> evidenceNameList = list.stream().map(ExcelBatchCallBackVo::getEvidenceName).collect(Collectors.toList());
        //根据excel的信息查询数据
        List<ExcelBatchCallBackVo> excelBatchCallBackVos = basEvdTaskInfoMapper.getBatchCallBack(collectionNameList, entityNameList, timeValueList, evidenceNameList);

        if (CollectionUtils.isNotEmpty(excelBatchCallBackVos)) {
            //过滤有sub_time的数据
            List<Integer> collect = excelBatchCallBackVos.stream().
                    filter(e -> ObjectUtils.isNotEmpty(e.getSubTime()) && e.getStatus() == 3)
                    .map(ExcelBatchCallBackVo::getId)
                    .collect(Collectors.toList());
            LambdaUpdateWrapper<BasEvdTaskInfo> taskInfoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            taskInfoLambdaUpdateWrapper.set(BasEvdTaskInfo::getStatus, 2).in(BasEvdTaskInfo::getId, collect);
            boolean update = this.update(taskInfoLambdaUpdateWrapper);
            if (update) {  //修改审核打回以后
                List<BasEvdTaskLog> basEvdTaskLogList = new ArrayList<>();
                List<KpiWorkScore> kpiWorkScoreList = new ArrayList<>();
                //查询修改审核打回的状态
                LambdaQueryWrapper<BasEvdTaskInfo> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(BasEvdTaskInfo::getStatus, 2).eq(BasEvdTaskInfo::getId, collect);
                List<BasEvdTaskInfo> taskInfos = this.list(wrapper);
                taskInfos.forEach(e -> {
                    //记录到日志表
                    BasEvdTaskLog basEvdTaskLog = new BasEvdTaskLog();
                    basEvdTaskLog.setTaskId(e.getId());
                    //collocter补录人员和上面查询的userId一样
                    basEvdTaskLog.setActUser(e.getCollocter());
                    basEvdTaskLog.setActType(2);
                    basEvdTaskLog.setRemark("批量打回");
                    basEvdTaskLogList.add(basEvdTaskLog);//

                    //截取sub_time的年月日
                    String format = cn.hutool.core.date.DateUtil.format(e.getSubTime(), "yyyy-MM-dd");
                    //截取sbt_time的几点钟
                    int hour = cn.hutool.core.date.DateUtil.hour(e.getSubTime(), true);
                    List<KpiWorkScore> kpipiWorkScoreList = getKpiWorkScoreList(e.getSubTime(), e.getCollocter());
                    if (CollectionUtils.isNotEmpty(kpipiWorkScoreList)) { //如果存在repulseTotal+1

                        kpipiWorkScoreList.forEach(k -> {
                            KpiWorkScore kpiWorkScore = new KpiWorkScore();
                            kpiWorkScore.setId(k.getId());
                            kpiWorkScore.setRepulseTotal(k.getRepulseTotal());
                            kpiWorkScoreList.add(kpiWorkScore);
                        });
                    } else { //不存在新增
                        KpiWorkScore kpiWorkScore = new KpiWorkScore();
                        kpiWorkScore.setUserId(e.getCollocter());
                        kpiWorkScore.setWorkDay(cn.hutool.core.date.DateUtil.parse(format));
                        kpiWorkScore.setHours(hour);
                        kpiWorkScore.setRepulseTotal(1);
                        kpiWorkScoreList.add(kpiWorkScore);
                    }


                });
                Boolean flag = CollectionUtils.isNotEmpty(kpiWorkScoreList) ? kpiWorkScoreService.saveOrUpdateBatch(kpiWorkScoreList) : null;
                basEvdTaskLogService.saveBatch(basEvdTaskLogList);

            }
        }

        List<ExcelBatchCallBackVo> callBackVos = new ArrayList<>();

        //筛选没有sub_time的信息并且标注该任务没有提交时间
        List<ExcelBatchCallBackVo> collect1 = excelBatchCallBackVos.stream()
                .filter(e -> ObjectUtils.isEmpty(e.getSubTime()) && e.getStatus() == 3)
                .map(v -> {
                    ExcelBatchCallBackVo callBackVo = new ExcelBatchCallBackVo();
                    callBackVo.setEntityName(v.getEntityName());
                    callBackVo.setTimeValue(v.getTimeValue());
                    callBackVo.setEvidenceName(v.getEvidenceName());
                    callBackVo.setCollectionName(v.getCollectionName());
                    callBackVo.setDesc("该任务没有提交时间");
                    return callBackVo;
                }).collect(Collectors.toList());
        //筛选出数据库里面没有数据的标注状态非审核中
        List<ExcelBatchCallBackVo> collect2 = list.stream().filter(t -> !excelBatchCallBackVos.contains(t)).map(v -> {
            ExcelBatchCallBackVo callBackVo = new ExcelBatchCallBackVo();
            BeanUtils.copyProperties(v, callBackVo);
            callBackVo.setDesc("任务不存在");
            return callBackVo;
        }).collect(Collectors.toList());

        //筛选出非审核中状态的标注状态非审核中
        List<ExcelBatchCallBackVo> collect3 = excelBatchCallBackVos.stream().filter(e -> e.getStatus() == 3).map(v -> {
            ExcelBatchCallBackVo callBackVo = new ExcelBatchCallBackVo();
            BeanUtils.copyProperties(v, callBackVo);
            callBackVo.setDesc("任务状态非审核中");//
            return callBackVo;
        }).collect(Collectors.toList());
        callBackVos.addAll(collect1);
        callBackVos.addAll(collect2);
        callBackVos.addAll(collect3);
        if (CollectionUtils.isNotEmpty(callBackVos)) {
            //更新数据为已打回
            EasyExcel.write(response.getOutputStream(), ExcelBatchCallBackVo.class).sheet("sheet1").doWrite(callBackVos);

        }


    }


    public List<KpiWorkScore> getKpiWorkScoreList(Date subTime, Integer userId) {
        //截取sub_time的年月日
        String format = cn.hutool.core.date.DateUtil.format(subTime, "yyyy-MM-dd");
        //截取sbt_time的几点钟
        int hour = cn.hutool.core.date.DateUtil.hour(subTime, true);
        //查询是否kpi_work_score表是否存在
        LambdaQueryWrapper<KpiWorkScore> kpiWorkScoreWrapper = new LambdaQueryWrapper<>();
        kpiWorkScoreWrapper.eq(KpiWorkScore::getWorkDay, format)
                .eq(KpiWorkScore::getHours, hour)
                .eq(KpiWorkScore::getUserId, userId);
        List<KpiWorkScore> kpipiWorkScoreList = kpiWorkScoreService.list(kpiWorkScoreWrapper);
        return kpipiWorkScoreList;
    }

    /**
     * 查询补录审核列表
     *
     * @param additionalEntryReview
     * @return
     */
    @Override
    public R getAdditionalEntryReviewList(AdditionalEntryReviewDto additionalEntryReview) {
        //查询当前是否是补录人员
        if (additionalEntryReview.getModelType() == 1) {
            List<String> collocterUserList = sysUserMapper.getUserIdRoleName(additionalEntryReview.getCurrentUserId(), "补录人员", null);
            if (CollectionUtils.isEmpty(collocterUserList)) {
                throw new ServiceException("当前用户不是补录人员");
            }
        }
        List<Long> userGroupIds = new ArrayList<>();
        //查询当前用户是否是审核人员
        Page<EvidenceDistributionVo> page = new Page<>(additionalEntryReview.getPageNum(), additionalEntryReview.getPagesize());
        if (additionalEntryReview.getModelType() == 2) {
            List<String> approverUserList = sysUserMapper.getUserIdRoleName(additionalEntryReview.getCurrentUserId(), "审核人员", "小组长");
            if (CollectionUtils.isEmpty(approverUserList)) {
                throw new ServiceException("当前用户不是审核人员");
            }
            //查询小组的id
            List<Long> groupIds = approverUserList.contains("小组长") ? sysGroupMapper.getGroupIds(additionalEntryReview.getCurrentUserId(), "小组长") : null;
            //查询小组下的用户id
            userGroupIds = CollectionUtils.isNotEmpty(groupIds) ? sysGroupMapper.getUserGroupIds(groupIds) : null;

        }
        List<EvidenceDistributionVo> AdditionalEntryReviewList = basEvdTaskInfoMapper.getAdditionalEntryReviewList(page, additionalEntryReview,
                additionalEntryReview.getCollocterIds(), additionalEntryReview.getApproverIds(), userGroupIds);
        page.setRecords(AdditionalEntryReviewList);
        return R.ok(page);
    }


    @Override
    public R getKpiInfoView(KpIInfoByQuryDto kpIInfoByQuryDto) {
        //取出相应条件
        String date = kpIInfoByQuryDto.getDate();
        DateTime dateTime = DateUtil.parseDate(date);
        String evdName = kpIInfoByQuryDto.getEvdName();
        Integer status = kpIInfoByQuryDto.getStatus();
        Integer checkR = kpIInfoByQuryDto.getCheckR();
        LambdaQueryWrapper<BasEvdTaskInfo> QueryWrapper = new LambdaQueryWrapper<>();
        Page<BasEvdTaskInfo> page = new Page<>(kpIInfoByQuryDto.getPageNum(), kpIInfoByQuryDto.getPageSize());
        if (!Objects.equal(date, null)) {
            QueryWrapper.eq(BasEvdTaskInfo::getCreated, dateTime);
        }

        if (!Objects.equal(evdName, null)) {
            BasEvdData basEvdData = basEvdDataMapper.selectOne(new LambdaQueryWrapper<BasEvdData>().eq(BasEvdData::getEvdName, evdName));
            if (basEvdData != null) {
                QueryWrapper.eq(BasEvdTaskInfo::getDataId, basEvdData.getId());
            }

        }
        if (!Objects.equal(status, null)) {
            QueryWrapper.eq(BasEvdTaskInfo::getStatus, status);
        }

        if (!Objects.equal(checkR, null)) {
            QueryWrapper.eq(BasEvdTaskInfo::getAprStatus, checkR);
        }


        Page<BasEvdTaskInfo> basEvdTaskInfoPage = basEvdTaskInfoMapper.selectPage(page, QueryWrapper);
        List<BasEvdTaskInfo> records = basEvdTaskInfoPage.getRecords();
        //返回数据
        ArrayList<BasEvdTaskINfoVo> basEvdTaskINfoVos = new ArrayList<>();
        for (BasEvdTaskInfo basEvdTaskInfo : records) {
            BasEvdTaskINfoVo basEvdTaskINfoVo = new BasEvdTaskINfoVo();
            BasEvdData basEvdData = basEvdDataMapper.selectOne(new LambdaQueryWrapper<BasEvdData>().eq(BasEvdData::getId, basEvdTaskInfo.getDataId()));
            EntityInfo entityInfo = entityInfoMapper.selectOne(new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getEntityCode, basEvdData.getEntityCode()));
            if (basEvdData.getDefect().equals(1)) {
                basEvdTaskINfoVo.setStatus(9999);
            } else {
                basEvdTaskINfoVo.setStatus(basEvdTaskInfo.getStatus());
            }
            basEvdTaskINfoVo.setEvdName(basEvdData.getEvdName());
            basEvdTaskINfoVo.setEvdCode(basEvdData.getEvdCode());
            basEvdTaskINfoVo.setEntityName(entityInfo.getEntityName());
            basEvdTaskINfoVo.setEntityCode(entityInfo.getEntityCode());
            basEvdTaskINfoVo.setCreated(basEvdTaskInfo.getCreated());
            basEvdTaskINfoVo.setColTime(basEvdTaskInfo.getColTime());
            basEvdTaskINfoVo.setAprTime(basEvdTaskInfo.getAprTime());
            basEvdTaskINfoVo.setAprStatus(basEvdTaskInfo.getAprStatus());
            basEvdTaskINfoVos.add(basEvdTaskINfoVo);
        }

        Page<BasEvdTaskINfoVo> page1 = new Page<>(kpIInfoByQuryDto.getPageNum(), kpIInfoByQuryDto.getPageSize());
        page1.setRecords(basEvdTaskINfoVos).setTotal(basEvdTaskInfoPage.getTotal());
        return R.ok(page1);

    }


    public List<BasEvdTaskInfo> getBasEvdTaskInfoList(EvidenceBatchDto evidenceBatchDto) {
        LambdaQueryWrapper<BasEvdTaskInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(BasEvdTaskInfo::getId, evidenceBatchDto.getTaskInfoIds());
        List<BasEvdTaskInfo> list = this.list(wrapper);
        return list;
    }

    public List<String> getStrings(List<BasEvdTaskInfo> collect, Integer type) {
        List<String> strings = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(collect)) {
            //过滤出data_id
            List<Integer> dataIds = collect.stream().map(BasEvdTaskInfo::getDataId).collect(Collectors.toList());
            List<Map<String, Object>> entityEvdNameList = basEvdTaskInfoMapper.getEntityEvdName(dataIds);
            if (CollectionUtils.isNotEmpty(entityEvdNameList)) {
                for (Map<String, Object> map : entityEvdNameList) {
                    for (BasEvdTaskInfo basEvdTaskInfo : collect) {
                        if (basEvdTaskInfo.getDataId().intValue() == Integer.parseInt(map.get("id").toString())) {
                            String statusNmae = type == 1 ? "无法分配" : "无法通过";
                            String name = new StringBuilder().append(map.get("entity_name").toString()).append("，")
                                    .append(map.get("evdName").toString()).append(",状态为")
                                    .append(TaskStatus.getDesc(basEvdTaskInfo.getStatus())).append(statusNmae).toString();
                            strings.add(name);
                        }
                    }
                }
            }
        }

        return strings;
    }

    public R getFeeds() {
        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        return R.ok(sysUsers);

    }

    public R getTeams() {

        List<SysGroup> sysGroups = sysGroupMapper.selectList(null);
        return R.ok(sysGroups);

    }

    /**
     * 审核改派
     *
     * @param taskReassignDto
     * @return
     */
    @Override
    public R updateTaskReassign(TaskReassignDto taskReassignDto) {
        List<String> approverUserList = sysUserMapper.getUserIdRoleName(taskReassignDto.getCurrentUserId(), null, "小组长");
        if (CollectionUtils.isEmpty(approverUserList)) {
            throw new ServiceException("审核改派必须是小组长");
        }
        //讲任务分配给审核人员
        LambdaUpdateWrapper<BasEvdTaskInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(BasEvdTaskInfo::getApprover, taskReassignDto.getApproverId()).in(BasEvdTaskInfo::getId, taskReassignDto.getTaskInfoIds());
        boolean update = this.update(wrapper);
        List<BasEvdTaskLog> basEvdTaskLogList = new ArrayList<>();
        if (update) {
            taskReassignDto.getTaskInfoIds().forEach(t -> {
                BasEvdTaskLog basEvdTaskLog = new BasEvdTaskLog();
                basEvdTaskLog.setTaskId(t);
                basEvdTaskLog.setActUser(taskReassignDto.getApproverId());
                basEvdTaskLog.setActType(2);
                basEvdTaskLog.setRemark("常规政府批量通过");
                basEvdTaskLogList.add(basEvdTaskLog);
            });
        }
        basEvdTaskLogService.saveBatch(basEvdTaskLogList);
        return R.ok(update);
    }

    /**
     * 常规政府批量通过
     *
     * @param evidenceBatchDto
     * @return
     */
    @Override
    public R auditPassInBulk(EvidenceBatchDto evidenceBatchDto) {
        LambdaUpdateWrapper<BasEvdTaskInfo> updateWrapper = new LambdaUpdateWrapper<>();
        //更新任务为审核通过
        updateWrapper.set(BasEvdTaskInfo::getCollocter, evidenceBatchDto.getCollocterId())
                .set(BasEvdTaskInfo::getStatus, 4);
        updateWrapper.in(BasEvdTaskInfo::getId, evidenceBatchDto.getTaskInfoIds());
        boolean update = this.update(updateWrapper);
        List<KpiWorkScore> kpiWorkScoreList = new ArrayList<>();

        if (update) {
            List<BasEvdTaskLog> basEvdTaskLogList = new ArrayList<>();

            //查询出状态为4审核通过的数据
            List<BasEvdTaskInfo> basEvdTaskInfoList = getBasEvdTaskInfoList(evidenceBatchDto);
            basEvdTaskInfoList.forEach(d -> {//添加日志
                BasEvdTaskLog basEvdTaskLog = new BasEvdTaskLog();
                basEvdTaskLog.setTaskId(d.getId());
                basEvdTaskLog.setActUser(d.getCollocter());
                basEvdTaskLog.setActType(2);
                basEvdTaskLog.setRemark("常规政府批量通过");
                basEvdTaskLogList.add(basEvdTaskLog);

                //截取sub_time的年月日
                String format = cn.hutool.core.date.DateUtil.format(d.getSubTime(), "yyyy-MM-dd");
                //截取sbt_time的几点钟
                int hour = cn.hutool.core.date.DateUtil.hour(d.getSubTime(), true);
                List<KpiWorkScore> kpipiWorkScoreList = getKpiWorkScoreList(d.getSubTime(), d.getCollocter());
                if (CollectionUtils.isNotEmpty(kpipiWorkScoreList)) { //如果存在repulseTotal+1

                    kpipiWorkScoreList.forEach(k -> {
                        KpiWorkScore kpiWorkScore = new KpiWorkScore();
                        kpiWorkScore.setId(k.getId());
                        kpiWorkScore.setRecordTotal(k.getRecordTotal() + 1);
                        kpiWorkScoreList.add(kpiWorkScore);
                    });
                } else { //不存在新增
                    KpiWorkScore kpiWorkScore = new KpiWorkScore();
                    kpiWorkScore.setUserId(d.getCollocter());
                    kpiWorkScore.setWorkDay(cn.hutool.core.date.DateUtil.parse(format));
                    kpiWorkScore.setHours(hour);
                    kpiWorkScore.setRecordTotal(1);
                    kpiWorkScoreList.add(kpiWorkScore);
                }

            });

        }

        return R.ok(update);
    }

    @Override
    public List<BasEvdTaskInfoStatusCountDto> taskCountByStatus(String qualCode, String dataYear) {
        return this.baseMapper.taskCountByStatus(qualCode, dataYear);
    }

    @Override
    public R getCollectionDetails(String entityCode, String evdCode) {
        //查询主体的信息
        CollectionDetailsVO collectionDetails = basEvdTaskInfoMapper.getCollectionDetails(entityCode, evdCode);
        //获取敞口的分组数量
        List<ModelMasterCountVo> modelMasterCounts = basEvdTaskInfoMapper.getModelMasterCount(entityCode);
        //获取分组数量最大的敞口
        ModelMasterCountVo modelMasterCount = modelMasterCounts.stream().max(Comparator.comparing(ModelMasterCountVo::getCount)).get();
        collectionDetails.setModelName(ObjectUtils.isNotEmpty(modelMasterCount) ? modelMasterCount.getModelName() : null);

        return null;
    }

    @Override
    public R getDropDownBox(String evdCode) {
        List<Map<String, Object>> mapList = basEvdTaskInfoMapper.getDropDownBox(evdCode);
        return R.ok(mapList);
    }


}
