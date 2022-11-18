package com.deloitte.additional.recording.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/09/14:39
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataListFindPrsProjectVersionsByYearVo implements Serializable {

    private String name;
    private String qualCode;
    private String qualName;
    private String modelCode;
    private String timeValue;
    private Integer verMasId;
}
