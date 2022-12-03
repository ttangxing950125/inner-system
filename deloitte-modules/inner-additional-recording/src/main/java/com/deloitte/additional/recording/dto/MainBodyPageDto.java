package com.deloitte.additional.recording.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainBodyPageDto implements Serializable {
    /**
     * 分页参数开始页 1
     */
    private Integer pageNum=1;
    /***
     *分页参数 默认是10
     */
    private Integer pagesize=5;

    /**
     * 是否上市  0-未上市 1-已上市
     */
    private Byte list;

    /**
     * 是否发债 0-未发债 1-已发债
     */
    private Byte entityBondTag;

    /**
     * 主体状态 0-失效 1-生效
     */
    private Byte status;

    /**
     * 版本
     */
    private String versionName;

    /**
     * 敞口
     */
    private String ExposureTo;

    /**
     * 年份
     */
    private String timeValue;

    /**
     *补录人员
     */
    private String collocterName;

    /**
     * 审核人员
     */
    private String approverName;

}
