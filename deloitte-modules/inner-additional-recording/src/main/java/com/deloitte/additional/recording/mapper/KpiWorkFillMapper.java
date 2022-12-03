package com.deloitte.additional.recording.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.KpiWorkFill;
import com.deloitte.additional.recording.domain.SysGroupUser;
import com.deloitte.additional.recording.dto.KpiWorkCalDto;
import com.deloitte.additional.recording.vo.kpi.KpiWorkCalVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author PenTang
 * @date 2022/11/24 15:05
 */
public interface KpiWorkFillMapper extends BaseMapper<KpiWorkFill> {

    List<KpiWorkCalVo> getKpiWorkCal( @Param("sysGroups") List<SysGroupUser> sysGroups,@Param("dto") KpiWorkCalDto dto);
}
