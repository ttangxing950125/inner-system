package com.deloitte.additional.recording.vo.kpi;

import com.baomidou.mybatisplus.annotation.TableField;
import com.deloitte.common.core.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @author PenTang
 * @date 2022/11/26 10:25
 */
@Data
public class BasEvdTaskINfoVo {

    private Integer id;

    /**
     * 数据记录的id，bas_evd_data.id
     */
    private Integer dataId;

    /**
     * evidence_id
     */
    @Excel(name = "evidence_id")
    private String evdCode;

    @TableField(exist = false)
    private String evdName;


    private  String entityName;

    @Excel(name = "主体id")
    private String entityCode;

    /**
     * 数据类型(1-固定
     */
    private Integer dataType;

    /**
     * 分配的数据补录人员
     */
    private Integer collocter;

    /**
     * 采集时间，分配任务的时间
     */
    private Date colTime;

    /**
     * 分配的数据审核人员
     */
    private Integer approver;

    /**
     * 审核时间，审核任务的时间
     */
    private Date aprTime;

    /**
     * 任务状态 参考字典表collStat
     -1	已关闭
     0	待分配
     1	补录中
     2	审核打回
     3	审核中
     4	审核通过
     5	无法录入

     */
    private Integer status;


    /**
     * 审核人员审核时选择的操作 2-审核打回 4-审核通过
     */
    private Integer aprStatus;

    /**
     * 该任务预期结束时间
     */
    private Date expectedEndTime;

    private Date created;


    /**
     * 任务备注
     */
    private String mark;

    /**
     * 数据备注
     */
    private String dataMark;


}
