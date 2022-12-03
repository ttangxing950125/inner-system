package com.deloitte.additional.recording.mapper;

import com.deloitte.additional.recording.domain.BasRecordingLabel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 中间补录层标签表 Mapper 接口
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-21
 */
@Mapper
public interface BasRecordingLabelMapper extends BaseMapper<BasRecordingLabel> {

}
