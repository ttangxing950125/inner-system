package com.deloitte.crm.domain.dto;

import com.alibaba.fastjson.JSON;
import com.deloitte.crm.domain.GovInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 【请填写功能名称】对象 gov_info
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Data
public class GovInfoByDto implements Serializable {
    private static final long serialVersionUID = 1L;

    public GovInfo getGovInfo() {
        return govInfo;
    }

    public void setGovInfo() {
        GovInfo govInfo = new GovInfo();
        govInfo.setCreated(this.created);
        govInfo.setUpdated(this.updated);
        govInfo.setId(this.id);
        govInfo.setGovName(this.govName);
        govInfo.setGovCode(this.govCode);
        govInfo.setPreGovCode(this.preGovCode);
        govInfo.setDqGovCode(this.dqGovCode);
        govInfo.setGovType(this.govType);
        govInfo.setGovLevelBig(this.govLevelBig);
        govInfo.setGovLevelSmall(this.govLevelSmall);
        govInfo.setGovNameHis(this.govNameHis);
        govInfo.setGovNameHis(this.entityNameHisRemarks);
        govInfo.setInvalid(this.invalid);
        govInfo.setNewGovName(this.newGovName);
        govInfo.setNewDqCode(this.newDqCode);
        govInfo.setNewGovCode(this.newGovCode);
        govInfo.setCreater(this.creater);
        govInfo.setUpdater(this.updater);
        this.govInfo = govInfo;
    }

    private GovInfo govInfo;

    private Long id;

    private String govName;

    private String param;

    private String govCode;

    private String preGovCode;

    private String dqGovCode;

    private Integer govType;

    private Integer govLevelBig;

    private Integer govLevelSmall;

    private String govNameHis;

    private String entityNameHisRemarks;

    private Integer invalid;

    private String newGovName;

    private String newDqCode;

    private String newGovCode;

    private String creater;

    private String updater;

    private Date created;

    private Date updated;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    private Integer pageSize;

    private Integer pageNum;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setGovName(String govName) {
        this.govName = govName;
    }

    public String getGovName() {
        return govName;
    }

    public void setGovCode(String govCode) {
        this.govCode = govCode;
    }

    public String getGovCode() {
        return govCode;
    }

    public void setDqGovCode(String dqGovCode) {
        this.dqGovCode = dqGovCode;
    }

    public String getPreGovCode() {
        return preGovCode;
    }

    public void setPreGovCode(String preGovCode) {
        this.preGovCode = preGovCode;
    }

    public String getDqGovCode() {
        return dqGovCode;
    }

    public void setGovType(Integer govType) {
        this.govType = govType;
    }

    public Integer getGovType() {
        return govType;
    }

    public void setGovLevelBig(Integer govLevelBig) {
        this.govLevelBig = govLevelBig;
    }

    public Integer getGovLevelBig() {
        return govLevelBig;
    }

    public void setGovLevelSmall(Integer govLevelSmall) {
        this.govLevelSmall = govLevelSmall;
    }

    public Integer getGovLevelSmall() {
        return govLevelSmall;
    }

    public void setGovNameHis(String govNameHis) {
        this.govNameHis = govNameHis;
    }

    public String getGovNameHis() {
        return govNameHis;
    }

    public void setEntityNameHisRemarks(String entityNameHisRemarks) {
        this.entityNameHisRemarks = entityNameHisRemarks;
    }

    public String getEntityNameHisRemarks() {
        return entityNameHisRemarks;
    }

    public void setInvalid(Integer invalid) {
        this.invalid = invalid;
    }

    public Integer getInvalid() {
        return invalid;
    }

    public void setNewGovName(String newGovName) {
        this.newGovName = newGovName;
    }

    public String getNewGovName() {
        return newGovName;
    }

    public void setNewDqCode(String newDqCode) {
        this.newDqCode = newDqCode;
    }

    public String getNewDqCode() {
        return newDqCode;
    }

    public void setNewGovCode(String newGovCode) {
        this.newGovCode = newGovCode;
    }

    public String getNewGovCode() {
        return newGovCode;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreater() {
        return creater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getUpdater() {
        return updater;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getUpdated() {
        return updated;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
