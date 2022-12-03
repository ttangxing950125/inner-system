package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.KpiWorkFill;
import com.deloitte.additional.recording.domain.KpiWorkScore;
import com.deloitte.additional.recording.dto.KpiWorkScoreDto;
import com.deloitte.additional.recording.vo.kpi.KpiWorkScoreVo;
import com.deloitte.common.core.domain.R;

import java.util.List;

/**
 * @author PenTang
 * @date 2022/11/28 11:18
 */
public interface KpiWorkScoreService extends IService<KpiWorkScore> {

    R getKpiWorkScore(KpiWorkScoreDto kpiWorkScoreDto);

}
