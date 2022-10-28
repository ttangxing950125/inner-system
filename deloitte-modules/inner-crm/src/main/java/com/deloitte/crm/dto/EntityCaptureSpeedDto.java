package com.deloitte.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/10/27/16:13
 * @Description:
 */
@Setter
@Getter
@AllArgsConstructor
public class EntityCaptureSpeedDto implements Serializable {
    //id
    private Integer id;
    //来源
    private String source;
    //主体名称
    private String entityName;
    //社会统一信用代码
    private String entityCode;

}
