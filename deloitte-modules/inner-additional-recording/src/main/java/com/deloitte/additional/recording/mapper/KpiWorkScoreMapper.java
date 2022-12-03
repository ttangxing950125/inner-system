package com.deloitte.additional.recording.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.KpiWorkScore;
import com.deloitte.additional.recording.dto.KpiWorkScoreDto;
import com.deloitte.additional.recording.vo.kpi.KpiWorkScoreVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author PenTang
 * @date 2022/11/28 11:07
 */
public interface KpiWorkScoreMapper extends BaseMapper<KpiWorkScore> {

      List<KpiWorkScoreVo> getKpiWorkScore( @Param("kpiWorkScoreDto") KpiWorkScoreDto kpiWorkScoreDto);
}
