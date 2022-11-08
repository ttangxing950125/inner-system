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
 * @Date: 2022/11/07/17:55
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTaskTargetvalPageListDto implements Serializable {
    /**
     * 分页参数开始页 1
     */
    private Integer pageNum;
    /***
     *分页参数 默认是10
     */
    private Integer pagesize;
    /**
     * 版本
     */
    private String versionName;

    /**
     * 敞口ID
     */
    private Long industryId;
    /**
     * 年份
     */
    private String year;
    /***
     * 主体名称
     */
    private String entityName;
}
