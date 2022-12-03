package com.deloitte.additional.recording.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.FinancialTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.dto.EntityInfoByFinDto;
import com.deloitte.additional.recording.vo.EntityInfoByFinVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenjiang
 * @since 2022-11-26
 */
public interface FinancialTaskMapper extends BaseMapper<FinancialTask> {


    List<EntityInfoByFinVo> getEntityFinalView(@Param("page") Page<EntityInfoByFinVo> page, @Param("entityInfoByFinDto") EntityInfoByFinDto entityInfoByFinDto);

}
