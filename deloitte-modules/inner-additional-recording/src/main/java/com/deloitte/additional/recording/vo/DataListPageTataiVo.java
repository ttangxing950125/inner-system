package com.deloitte.additional.recording.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/08/15:17
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataListPageTataiVo implements Serializable {
    //ID
    private Integer id;
    //指标code
    private String qualCode;
    //指标名称
    private String qualName;
    //指标描述
    private String description;
}
