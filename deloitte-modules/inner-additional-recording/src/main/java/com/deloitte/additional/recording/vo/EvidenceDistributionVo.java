package com.deloitte.additional.recording.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvidenceDistributionVo implements Serializable {

    private Integer taskinfoId;

    /**
     * evidence的code
     */
    private String code;

    /**
     * 主体名称
     */
    private String entityName;

    /**
     * 年份
     */
    private Integer timeValue;

    /**
     * evidence的名称
     */
    private String evidenceName;

    /**
     *标签
     */
    private String label;

    /**
     *是否上市
     */
    private Byte tinyint;

    /**
     * 是否发债
     */
    private Byte entityBondTag;

    /**
     *补录人员
     */
    private String collocterName;

    /**
     *审核人员
     */
    private String approverName;

    /**
     *
     * 任务状态 参考字典表collStat
     * -1	已关闭
     * 0	待分配
     * 1	补录中
     * 2	审核打回
     * 3	审核中
     * 4	审核通过
     * 5	无法录入
     */
    private String status;
}
