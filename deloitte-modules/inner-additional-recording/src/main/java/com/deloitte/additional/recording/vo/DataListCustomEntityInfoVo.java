package com.deloitte.additional.recording.vo;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/09/18:30
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataListCustomEntityInfoVo implements Serializable {

    private Integer id;

    private String timeValue;

    private String entityName;

    private String entityCode;

    private String entityType;

    private String qualCode;
}
