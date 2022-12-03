package com.deloitte.additional.recording.mapper;

import com.deloitte.additional.recording.domain.BasRecordingLableEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 中间补录层 标签和主体对应关系表 Mapper 接口
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-21
 */
@Mapper
public interface BasRecordingLableEntityMapper extends BaseMapper<BasRecordingLableEntity> {

}
