package com.deloitte.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.crm.domain.EntityCaptureSpeed;
import com.deloitte.crm.dto.EntityCaptureSpeedDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 冉浩岑
 * @date 2022/10/27 14:18
 */
public interface EntityCaptureSpeedMapper extends BaseMapper<EntityCaptureSpeed> {

    List<EntityCaptureSpeedDto> search(@Param("entityNameOrCode") String entityNameOrCode);
}
