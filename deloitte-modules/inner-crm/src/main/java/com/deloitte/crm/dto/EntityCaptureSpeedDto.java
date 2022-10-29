package com.deloitte.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

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

    /**
     * 0.未捕获 1.已捕获
     * 是否已捕获 （导入后，角色6还未新增） 这时候wind_task应该有数据
     */
    private Integer capture;
    /**
     * 0-未处理 1-已有主体 2-新增主体 角色6是否新增,不为0就是已处理
     */
    private Integer added;
    /**
     * 0-未处理 1-已处理 角色2是否已处理
     */
    private Integer divide;
    /**
     * 0-未处理 1-已处理  角色3,4,5任务处理状态
     */
    private Integer supplement;
    /**
     * 0-未推送 1-已推送 是否推送补录平台
     */
    private Integer pushMeta;

    /**
     * 捕获时间
     */
    private Date captureTime;

    /**
     * 更新时间
     */
    private Date updated;
}
