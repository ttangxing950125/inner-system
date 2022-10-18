package com.deloitte.crm.vo;

import lombok.Data;

/**
 * @author PenTang
 * @date 2022/10/17 19:10
 */
@Data
public class ProductsMasterRelVo {

  private  String proCustId;

    /**
     * 年份
     */
    private String dataYear;

    /**
     * 修改后的值
     */
    private Integer proMasDictIdNew;


    private String  entityCode;


}
