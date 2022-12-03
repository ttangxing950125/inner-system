package com.deloitte.additional.recording.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvidenceVo {

    /**
     * evidence的code
     */
    private String evdCode;

    /**
     * 指标
     */
    private String label;
}
