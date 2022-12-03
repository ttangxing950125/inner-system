package com.deloitte.additional.recording.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 冉浩岑
 * @date 2022/11/17 10:25
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
public class CenterMasterBackVo {
    /** id */
    private Integer id;
    /** 描述 */
    private String description;
    /** 敞口code */
    private String model_code;
    /** 敞口name */
    private String name;
    /** 映射masterName */
    private Integer tarMaster;
    /** 映射masterID */
    private String tarMid;
    /** 映射ID */
    private Integer tranId;
    /** 状态 */
    private Integer status;
    /** 修改时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated;
    /** 创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;
}
