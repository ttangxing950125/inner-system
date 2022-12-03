package com.deloitte.additional.recording.vo.kpi;

import lombok.Data;

import java.util.List;

/**
 * @author PenTang
 * @date 2022/11/29 19:21
 */
@Data
public class KpiWorkScoreVoByPage {

    private List<KpiWorkCalViewVo>  kpiWorkScoreVoList;

    private  Integer total;
}
