package com.deloitte.crm.domain;

import java.util.Date;
import java.io.Serializable;
import java.util.Objects;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import com.deloitte.common.core.annotation.Excel;
import org.stringtemplate.v4.ST;

/**
 * 证券发行-股票发行-聆讯信息一览(ThkSecIssInfo)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-09-25 17:14:35
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ThkSecIssInfo implements Serializable {
    private static final long serialVersionUID = -62590796069072893L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 任务id
     */
    private Integer taskId;
    /**
     * 导入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date importTime;
    /**
     * 新增 2修改
     */
    private Integer changeType;

    /**
     * 证券简称
     */
    @Excel(name = "证券代码")
    private String code;
    /**
     * 证券名称
     */
    @Excel(name = "证券简称")
    private String name;
    /**
     * 最新公告日期
     */
    @Excel(name = "最新公告日", dateFormat = "yyyy-MM-dd")
    private String infoDate;
    /**
     * 申请状态
     */
    @Excel(name = "申请状态")
    private String status;
    /**
     * 公司中文名称
     */
    @Excel(name = "公司中文名称")
    private String entityCnName;
    /**
     * 公司英文名称
     */
    @Excel(name = "公司英文名称")
    private String entityUkName;
    /**
     * 拟上市板块
     */
    @Excel(name = "拟上市板块")
    private String simulationListed;
    /**
     * 成立时间
     */
    @Excel(name = "成立日期",dateFormat = "yyyyMMdd")
    private String createDate;
    /**
     * 注册地址
     */
    @Excel(name = "注册地址")
    private String registerAddress;
    /**
     * 办公地址
     */
    @Excel(name = "办公地址")
    private String entityAddress;
    /**
     * 公司网址
     */
    @Excel(name = "公司网址")
    private String entityWeb;
    /**
     * 公司邮箱
     */
    @Excel(name = "公司邮箱")
    private String entityMail;
    /**
     * 公司电话
     */
    @Excel(name = "公司电话")
    private String entityPhone;
    /**
     * 公司传真
     */
    @Excel(name = "公司传真")
    private String entityFax;
    /**
     * 年结日 mm-dd
     */
    @Excel(name = "年结日")
    private String yearBillDate;
    /**
     * 员工数
     */
    @Excel(name = "员工数")
    private Integer entityEmpCount;
    /**
     * 经营范围
     */
    @Excel(name = "经营范围")
    private String entityScope;
    /**
     * 公司描述
     */
    @Excel(name = "公司描述")
    private String entityDes;
    /**
     * 所属Wind行业
     */
    @Excel(name = "所属Wind行业")
    private String belWind;
    /**
     * 保荐人
     */
    @Excel(name = "保荐人")
    private String sponsor;
    /**
     * 全球协调人
     */
    @Excel(name = "全球协调人")
    private String coordinator;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThkSecIssInfo that = (ThkSecIssInfo) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(name, that.name) &&
                Objects.equals(infoDate, that.infoDate) &&
                Objects.equals(status, that.status) &&
                Objects.equals(entityCnName, that.entityCnName) &&
                Objects.equals(entityUkName, that.entityUkName) &&
                Objects.equals(simulationListed, that.simulationListed) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(registerAddress, that.registerAddress) &&
                Objects.equals(entityAddress, that.entityAddress) &&
                Objects.equals(entityWeb, that.entityWeb) &&
                Objects.equals(entityMail, that.entityMail) &&
                Objects.equals(entityPhone, that.entityPhone) &&
                Objects.equals(entityFax, that.entityFax) &&
                Objects.equals(yearBillDate, that.yearBillDate) &&
                Objects.equals(entityEmpCount, that.entityEmpCount) &&
                Objects.equals(entityScope, that.entityScope) &&
                Objects.equals(entityDes, that.entityDes) &&
                Objects.equals(belWind, that.belWind) &&
                Objects.equals(sponsor, that.sponsor) &&
                Objects.equals(coordinator, that.coordinator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, infoDate, status, entityCnName, entityUkName, simulationListed, createDate, registerAddress, entityAddress, entityWeb, entityMail, entityPhone, entityFax, yearBillDate, entityEmpCount, entityScope, entityDes, belWind, sponsor, coordinator);
    }
}
