package com.deloitte.additional.recording.vo.kpi;

import lombok.Data;

import java.util.List;

/**
 * @author PenTang
 * @date 2022/11/28 15:55
 */
@Data
public class KpiWorkScoreVoByH {

    private List<KpiWorkScoreVo>  kpiWorkScoreVoList;

    /**
     * 合计
     */
    private  KpiWorkScoreVo kpiWorkScoreVo;
}
