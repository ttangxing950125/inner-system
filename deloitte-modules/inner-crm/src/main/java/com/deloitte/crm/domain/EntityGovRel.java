package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 【请填写功能名称】对象 entity_gov_rel
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Data
@Accessors(chain = true)
public class EntityGovRel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 城投主体code
     */
    @Excel(name = "城投主体code")
    private String entityCode;

    /**
     * 德勤政府code
     */
    @Excel(name = "德勤政府code")
    private String dqGovCode;

    /**
     * 德勤政府code
     */
    @Excel(name = "持股方式")
    private String shareMethod;

    /**
     * 德勤政府code
     */
    @Excel(name = "支持力度")
    private String support;

    /**
     * 德勤政府code
     */
    @Excel(name = "支持力度判断依据")
    private String judgment;

    /**
     * 德勤政府code
     */
    @Excel(name = "持股比例")
    private String shareRatio;

    /**
     * 德勤政府code
     */
    @Excel(name = "持股比例年份")
    private String shareRatioYear;

    /**
     * 德勤政府code
     */
    @Excel(name = "状态 0-禁用 1-启用")
    private String status;

    /**
     * 德勤政府code
     */
    @Excel(name = "备注")
    private String remarks;

    /**
     * 德勤政府code
     */
    @Excel(name = "created")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date created;

    /**
     * 德勤政府code
     */
    @Excel(name = "updated")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updated;

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}







