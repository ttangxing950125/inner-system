package com.deloitte.additional.recording.controller;

import com.deloitte.additional.recording.domain.KpiWorkFill;
import com.deloitte.additional.recording.dto.KpiWorkCalDto;
import com.deloitte.additional.recording.dto.KpiWorkFillDto;
import com.deloitte.additional.recording.service.KpiWorkFillService;
import com.deloitte.common.core.domain.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author PenTang
 * @date 2022/11/24 15:10
 */
@RestController
@RequestMapping("/KpiWork")
@Api("实习生工时-成果")
public class KpiWorkFillController {
    @Resource
    private KpiWorkFillService kpiWorkFillService;

    /**
     *根据传入的日期查询当天的工作情况(并额外返回当月有多少天)(YYYY-MM)
     *
     * @param date
     * @return R
     * @author penTang
     * @date 2022/11/24 15:23
    */
    @PostMapping("/getViewByDate")
     public R getKpiWorkView(String date){
        return R.ok(kpiWorkFillService.getKpiWorkView(date));
     }

    /**
     *一次提交一填写记录
     *
     * @param kpiWorkFillDto
     * @return R
     * @author penTang
     * @date 2022/11/25 18:04
     */
    @PostMapping("/submitWorkFill")
     public R submitWorkFill(@RequestBody KpiWorkFillDto kpiWorkFillDto){
        return  R.ok(kpiWorkFillService.kpiWorkSubmit(kpiWorkFillDto));
     }

    /**
     *根据条件进行kpi统计
     *
     * @param kpiWorkCalDto
     * @return R
     * @author penTang
     * @date 2022/11/26 16:25
     */
    @PostMapping("/query")
     public R WorkResultByQuery(@RequestBody KpiWorkCalDto kpiWorkCalDto){
        return  R.ok(kpiWorkFillService.KpiWorkCal(kpiWorkCalDto));
    }
    /**
     *工时审核
     *
     * @param KpiWorkFill
     * @return R
     * @author penTang
     * @date 2022/11/26 12:45
     */
    @PostMapping("/aprWorkFill")
    public R  aproWorkFill(@RequestBody  KpiWorkFill KpiWorkFill){
        return  R.ok(kpiWorkFillService.aprKpiWorkView(KpiWorkFill));
    }

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
    @PostMapping("/getApr")
    public R getAprWorkFill(String date,Integer id,String userName){
        return  R.ok(kpiWorkFillService.getKpiWorkView(date,id,userName));
    }


}
