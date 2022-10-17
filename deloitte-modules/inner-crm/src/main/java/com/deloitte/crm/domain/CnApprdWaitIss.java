package com.deloitte.crm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * IPO-审核通过尚未发行(CnApprdWaitIss)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 18:27:46
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CnApprdWaitIss implements Serializable {
    private static final long serialVersionUID = 450243973827664054L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * wind_task 的id
     */
    private Integer taskId;

    /**
     * 导入日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date importTime;

    /**
     * 数据变化类型 1-新增 2-更新
     */
    private Integer  changeType;

    /**
     * 代码
     */
    @Excel(name = "代码")
    private String code;
    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String entityName;
    /**
     * 省份
     */
    @Excel(name = "省份")
    private String province;
    /**
     * 城市
     */
    @Excel(name = "城市")
    private String city;
    /**
     * 审核通过会议日期
     */
    @Excel(name = "审核通过会议日期")
    private Date approvedDate;
    /**
     * 上市板
     */
    @Excel(name = "上市板")
    private String ipoBoard;
    /**
     * 主承销商
     */
    @Excel(name = "主承销商")
    private String mainUnw;
    /**
     * 预计发行股数(万股)
     */
    @Excel(name = "预计发行股数(万股)")
    private Double estIssNum;
    /**
     * 预计募集资金(万元)
     */
    @Excel(name = "预计募集资金(万元)")
    private Double estFundNum;
    /**
     * 交易所
     */
    @Excel(name = "交易所")
    private String exchange;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CnApprdWaitIss that = (CnApprdWaitIss) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(entityName, that.entityName) &&
                Objects.equals(province, that.province) &&
                Objects.equals(city, that.city) &&
                Objects.equals(approvedDate, that.approvedDate) &&
                Objects.equals(ipoBoard, that.ipoBoard) &&
                Objects.equals(mainUnw, that.mainUnw) &&
                Objects.equals(estIssNum, that.estIssNum) &&
                Objects.equals(estFundNum, that.estFundNum) &&
                Objects.equals(exchange, that.exchange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, entityName, province, city, approvedDate, ipoBoard, mainUnw, estIssNum, estFundNum, exchange);
    }
}
