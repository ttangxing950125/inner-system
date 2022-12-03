package com.deloitte.additional.recording.vo.kpi;

import com.deloitte.additional.recording.domain.KpiWorkFill;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PenTang
 * @date 2022/11/24 16:15
 */
@Data
public class KpiWorkViewVo {

    /**
     * 日期总数
     */
    private  Integer DateCount;

    /**
     * 工作列表
     */
    private List<KpiWorkFill> KpiWorkFills =new ArrayList<KpiWorkFill>();

}
