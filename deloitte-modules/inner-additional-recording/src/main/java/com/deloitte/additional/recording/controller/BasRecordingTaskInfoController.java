package com.deloitte.additional.recording.controller;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.BasRecordingTaskInfo;
import com.deloitte.additional.recording.domain.BaseStructuredNotes;
import com.deloitte.additional.recording.dto.RecordingTaskInfoGetInfoReqDto;
import com.deloitte.additional.recording.dto.RecordingTaskSubmitDto;
import com.deloitte.additional.recording.service.BasRecordingTaskInfoService;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 中间补录层任务表 前端控制器
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-21
 */
@Slf4j
@RestController
@RequestMapping("/taskInfo")
public class BasRecordingTaskInfoController {

    @Resource
    private BasRecordingTaskInfoService basRecordingTaskInfoService;

    /**
     * 获取补录任务列表
     * @see com.deloitte.additional.recording.dto.RecordingTaskInfoGetInfoReqDto
     * @param requestParam
     * @param request
     * @param response
     * @return
     * @desc: 传递entityCode 可调用接口
     * @desc: 传递报告期  等可以通过共用接口获取
     * @desc:  {@link CommonController#comboBox()}
     */
    @RequestMapping("/getTaskInfoByParamPage")
    public R getTaskInfoByParamPage(@RequestBody @Validated RecordingTaskInfoGetInfoReqDto requestParam, HttpServletRequest request, HttpServletResponse response) {
        log.info("获取补录任务列表>>>>请求参数:{}", JSON.toJSONString(requestParam));
        Optional.ofNullable(requestParam).orElseThrow(() -> new ServiceException("参数不可以为空"));
        final Page<BasRecordingTaskInfo> taskInfoByParamPage = basRecordingTaskInfoService.getTaskInfoByParamPage(requestParam);
        // 1-补录中(待补录)
        final Long aLongCountOneSize = basRecordingTaskInfoService.getBaseMapper().selectCount(new LambdaQueryWrapper<BasRecordingTaskInfo>()
                .eq(BasRecordingTaskInfo::getPeriodReportStatus, 1).eq(BasRecordingTaskInfo::getStatus,Boolean.TRUE));
        // 3-审核中
        final Long aLongCountThere = basRecordingTaskInfoService.getBaseMapper().selectCount(new LambdaQueryWrapper<BasRecordingTaskInfo>()
                .eq(BasRecordingTaskInfo::getPeriodReportStatus, 3).eq(BasRecordingTaskInfo::getStatus,Boolean.TRUE));
        //全部
        final Long totalCount = basRecordingTaskInfoService.getBaseMapper().selectCount(new LambdaQueryWrapper<BasRecordingTaskInfo>()
                .eq(BasRecordingTaskInfo::getStatus,Boolean.TRUE));
        final HashMap<String, Object> resultMaps = Maps.newHashMap();
        resultMaps.put("pageInfos",basRecordingTaskInfoService.getTaskInfoByParamPage(requestParam));
        resultMaps.put("totalCount",totalCount);
        resultMaps.put("aLongCountOne",aLongCountOneSize);
        resultMaps.put("aLongCountThere",aLongCountThere);
        log.info("获取补录任务列表>>>>返回结果集:{}", JSON.toJSONString(resultMaps));
        return R.ok(resultMaps);
    }

    /**
     * 选择分配
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/chooseDistribution")
    public R chooseDistribution(HttpServletRequest request,HttpServletResponse response){
        return R.ok(basRecordingTaskInfoService.chooseDistribution());
    }

    /**
     * 提交分配任务
     * @param submitDto
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/taskSubmit",method = RequestMethod.POST)
    public R taskSubmit(@RequestBody  Map<String,List> requestMap, HttpServletRequest request, HttpServletResponse response){
        Optional.ofNullable(requestMap).orElseThrow(() -> new ServiceException("参数不可以为空"));
        Optional.ofNullable( requestMap.get("submitInfos")).orElseThrow(() -> new ServiceException("请求参数不可为空"));
        final JSONArray jsonArray = JSONUtil.parseArray(requestMap.get("submitInfos"));
        List<RecordingTaskSubmitDto> becordingTaskSubmitDto = jsonArray.toList(RecordingTaskSubmitDto.class);
        return basRecordingTaskInfoService.taskSubmit(becordingTaskSubmitDto);
    }
}
