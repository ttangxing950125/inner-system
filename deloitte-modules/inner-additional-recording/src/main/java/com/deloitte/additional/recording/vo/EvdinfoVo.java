package com.deloitte.additional.recording.vo;

import lombok.Data;

import java.io.Serializable;

/**
 *
 */
@Data
public class EvdinfoVo implements Serializable {

    /**
     * evidentce的编码
     */
    private  String code;

    /**
     * evidentce的名称
     */
    private String name;

    /**
     * 数据频率 1-年度 2-季度 3-月度
     */
    private String dataFrequency;

    /**
     *evd采集规则描述
     */
    private String collectDescribe;

    /**
     * 单位
     */
    private String unit;

    /**
     * 1	日期
     * 2	文本
     * 3	数字
     */
    private String valType;

    /**
     * 显示类别
     */
    private String dispType;

    /**
     * 异常值备注
     */
    private String errDescription;

    /**
     *
     */
}
