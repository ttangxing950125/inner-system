package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 【请填写功能名称】对象 bond_info
 *
 * @author deloitte
 * @date 2022-09-23
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BondInfo {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 债券全称
     */
    @Excel(name = "债券全称")
    private String bondName;

    /**
     * 债券状态 n1-等待发行\n 2-正在发行 3-\n已发行待上市\n \r\n4-成功上市 5-推迟发行\r\n6-\n发行失败 7-违约\r\n8-展期
     */
    @Excel(name = "债券状态")
    private Integer bondStatus;

    /**
     * 德勤内部债券代码 bond_0000id 6位数字
     */
    @Excel(name = "德勤内部债券代码 bond_0000id 6位数字")
    private String bondCode;

    /**
     * 债券交易代码
     */
    private String oriCode;

    /**
     * 债券简称
     */
    @Excel(name = "债券简称")
    private String bondShortName;


    /**
     * 起息日
     */
    @ApiModelProperty(value = "起息日")
    private String valueDate;

    /**
     * 到期日
     */
    @ApiModelProperty(value = "到期日")
    private String dueDate;


    /**
     * 公私募类型 0_公募 1_私募
     */
    @Excel(name = "公私募类型 0_公募 1_私募")
    private Integer raiseType;

    /**
     * 无效
     */
    private Integer bondType;

    /**
     * 债卷状态 0_存续 1_违约 2_已兑付
     */
    @Excel(name = "债卷状态 0_存续 1_违约 2_已兑付")
    private Integer bondState;

    /**
     * 是否集合债
     */
    private Boolean coll;

    /**
     * 是否abs债券
     */
    private Boolean abs;

    /**
     * 状态 1-删除 0-未删除 默认都是未删除
     */
    private Boolean isDeleted = Boolean.FALSE;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date created;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date updated;

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }

    /**
     * 上市日期
     */
    private Date listdate;
    /**
     * 上市地点
     */
    private String exchange;
    /**
     * 发行总额
     * [单位] 亿元
     */
    private String issueamount;
    /**
     * wind1一级
     */
    private String wind1;
    /**
     * wind2二级级
     */
    private String wind2;


}
