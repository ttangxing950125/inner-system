package com.deloitte.additional.recording.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvdNameCodeVo implements Serializable {

    /**
     * evidence的id
     */
    private Integer evidenceId;

    /**
     * evidence编码
     */
    private String evidenceCode;

    /**
     * evidence名称
     */
    private String evidenceName;
}
