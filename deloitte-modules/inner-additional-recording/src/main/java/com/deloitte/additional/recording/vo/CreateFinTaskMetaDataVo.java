package com.deloitte.additional.recording.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/26/16:00
 * @Description:
 */
@Data
public class CreateFinTaskMetaDataVo implements Serializable {

    private Integer id;
    //主体编码
    private String entityCode;

    //数据类型
    private String dataTypeCode;

    // `table_type` varchar(1) DEFAULT NULL COMMENT '表类型 1 主表，2 附注',
    private String tableType;

    //报告期 年月日yyyy-MM-dd
    private Date reportDate;
    //报告期取年
    private String year;
}
