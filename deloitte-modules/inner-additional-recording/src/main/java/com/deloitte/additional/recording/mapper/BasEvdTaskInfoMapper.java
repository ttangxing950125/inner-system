package com.deloitte.additional.recording.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.BasEvdTaskInfo;
import com.deloitte.additional.recording.dto.BasEvdTaskInfoStatusCountDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @创建人 tangx
 * @创建时间 2022/11/29
 * @描述 BasEvdTaskInfo 表 数据处理层
 */
public interface BasEvdTaskInfoMapper  extends BaseMapper<BasEvdTaskInfo> {


    Integer taskCount(@Param("qualCode") String qualCode, @Param("dataYear") String dataYear);

    List<BasEvdTaskInfoStatusCountDto> taskCountByStatus(@Param("qualCode") String qualCode, @Param("dataYear") String dataYear);
}
