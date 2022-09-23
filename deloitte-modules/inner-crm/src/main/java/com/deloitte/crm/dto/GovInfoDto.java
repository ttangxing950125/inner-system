package com.deloitte.crm.dto;

import lombok.Data;

/**
 * 统计政府信息
 * @author PenTang
 * @date 2022/09/22 23:14
 */
@Data
public class GovInfoDto {

    /** 省 */
   private  Integer province ;
    /** 市 */
   private Integer city;
    /** 县*/
    private Integer county;
    /** 经开 */
    private Integer open;
    /** 政府总数*/
    private Integer govSum;

}
