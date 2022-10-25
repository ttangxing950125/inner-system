package com.deloitte.crm.vo;

import com.deloitte.crm.annotation.Attrs;
import com.deloitte.crm.domain.EntityFinancial;
import com.deloitte.crm.domain.EntityGovRel;
import com.deloitte.crm.domain.EntityInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色3，4，5补充录入查询和回显 实体类
 *
 * @author 冉浩岑
 * @date 2022/10/24 11:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EntitySupplyMsgBack {
//    统一基础信息
    /**
     * 任务ID
     */
    private Integer taskId;
    /**
     * 任务状态
     */
    private Integer state;

    /**
     * 德勤企业识别码
     */
    private String entityCode;

    /**
     * 来源---来源于任务列表
     */
    private String source;
    /**
     * 企业名称
     */
    private String entityName;
    /**
     * 统一社会信用代码
     */
    private String creditCode;
    /**
     * wind行业划分
     */
    private String windMaster;
    /**
     * 申万行业划分
     */
    private String shenWanMaster;
    /**
     * 财报列示类型
     */
    private String listType;
    /**
     * 关注报告类型
     */
    private List<String> reportType;


    /**
     * 备注
     */
    private String remarks;

    //角色 3 属性字段
    /**
     * 所属地区
     */
    @Attrs(attrId = 983, attrName = "所属地区")
    private String belPlace;
    /**
     * 所属辖区
     */
    @Attrs(attrId = 658, attrName = "所属辖区")
    private String belJurisdiction;
    /**
     * 对口监管机构
     */
    @Attrs(attrId = 657, attrName = "对口监管机构")
    private String regulators;


    //角色 4 属性字段
    /**
     * 政府持股方式
     */
    @Attrs(attrId = 676, attrName = "政府持股方式")
    private String shareMethod;
    /**
     * 政府对当前城投支持力度
     */
    @Attrs(attrId = 677, attrName = "政府对当前城投支持力度")
    private String support;
    /**
     * 政府对当前城投支持力度判断依据
     */
    @Attrs(attrId = 981, attrName = "政府对当前城投支持力度判断依据")
    private String judgment;
    /**
     * 政府部门实际持股比例
     */
    @Attrs(attrId = 678, attrName = "政府部门实际持股比例")
    private String shareRatio;
    /**
     * 政府部门实际持股比例年份
     */
    @Attrs(attrId = 982, attrName = "政府部门实际持股比例年份")
    private String shareRatioYear;
    /**
     * 德勤政府唯一识别码
     */
    private String dqGovCode;


    public EntityGovRel newEntityGovRel() {
        EntityGovRel entityGovRel = new EntityGovRel();
        entityGovRel.setEntityCode(this.entityCode)
                .setDqGovCode(this.dqGovCode)
                .setShareMethod(this.shareMethod)
                .setSupport(this.support)
                .setJudgment(this.judgment)
                .setShareRatio(this.shareRatio)
                .setShareRatioYear(this.shareRatioYear)
                .setRemarks(this.remarks);
        return entityGovRel;
    }

    public void haveEntityGovRel(EntityGovRel entityGovRel) {
        this.entityCode = entityGovRel.getEntityCode();
        this.dqGovCode = entityGovRel.getDqGovCode();
        this.shareMethod = entityGovRel.getShareMethod();
        this.support = entityGovRel.getSupport();
        this.judgment = entityGovRel.getJudgment();
        this.shareRatio = entityGovRel.getShareRatio();
        this.shareRatioYear = entityGovRel.getShareRatioYear();
        this.remarks = entityGovRel.getRemarks();
    }

    public EntityInfo newEntityInfo() {
        EntityInfo entityInfo = new EntityInfo();
        entityInfo.setEntityCode(this.entityCode)
                .setEntityName(this.entityName)
                .setCreditCode(this.creditCode)
                .setWindMaster(this.windMaster)
                .setShenWanMaster(this.shenWanMaster)
                .setListType(this.listType);
        String value = "";
        if (!CollectionUtils.isEmpty(this.reportType)) {
            StringBuffer str = new StringBuffer();
            for (int i = 0; i < this.reportType.size(); i++) {
                str.append(reportType.get(i));
                if (i < this.reportType.size() - 1) {
                    str.append(",");
                }
            }
            value = str.toString();
        }
        entityInfo.setReportType(value);
        return entityInfo;
    }

    public void haveEntityInfo(EntityInfo entityInfo) {
        this.entityCode = entityInfo.getEntityCode();
        this.entityName = entityInfo.getEntityName();
        this.creditCode = entityInfo.getCreditCode();
        this.windMaster = entityInfo.getEntityCode();
        this.shenWanMaster = entityInfo.getEntityCode();
        this.listType = entityInfo.getEntityCode();
        String reportType = entityInfo.getReportType();

        if (!ObjectUtils.isEmpty(reportType)) {
            String[] split = reportType.split(",");
            if (split.length > 0) {
                List<String> list = new ArrayList<>();
                for (String var : split) {
                    if (!ObjectUtils.isEmpty(var)) {
                        list.add(var);
                    }
                }
                this.reportType = list;
            }
        }
    }

    public EntityFinancial newEntityFinancial() {
        EntityFinancial entityFinancial = new EntityFinancial();
        entityFinancial.setEntityCode(this.entityCode)
                .setBelJurisdiction(this.belJurisdiction)
                .setBelPlace(this.belPlace)
                .setRegulators(this.regulators)
                .setRemarks(this.remarks);
        return entityFinancial;
    }

    public void haveEntityFinancial(EntityFinancial entityFinancial) {
        this.entityCode = entityFinancial.getEntityCode();
        this.belJurisdiction = entityFinancial.getBelJurisdiction();
        this.belPlace = entityFinancial.getBelPlace();
        this.regulators = entityFinancial.getRegulators();
        this.remarks = entityFinancial.getRemarks();
    }
}
