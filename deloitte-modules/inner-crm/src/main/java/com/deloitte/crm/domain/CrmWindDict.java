package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.web.domain.BaseEntity;

/**
 * 导入的wind文件分类对象 crm_wind_dict
 * 
 * @author deloitte
 * @date 2022-09-21
 */
public class CrmWindDict extends BaseEntity
{
    private static final Long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** wind文件分类名 */
    @Excel(name = "wind文件分类名")
    private String cateName;
    /** windCateId */
    @Excel(name = "windCateId")
    private Integer cateId;

    /** wind文件具体名称 */
    @Excel(name = "wind文件具体名称")
    private String windFileName;

    /** 文件数据存放在哪个数据表中 */
    @Excel(name = "文件数据存放在哪个数据表中")
    private String fileTable;

    /** 每天的权利文件数据放在哪个数据表中 */
    @Excel(name = "每天的权利文件数据放在哪个数据表中")
    private String fileTableHis;

    /** wind文件任务描述 */
    @Excel(name = "wind文件任务描述")
    private String taskDesc;

    /** 任务文件状态 0-禁用 1-启用 */
    @Excel(name = "任务文件状态 0-禁用 1-启用")
    private Integer status;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date created;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date updated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCateName(String cateName)
    {
        this.cateName = cateName;
    }

    public String getCateName() 
    {
        return cateName;
    }
    public void setWindFileName(String windFileName) 
    {
        this.windFileName = windFileName;
    }

    public String getWindFileName() 
    {
        return windFileName;
    }
    public void setFileTable(String fileTable) 
    {
        this.fileTable = fileTable;
    }

    public String getFileTable() 
    {
        return fileTable;
    }
    public void setFileTableHis(String fileTableHis) 
    {
        this.fileTableHis = fileTableHis;
    }

    public String getFileTableHis() 
    {
        return fileTableHis;
    }
    public void setTaskDesc(String taskDesc) 
    {
        this.taskDesc = taskDesc;
    }

    public String getTaskDesc() 
    {
        return taskDesc;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
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

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
