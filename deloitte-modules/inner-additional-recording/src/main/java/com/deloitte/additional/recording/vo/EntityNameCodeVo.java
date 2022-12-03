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
public class EntityNameCodeVo implements Serializable {

    private Integer entityId;

    /**
     * 主体名称
     */
    private String entityName;

    /**
     * 主体编码
     */
    private String entityCode;

}
