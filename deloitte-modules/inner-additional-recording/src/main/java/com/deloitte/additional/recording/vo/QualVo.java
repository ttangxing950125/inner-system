package com.deloitte.additional.recording.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QualVo implements Serializable {

    /**
     * 指标编码
     */
    private String qualCode;

    /**
     *指标名称
     */
    private String qualName;

    private List<EvdinfoVo> evdinfoVos;
}
