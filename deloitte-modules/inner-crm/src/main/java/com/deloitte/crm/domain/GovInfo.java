package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;
import com.deloitte.common.core.annotation.Excel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 【请填写功能名称】对象 gov_info
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Accessors(chain = true)
@Data
public class GovInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 政府主体官方名称 */
    @Excel(name = "政府主体官方名称")
    private String govName;

    /** 官方行政代码，六位数字，各地方唯一 */
    @Excel(name = "官方行政代码，六位数字，各地方唯一")
    private String govCode;

    @Excel(name = "上级地方政府行政编码，六位数字，各地方唯一")
    private String preGovCode;

    /** 对于地方政府主体：
省级、地级、县级政府为“GV+官方行政代码”
经开区为“GVA”+000001开始排序
高新区为“GVB”+000001开始排序
新区为“GVC”+000001开始排序
其他类型区域暂以“GVZ”+000001开始排序 */
    @Excel(name = "对于地方政府主体：省级、地级、县级政府为“GV+官方行政代码”经开区为“GVA”+000001开始排序高新区为“GVB”+000001开始排序新区为“GVC”+000001开始排序其他类型区域暂以“GVZ”+000001开始排序")
    private String dqGovCode;

    /** sys_dict_data  gov_type
1、地方政府
2、地方主管部门
3、其他 */
    @Excel(name = "sys_dict_data  gov_type1、地方政府2、地方主管部门3、其他")
    private Integer govType;

    /** sys_dict_data gov_level_big */
    @Excel(name = "sys_dict_data gov_level_big")
    private Integer govLevelBig;

    /** sys_dict_data gov_level_small */
    @Excel(name = "sys_dict_data gov_level_small")
    private Integer govLevelSmall;

    /** 汇总合并根据企业主体-匹配表中主体所有的曾用名或别称，存在多个时以“、”区分

每次有操作曾用名表的时候，重新更新这个字段 */
    @Excel(name = "汇总合并根据企业主体-匹配表中主体所有的曾用名或别称，存在多个时以“、”区分每次有操作曾用名表的时候，重新更新这个字段")
    private String govNameHis;

    /** 汇总合并根据企业主体-匹配表中主体所有的曾用名或别称备注，形式为日期+更新人+备注；日期+更新人+备注 */
    @Excel(name = "汇总合并根据企业主体-匹配表中主体所有的曾用名或别称备注，形式为日期+更新人+备注；日期+更新人+备注")
    private String entityNameHisRemarks;

    /** 失效 0-有效 1-失效
一般来说，一家政府如果升级改名改编码，我们会将原政府实现，然后添加新政府 */
    @Excel(name = "失效 0-有效 1-失效一般来说，一家政府如果升级改名改编码，我们会将原政府实现，然后添加新政府")
    private Integer invalid;

    /** 失效后的新政府名，如果没有填写就是撤销 */
    @Excel(name = "失效后的新政府名，如果没有填写就是撤销")
    private String newGovName;

    /** 失效后的新政府德勤code */
    @Excel(name = "失效后的新政府德勤code")
    private String newDqCode;

    /** 失效后的新政府code */
    @Excel(name = "失效后的新政府code")
    private String newGovCode;

    /** 创建数据的用户名 */
    @Excel(name = "创建数据的用户名")
    private String creater;

    /** 更新数据的用户名 */
    @Excel(name = "更新数据的用户名")
    private String updater;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date created;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date updated;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGovName(String govName) 
    {
        this.govName = govName;
    }

    public String getGovName() 
    {
        return govName;
    }
    public void setGovCode(String govCode) 
    {
        this.govCode = govCode;
    }

    public String getGovCode() 
    {
        return govCode;
    }
    public void setDqGovCode(String dqGovCode) 
    {
        this.dqGovCode = dqGovCode;
    }

    public String getPreGovCode()
    {
        return preGovCode;
    }
    public void setPreGovCode(String preGovCode)
    {
        this.preGovCode = preGovCode;
    }

    public String getDqGovCode() 
    {
        return dqGovCode;
    }
    public void setGovType(Integer govType) 
    {
        this.govType = govType;
    }

    public Integer getGovType() 
    {
        return govType;
    }
    public void setGovLevelBig(Integer govLevelBig) 
    {
        this.govLevelBig = govLevelBig;
    }

    public Integer getGovLevelBig() 
    {
        return govLevelBig;
    }
    public void setGovLevelSmall(Integer govLevelSmall) 
    {
        this.govLevelSmall = govLevelSmall;
    }

    public Integer getGovLevelSmall() 
    {
        return govLevelSmall;
    }
    public void setGovNameHis(String govNameHis) 
    {
        this.govNameHis = govNameHis;
    }

    public String getGovNameHis() 
    {
        return govNameHis;
    }
    public void setEntityNameHisRemarks(String entityNameHisRemarks) 
    {
        this.entityNameHisRemarks = entityNameHisRemarks;
    }

    public String getEntityNameHisRemarks() 
    {
        return entityNameHisRemarks;
    }
    public void setInvalid(Integer invalid) 
    {
        this.invalid = invalid;
    }

    public Integer getInvalid() 
    {
        return invalid;
    }
    public void setNewGovName(String newGovName) 
    {
        this.newGovName = newGovName;
    }

    public String getNewGovName() 
    {
        return newGovName;
    }
    public void setNewDqCode(String newDqCode) 
    {
        this.newDqCode = newDqCode;
    }

    public String getNewDqCode() 
    {
        return newDqCode;
    }
    public void setNewGovCode(String newGovCode) 
    {
        this.newGovCode = newGovCode;
    }

    public String getNewGovCode() 
    {
        return newGovCode;
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
