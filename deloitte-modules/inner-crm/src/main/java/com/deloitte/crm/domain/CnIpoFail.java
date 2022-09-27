package com.deloitte.crm.domain;

import java.util.Date;
import java.io.Serializable;

import cn.hutool.core.date.DateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

/**
 * IPO-发行失败(CnIpoFail)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:49
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CnIpoFail implements Serializable {
    private static final long serialVersionUID = -21803302062608092L;
    /**
     * 主键
     */
    @Excel(name = "主键")
    private Integer id;
    /**
     * 杭州富通通信技术股份有限公司
     */
    @Excel(name = "杭州富通通信技术股份有限公司")
    private String entityName;
    /**
     * 代码
     */
    @Excel(name = "代码")
    private String code;
    /**
     * 招股日期 yyyy-mm-dd
     */
    @Excel(name = "招股日期 yyyy-mm-dd")
    private Date prospDate;
    /**
     * 发行失败结果公告日 yyyy-mm-dd
     */
    @Excel(name = "发行失败结果公告日 yyyy-mm-dd")
    private Date issFailDate;
    /**
     * 发行价格(元)
     */
    @Excel(name = "发行价格(元)")
    private Double issPrice;
    /**
     * 发行数量(万股)
     */
    @Excel(name = "发行数量(万股)")
    private Double issCount;
    /**
     * 募资总额(万元)
     */
    @Excel(name = "募资总额(万元)")
    private Double fundTotal;
    /**
     * 发行费用(万元)
     */
    @Excel(name = "发行费用(万元)")
    private Double issCost;
    /**
     * 发行市盈率(倍)
     */
    @Excel(name = "发行市盈率(倍)")
    private Double issYield;
    /**
     * 发行方式
     */
    @Excel(name = "发行方式")
    private String issType;
    /**
     * 承销方式
     */
    @Excel(name = "承销方式")
    private String unwType;
    /**
     * 超额认购倍数
     */
    @Excel(name = "超额认购倍数")
    private Integer exceedSubcMult;
    /**
     * 网上发行日期 yyyy-mm-dd
     */
    @Excel(name = "网上发行日期 yyyy-mm-dd")
    private Date issNetDate;
    /**
     * 主承销商
     */
    @Excel(name = "主承销商")
    private String mainUnw;
    /**
     * 公司简介
     */
    @Excel(name = "公司简介")
    private String entityDes;

    /**
     * wind_task 的id
     */
    @Excel(name = "wind_task 的id")
    private Integer  taskId ;

    /**
     * 导入日期 yyyy-mm-dd
     */
    @Excel(name = "导入日期 yyyy-mm-dd")
    private DateTime importTime;

    /**
     * 数据变化类型 1-新增 2-更新
     */
    @Excel(name = "数据变化类型 1-新增 2-更新")
    private Integer  changeType;


}
