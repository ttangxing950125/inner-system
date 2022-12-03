package com.deloitte.additional.recording.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 工作台补录审核人员的返回工具类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TaskUserVo implements Serializable {

    /**
     * 用户显示名称
     */
    private String userName;

    /**
     * 用户显示名称
     */
    private Integer userId;

    /**
     * 角色名称
     */
    private String roleName;

}