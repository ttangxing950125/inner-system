package com.deloitte.additional.recording.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvidenceBatchDto implements Serializable {

    /**
     * 补录人员的id
     */
    private Integer collocterId;


    /**
     * 任务id
     */
    List<Integer> taskInfoIds;

}
