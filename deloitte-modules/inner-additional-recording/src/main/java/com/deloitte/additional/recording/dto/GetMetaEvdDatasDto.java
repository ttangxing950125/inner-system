package com.deloitte.additional.recording.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/14/19:33
 * @Description: evdData数据请求参数
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMetaEvdDatasDto implements Serializable {
    /**
     * 分页参数开始页 1
     */
    private Integer pageNum = 1;
    /***
     *分页参数 默认是10
     */
    private Integer pagesize = 10;
    /**
     * 年份
     */
    private String year;
    /**
     * 搜索参数
     */
    private String search;
    /**
     * 敞口Code
     */
    private String modelCode;
    /**
     * 版本Id
     */
    private Integer id;

}
