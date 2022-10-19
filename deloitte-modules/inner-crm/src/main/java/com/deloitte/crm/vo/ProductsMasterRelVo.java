package com.deloitte.crm.vo;

import lombok.Data;

/**
 * @author PenTang
 * @date 2022/10/17 19:10
 */
@Data
public class ProductsMasterRelVo {

  /**
   * 主体名称
   */
  private String entityName;

  /**
   * 客户产品id
   */
  private  Integer proCustId;

    /**
     * 年份
     */
    private String dataYear;



  /**
   * 变更客户名称
   */
  private String customer;


  /**
   * 变更前敞口名称
   */
  private String masterNameOld;


  /**
   * 变更前敞口id
   */
  private Integer dictIdOld;


  /**
   * 变更后敞口名称
   */
  private String masterNameNew;


  /**
   * 变更后敞口id
   */
  private Integer dictIdNew;


    private String  entityCode;


}
