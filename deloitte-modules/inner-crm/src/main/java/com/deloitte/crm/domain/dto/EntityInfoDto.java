package com.deloitte.crm.domain.dto;

import com.alibaba.fastjson.JSON;
import com.deloitte.crm.domain.EntityInfo;

import java.io.Serializable;
import java.util.Date;

/**
 * 【请填写功能名称】对象 entity_info
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public class EntityInfoDto implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Long id;

    private EntityInfo entityInfo;

    private String entityName;

    private String entityCode;

    private String creditCode;

    private Integer list;

    private Integer issueBonds;

    private Integer creditError;

    private Integer creditErrorType;

    private String entityNameHis;

    private String entityNameHisRemarks;

    private String creater;

    private String updater;

    private Date created;

    private Date updated;

    private Integer pageSize;

    private Integer pageNum;
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
    public EntityInfo getEntityInfo() {
        EntityInfo entityInfo = new EntityInfo();
        entityInfo.setEntityName(this.entityName);
        entityInfo.setEntityCode(this.entityCode);
        entityInfo.setCreditCode(this.creditCode);
        return entityInfo;
    }

    public void setEntityInfo() {
        EntityInfo entityInfo = new EntityInfo();
        entityInfo.setEntityName(this.entityName);
        entityInfo.setEntityCode(this.entityCode);
        entityInfo.setCreditCode(this.creditCode);
        this.entityInfo = entityInfo;
    }

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setEntityName(String entityName) 
    {
        this.entityName = entityName;
    }

    public String getEntityName() 
    {
        return entityName;
    }
    public void setEntityCode(String entityCode) 
    {
        this.entityCode = entityCode;
    }

    public String getEntityCode() 
    {
        return entityCode;
    }
    public void setCreditCode(String creditCode) 
    {
        this.creditCode = creditCode;
    }

    public String getCreditCode() 
    {
        return creditCode;
    }
    public void setList(Integer list) 
    {
        this.list = list;
    }

    public Integer getList() 
    {
        return list;
    }
    public void setIssueBonds(Integer issueBonds) 
    {
        this.issueBonds = issueBonds;
    }

    public Integer getIssueBonds() 
    {
        return issueBonds;
    }
    public void setCreditError(Integer creditError) 
    {
        this.creditError = creditError;
    }

    public Integer getCreditError() 
    {
        return creditError;
    }
    public void setCreditErrorType(Integer creditErrorType) 
    {
        this.creditErrorType = creditErrorType;
    }

    public Integer getCreditErrorType() 
    {
        return creditErrorType;
    }
    public void setEntityNameHis(String entityNameHis) 
    {
        this.entityNameHis = entityNameHis;
    }

    public String getEntityNameHis() 
    {
        return entityNameHis;
    }
    public void setEntityNameHisRemarks(String entityNameHisRemarks) 
    {
        this.entityNameHisRemarks = entityNameHisRemarks;
    }

    public String getEntityNameHisRemarks() 
    {
        return entityNameHisRemarks;
    }
    public void setCreater(String creater) 
    {
        this.creater = creater;
    }

    public String getCreater() 
    {
        return creater;
    }
    public void setUpdater(String updater) 
    {
        this.updater = updater;
    }

    public String getUpdater() 
    {
        return updater;
    }
    public void setCreated(Date created) 
    {
        this.created = created;
    }

    public Date getCreated() 
    {
        return created;
    }
    public void setUpdated(Date updated) 
    {
        this.updated = updated;
    }

    public Date getUpdated() 
    {
        return updated;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
