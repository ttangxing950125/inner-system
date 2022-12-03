package com.deloitte.additional.recording.dto;

import lombok.Data;

/**
 * @author PenTang
 * @date 2022/11/24 19:52
 */
@Data
public class KpIInfoByQuryDto {

    /**
     * 日期
     */
    private  String date ;


    /**
     * evd名字
     */
    private  String EvdName;


    /**
     * 补录状态
     */
    private Integer status;

    /**
     * 质检结果
     */
    private Integer checkR;


    private  Integer  pageNum;

    private  Integer pageSize;


}
