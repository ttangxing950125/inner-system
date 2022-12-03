package com.deloitte.additional.recording.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class QualNameCodeVo implements Serializable {


    /**
     * 指标id
     */
    private Integer qualId;

    /**
     * 指标名称
     */
    private String qualName;

    /**
     * 指标code
     */
    private String qualCode;


}
