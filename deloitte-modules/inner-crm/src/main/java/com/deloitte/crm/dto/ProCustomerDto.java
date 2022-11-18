package com.deloitte.crm.dto;

import lombok.Data;

/**
 * @author PenTang
 * @date 2022/10/17 10:09
 * 用于敞口划分(产品下敞口)
 */
@Data
public class ProCustomerDto {

    /**
     *  产品客户id
     */
    private  String proCumId;
    /**
     * 产品id
     */
    private  String productsId ;

    /**
     * 客户名称
     */
    private String customer;

    /**
     * 产品名称
     */
    private String proName ;


    /**
     * 敞口名称
     */
    private String masterName;


    /**
     * 敞口id
     */
    private String dictId;

    /**
     * 映射情况
     */
    private String updateMark;


}
