package com.deloitte.additional.recording.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CollectionDetailsVO implements Serializable {
    /**
     * 主体id
     */
    private Integer id;

    /**
     * 主体编码
     */
    private String entityCode;

    /**
     * 主体名称
     */
    private String entityName;

    /**
     * 敞口名称
     */
    private String modelName;

    /**
     * 信用编码
     */
    private String creditCode;

    /**
     * 是否发债
     */
    private Byte entityBondTag;

    /**
     *当前是否上市 0-未上市 1-已上市
     */
    private Byte list;

    /**
     * 报告期
     */
    private String timeValue;

    /**
     * 审核意见
     */
    private String mark;

    /**
     *指标数据
     */
    private List<QualVo> qualVos;


}
