package com.deloitte.crm.domain;

import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

/**
 * (CnDelistInfo)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-11 18:46:11
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CnDelistInfo implements Serializable {
    private static final long serialVersionUID = -73576670427883067L;
    /**
     * 主键
     */
    @Excel(name = "主键")
    private Integer id;
    /**
     * 导入时间
     */
    @Excel(name = "导入时间")
    private Date importTime;
    /**
     * 1-新增 2-修改
     */
    @Excel(name = "1-新增 2-修改")
    private Integer changeType;
    /**
     * crm_wind_task的id
     */
    @Excel(name = "crm_wind_task的id")
    private Integer taskId;
    /**
     * 序号
     */
    @Excel(name = "序号")
    private Integer number;
    /**
     * 代码
     */
    @Excel(name = "代码")
    private String code;
    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;
    /**
     * 退市日期
     */
    @Excel(name = "退市日期")
    private Date delistDate;
    /**
     * 退市时股价(元)
     */
    @Excel(name = "退市时股价(元)")
    private Double delistSharePrice;
    /**
     * 退市时每股净资产(元)
     */
    @Excel(name = "退市时每股净资产(元)")
    private Double delistNetWorth;
    /**
     * 终止上市原因
     */
    @Excel(name = "终止上市原因")
    private String stopDelistdWhy;
    /**
     * 重组后代码
     */
    @Excel(name = "重组后代码")
    private String reconstitutedCode;
    /**
     * 重组后简称
     */
    @Excel(name = "重组后简称")
    private String reconstitutedName;
    /**
     * 重组后上市日期
     */
    @Excel(name = "重组后上市日期")
    private String reconstitutedDelistDate;
    /**
     * 退入三板日期
     */
    @Excel(name = "退入三板日期")
    private Date threeBoardDate;
    /**
     * 三板代码
     */
    @Excel(name = "三板代码")
    private String threeBoardCode;
    /**
     * 三板简称
     */
    @Excel(name = "三板简称")
    private String threeBoardName;
    /**
     * 退市股证券类型
     */
    @Excel(name = "退市股证券类型")
    private String delistedType;
    /**
     * 重组后证券类型
     */
    @Excel(name = "重组后证券类型")
    private String reconstitutedType;
    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private Date created;
    /**
     * 更新时间
     */
    @Excel(name = "更新时间")
    private Date updated;


}
