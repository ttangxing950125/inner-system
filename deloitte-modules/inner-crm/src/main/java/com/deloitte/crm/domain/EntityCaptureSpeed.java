package com.deloitte.crm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.crm.annotation.UpdateLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName entity_capture_speed
 * 状态捕获记录表
 */
@TableName("entity_capture_speed")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class EntityCaptureSpeed implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 来源
     */
    private String source;
    /**
     * 主体名
     */
    private String entityName;
    /**
     * 编码
     */
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
     * 是否初始化的数据
     */
    private Integer auto;
    /**
     * 捕获时间
     */
    private Date captureTime;
    /**
     * 新增时间
     */
    private Date addedTime;
    /**
     * 敞口划分时间
     */
    private Date divideTime;
    /**
     * 补充时间
     */
    private Date supplementTime;
    /**
     * 推送时间
     */
    private Date pushMetaTime;


    /**
     * 最后更新的用户，用户名
     */
    private String updater;
    /**
     * 创建时间
     */
    private Date created;
    /**
     * 更新时间
     */
    private Date updated;

    /**
     * 统一社会信用代码
     */
    @Excel(name = "统一社会信用代码")
    @UpdateLog(fieldName = "统一社会信用代码", tableFieldName = "credit_code")
    private String creditCode;
}
