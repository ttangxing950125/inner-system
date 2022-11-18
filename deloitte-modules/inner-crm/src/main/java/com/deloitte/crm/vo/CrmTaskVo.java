package com.deloitte.crm.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author PenTang
 * @date 2022/10/08 10:01
 */
@Data
@Accessors(chain = true)
public class CrmTaskVo implements Serializable {
    /***
     *页码
    */
    private Integer pageNum;
    /***
     *每页条数
     */
    private Integer  pageSize;
    /***
     *指定日期
     */
    private  String taskDate;
}
