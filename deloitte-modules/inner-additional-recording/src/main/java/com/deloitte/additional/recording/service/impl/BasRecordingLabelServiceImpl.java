package com.deloitte.additional.recording.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.*;
import com.deloitte.additional.recording.dto.BasRecordingLabelReqDto;
import com.deloitte.additional.recording.mapper.*;
import com.deloitte.additional.recording.service.BasRecordingLabelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.filter.impl.Op;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * <p>
 * 中间补录层标签表 服务实现类
 * </p>
 * @author chenjiang
 * @since 2022-11-21
 */
@Slf4j
@Service
public class BasRecordingLabelServiceImpl extends ServiceImpl<BasRecordingLabelMapper, BasRecordingLabel> implements BasRecordingLabelService {
    @Resource
    private BasRecordingLabelMapper basRecordingLabelMapper;
    @Resource
    private BasRecordingTaskInfoMapper basRecordingTaskInfoMapper;
    @Resource
    private EntityInfoMapper entityInfoMapper;
    @Resource
    private BasRecordingTableMapper basRecordingTableMapper;
    @Resource
    private BasRecordingLabelFiledMapper basRecordingLabelFiledMapper;
    @Resource
    private BasEvdDataMapper basEvdDataMapper;
    @Resource
    private BasEvdInfoMapper basEvdInfoMapper;



    /**
     * 标签补录
     * @param reqDto
     * @return
     */
    @Override
    public R getEcordingLabelList(BasRecordingLabelReqDto reqDto) {
        log.info("==> 标签补录分页查询开始 请求参数:>>{}", JSON.toJSONString(reqDto));
        Page<BasRecordingTaskInfo> page = new Page<>(reqDto.getPageNum(), reqDto.getPagesize());
        final LambdaQueryWrapper<BasRecordingTaskInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(reqDto.getEntityCode())) {
            queryWrapper.eq(BasRecordingTaskInfo::getEntityCode, reqDto.getEntityCode());
        }
        if (StringUtils.isNotEmpty(reqDto.getEntityName())) {
            queryWrapper.like(BasRecordingTaskInfo::getEntityName, reqDto.getEntityName());
        }
        if (StringUtils.isNotEmpty(reqDto.getLableCode())) {
            queryWrapper.eq(BasRecordingTaskInfo::getLableCode, reqDto.getLableCode());
        }
        if (StringUtils.isNotEmpty(reqDto.getTableCode())) {
            queryWrapper.eq(BasRecordingTaskInfo::getTableCode, reqDto.getTableCode());
        }
        if (reqDto.getStatus() != null) {
            queryWrapper.eq(BasRecordingTaskInfo::getPeriodReportStatus, reqDto.getStatus());
        }
        queryWrapper.eq(BasRecordingTaskInfo::getPeriodCollocter, reqDto.getPeriodCollocterId());
        queryWrapper.eq(BasRecordingTaskInfo::getStatus, 1);

        Page<BasRecordingTaskInfo> resultPage = basRecordingTaskInfoMapper.selectPage(page, queryWrapper);
        if (CollUtil.isEmpty(resultPage.getRecords())) {
            return R.ok(resultPage);
        }
        List<BasRecordingTaskInfo> records = resultPage.getRecords();
        List<BasRecordingTaskInfo> collect = records.stream().map(record -> {
            final EntityInfo entityInfo = entityInfoMapper.selectOne(new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getEntityCode, record.getEntityCode())
                    .eq(EntityInfo::getStatus, 1));
            record.setEntityName(Optional.ofNullable(entityInfo).map(EntityInfo::getEntityName).orElse(""));
            record.setTableName(Optional.ofNullable(basRecordingTableMapper.selectOne(new LambdaQueryWrapper<BasRecordingTable>()
                    .eq(BasRecordingTable::getCode, record.getTableCode()))).map(BasRecordingTable::getName).orElse(""));
            record.setLableName(Optional.ofNullable(basRecordingLabelMapper.selectOne(new LambdaQueryWrapper<BasRecordingLabel>()
                    .eq(BasRecordingLabel::getCode, record.getLableCode()))).map(BasRecordingLabel::getName).orElse(""));
            return record;
        }).collect(Collectors.toList());
        resultPage.setRecords(collect);
        log.info("==> 标签补录分页查询结束 返回结果集:>>{}", JSON.toJSONString(resultPage));
        return R.ok(resultPage);
    }

    /**
     * @param taskId 任务ID
     * @return
     */
    @Override
    public R editEcordingLabel(Integer taskId) {
        HashMap<String, Object> resultMap = Maps.newHashMap();
        //1、通过TaskId获取任务
        BasRecordingTaskInfo taskInfo = basRecordingTaskInfoMapper.selectOne(new LambdaQueryWrapper<BasRecordingTaskInfo>().eq(BasRecordingTaskInfo::getId, taskId));
        if (taskInfo == null || taskInfo.getPeriodReportStatus() == 3 || taskInfo.getPeriodReportStatus() == 4) {
            throw new ServiceException("该任务不存在或状态不支持编辑");
        }
        EntityInfo entityInfo = Optional.ofNullable(entityInfoMapper.selectOne(new LambdaQueryWrapper<EntityInfo>()
                .eq(EntityInfo::getEntityCode, taskInfo.getEntityCode()).eq(EntityInfo::getStatus, 1)))
                .orElseThrow(() -> new ServiceException("主体Code:" + taskInfo.getEntityCode() + "查询不到主体信息"));
        //主体名称
        taskInfo.setEntityName(entityInfo.getEntityName());
        final BasRecordingTable basRecordingTable = basRecordingTableMapper.selectOne(new LambdaQueryWrapper<BasRecordingTable>().eq(BasRecordingTable::getCode, taskInfo.getTableCode()));
        Optional.ofNullable(basRecordingTable).orElseThrow(() -> new ServiceException("表不存在"));
        //表名称
        taskInfo.setTableName(basRecordingTable.getName());
        //1.2
        final BasRecordingLabel basRecordingLabel = basRecordingLabelMapper.selectOne(new LambdaQueryWrapper<BasRecordingLabel>().eq(BasRecordingLabel::getCode, taskInfo.getLableCode()).eq(BasRecordingLabel::getTableCode, taskInfo.getLableCode()));
        if (basRecordingLabel == null) {
            throw new ServiceException("标签不纯在");
        }
        /**
         * 标签类型 1-标准化表格 2-文本描述型 3-非标准化表格 4-非标准化表格年份
         */
        if (basRecordingLabel.getType() == 2) {

        } else if (basRecordingLabel.getType() == 1) {
            //2、通过lableCode 和sourceType获取 sourceCode(Evd_code) sourceCode 目前只取evd
            List<BasRecordingLabelFiled> basRecordingLabelFiledsList = basRecordingLabelFiledMapper.selectList(new LambdaQueryWrapper<BasRecordingLabelFiled>()
                    .eq(BasRecordingLabelFiled::getLabelCode, taskInfo.getLableCode())
                    //1-evd 2-三表 3-附注 目前只考虑evd 目前只查询evd
                    .eq(BasRecordingLabelFiled::getSourceType, 1));
            if (CollUtil.isEmpty(basRecordingLabelFiledsList)) {
                return R.ok();
            }
            //3、获取到所有的 sourceCode
            List<String> collectSourceCode = basRecordingLabelFiledsList.stream().map(BasRecordingLabelFiled::getSourceCode).collect(Collectors.toList());
            //4、查询所有的EvdData 根据entityCode 和evdCode 和报告期
            List<BasEvdData> basEvdDataLists = collectSourceCode.stream().map(e -> CompletableFuture.supplyAsync(() -> {
                return basEvdDataMapper.selectOne(new LambdaQueryWrapper<BasEvdData>()
                        .eq(BasEvdData::getEvdCode, e)
                        .eq(BasEvdData::getTimeValue, taskInfo.getPeriodReportTime())
                        .eq(BasEvdData::getEntityCode, taskInfo.getEntityCode()));
            })).map(CompletableFuture::join).collect(Collectors.toList());


            List<BasEvdData> newBaseEvdData = basEvdDataLists.stream().map(bs -> {
                BasEvdInfo basEvdInfo = basEvdInfoMapper.selectOne(new LambdaQueryWrapper<BasEvdInfo>().eq(BasEvdInfo::getCode, bs.getEvdCode()));
                bs.setEvdName(basEvdInfo.getName());
                return bs;
            }).collect(Collectors.toList());
        }




        return null;
    }
}
