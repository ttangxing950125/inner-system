package com.deloitte.additional.recording.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.KpiWorkFill;
import com.deloitte.additional.recording.dto.KpiWorkCalDto;
import com.deloitte.additional.recording.dto.KpiWorkFillDto;
import com.deloitte.common.core.domain.R;
/**
 * @author PenTang
 * @date 2022/11/24 15:01
 */
public interface KpiWorkFillService extends IService<KpiWorkFill> {

    /**
     *根据传入的日期查询当天的工作情况(并额外返回当月有多少天)
     *
     * @param Date
     * @return R
     * @author penTang
     * @date 2022/11/24 16:38
    */

    R getKpiWorkView(String Date);

    /**
     *工时审核查询
     *
     * @param date
     * @param id
     * @param userName
     * @return R
     * @author penTang
     * @date 2022/11/25 18:18
    */
    R getKpiWorkView(String date,Integer id,String userName);

    /**
     *工时审核
     *
     * @param KpiWorkFill
     * @return R
     * @author penTang
     * @date 2022/11/26 12:45
    */
    R aprKpiWorkView(KpiWorkFill KpiWorkFill);



    /**
     *一次提交一填写记录
     *
     * @param kpiWorkFillDto
     * @return R
     * @author penTang
     * @date 2022/11/25 18:04
    */
    R kpiWorkSubmit(KpiWorkFillDto kpiWorkFillDto);

    /**
     *根据条件进行kpi统计
     *
     * @param kpiWorkCalDto
     * @return R
     * @author penTang
     * @date 2022/11/26 16:25
    */
    R KpiWorkCal(KpiWorkCalDto kpiWorkCalDto);
}
