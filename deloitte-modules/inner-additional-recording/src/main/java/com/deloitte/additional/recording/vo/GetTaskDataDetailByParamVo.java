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
 * @Date: 2022/11/28/15:43
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTaskDataDetailByParamVo implements Serializable {
    //科目名称
    private String confName;

    //科目数量
    private Integer recordingValue;
    //单位
    private String unit;
    //自动化数值
    private String ocrValue;
    //自动化单位
    private String automationUnit;
    //自动化来源链接
    private String automationSource;
    //自动化来源链接
    private String errDescription;
}
