package com.deloitte.crm.vo;

import lombok.Data;

/**
 * @author PenTang
 * @date 2022/11/23 10:41
 */
@Data
public class EmailCrmTask {

    private  String  entityName;

    private  String  code ;

    private String sourceName ;

    private String windMaster;

    private String shenWanMaster;

    private String result;

    private String remarks;

}
