package com.deloitte.crm.domain;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.deloitte.common.core.annotation.Excel;
import lombok.Data;

/**
 * 【请填写功能名称】对象 bond_info
 * 
 * @author deloitte
 * @date 2022-09-23
 */
@Data
public class BondInfo
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 债券状态 */
    @Excel(name = "债券状态")
    private Integer bondStatus;

    /** 德勤内部债券代码 bond_0000id 6位数字 */
    @Excel(name = "德勤内部债券代码 bond_0000id 6位数字")
    private String bondCode;

    /** 债券简称 */
    @Excel(name = "债券简称")
    private String bondShortName;

    /**
     * 公私募类型 0_公募 1_私募
     */
    @Excel(name="公私募类型 0_公募 1_私募")
    private Integer raiseType;

    /**
     * 债卷状态 0_存续 1_违约 2_已兑付
     */
    @Excel(name="债卷状态 0_存续 1_违约 2_已兑付")
    private Integer bondState;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date created;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date updated;

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
