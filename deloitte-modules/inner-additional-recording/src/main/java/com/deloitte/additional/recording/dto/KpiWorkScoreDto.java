package com.deloitte.additional.recording.dto;

import lombok.Data;

/**
 * @author PenTang
 * @date 2022/11/28 15:42
 */
@Data
public class KpiWorkScoreDto {

    private  Integer userId;

    private  String workDay;

    /**
     * 统计(D-按天 H-按小时)
     */
    private  String statistical;
}
