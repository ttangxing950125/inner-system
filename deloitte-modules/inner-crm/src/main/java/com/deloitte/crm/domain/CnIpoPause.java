package com.deloitte.crm.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import java.util.Objects;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.crm.utils.EqualsUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

/**
 * IPO-发行暂缓(CnIpoPause)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:54
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CnIpoPause implements Serializable {
    private static final long serialVersionUID = -33711356804257814L;
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String entityName;
    /**
     * 代码
     */
    @Excel(name = "代码")
    private String code;
    /**
     * 招股日期
     */
    @Excel(name = "招股日期")
    private Date prospDate;
    /**
     * 发行暂缓结果公告日
     */
    @Excel(name = "发行暂缓结果公告日")
    private Date issSusDate;
    /**
     * 发行价格(元)
     */
    @Excel(name = "发行价格(元)")
    private BigDecimal issPrice;
    /**
     * 发行数量(万股)
     */
    @Excel(name = "发行数量(万股)")
    private BigDecimal issCount;
    /**
     * 募资总额(万元)
     */
    @Excel(name = "募资总额(万元)")
    private BigDecimal fundTotal;
    /**
     * 发行费用(万元)
     */
    @Excel(name = "发行费用(万元)")
    private BigDecimal issCost;
    /**
     * 发行市盈率(倍)
     */
    @Excel(name = "发行市盈率(倍)")
    private BigDecimal issMult;
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
    private BigDecimal exceedSubcMult;
    /**
     * 网上发行日期
     */
    @Excel(name = "网上发行日期")
    private Date netIssDate;
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
    private Integer  taskId ;

    /**
     * 导入日期
     */
    private Date importTime;
    /**
     * 数据变化类型 1-新增 2-更新
     */
    private Integer  changeType;

    @Override
    public boolean equals(Object o) {
        return EqualsUtil.equalsAnnoField(this, o, Excel.class);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityName, code, prospDate, issSusDate, issPrice, issCount, fundTotal, issCost, issMult, issType, unwType, exceedSubcMult, netIssDate, mainUnw, entityDes);
    }
}
