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
 * @Date: 2022/11/07/16:20
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataListGetDropDownBoxVo implements Serializable {
    //   版本
    private List<String> prsProjectVersionList;
    //敞口:
    private List<String> prsModelMasterLists;
    //月份
    private List<Map<String,Object>> sysDitMonthLists;
}
