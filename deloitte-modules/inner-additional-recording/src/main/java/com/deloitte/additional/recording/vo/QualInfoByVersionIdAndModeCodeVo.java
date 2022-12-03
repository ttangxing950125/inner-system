package com.deloitte.additional.recording.vo;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/14/16:04
 * @Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class QualInfoByVersionIdAndModeCodeVo implements Serializable {
    /**
     * 标题
     */
    private String lable;
    /**
     * 指标Code
     */
    private String qualCode;
    /**
     * 指标名称
     */
    private String qualName;

    /**
     * id
     */
    private Integer qualId;

}
