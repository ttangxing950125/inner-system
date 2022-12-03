package com.deloitte.crm.dto;

import lombok.Data;

/**
 * @author PenTang
 * @date 2022/11/24 9:55
 */
@Data
public class OpenCrmDto {

    /**
     * 业务场景(crm产品)
     */
    private String businessScene;

    /**
     * 业务线(Crm产品下的客户)
     */
    private String businessLine;

    /**
     * 行业粉饰(Crm产品下的客户敞口)
     */
    private  String industryWhitewash;

}
