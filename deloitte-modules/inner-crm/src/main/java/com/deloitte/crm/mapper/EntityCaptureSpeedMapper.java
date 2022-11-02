package com.deloitte.crm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.crm.domain.EntityCaptureSpeed;
import com.deloitte.crm.dto.EntityCaptureSpeedDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 冉浩岑
 * @date 2022/10/27 14:18
 */
@Mapper
public interface EntityCaptureSpeedMapper extends BaseMapper<EntityCaptureSpeed> {

    IPage<EntityCaptureSpeedDto> search(@Param("page") Page page, @Param("entityNameOrCode") String entityNameOrCode);
}
