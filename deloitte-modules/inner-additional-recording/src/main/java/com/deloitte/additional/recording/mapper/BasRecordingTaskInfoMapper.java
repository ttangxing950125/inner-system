package com.deloitte.additional.recording.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.BasRecordingTaskInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.dto.RecordingTaskInfoGetInfoReqDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * <p>
 * 中间补录层任务表 Mapper 接口
 * </p>
 *
 * @author blank
 * @since 2022-11-21
 */
@Mapper
public interface BasRecordingTaskInfoMapper extends BaseMapper<BasRecordingTaskInfo> {

    IPage<BasRecordingTaskInfo> getTaskInfoByParamPage(@Param("page") Page<BasRecordingTaskInfo> page, @Param("requests") RecordingTaskInfoGetInfoReqDto requests);
}
