package com.deloitte.crm.domain.dto;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.crm.domain.GovInfo;
import com.deloitte.crm.utils.TimeFormatUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * 政府主体分页查询--响应类
 * @author 冉浩岑
 * @date 2022/10/17 10:05
 */
@Data
@Accessors(chain = true)
public class GovInfoList {
    /**
     * $column.columnComment
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 政府主体官方名称
     */
    @Excel(name = "政府主体官方名称")
    private String govName;

    /**
     * 官方行政代码，六位数字，各地方唯一
     */
    @Excel(name = "官方行政代码，六位数字，各地方唯一")
    private String govCode;

    @Excel(name = "上级地方政府行政编码，六位数字，各地方唯一")
    private String preGovCode;

    /**
     * 对于地方政府主体：
     * 省级、地级、县级政府为“GV+官方行政代码”
     * 经开区为“GVA”+000001开始排序
     * 高新区为“GVB”+000001开始排序
     * 新区为“GVC”+000001开始排序
     * 其他类型区域暂以“GVZ”+000001开始排序
     */
    @Excel(name = "对于地方政府主体：省级、地级、县级政府为“GV+官方行政代码”经开区为“GVA”+000001开始排序高新区为“GVB”+000001开始排序新区为“GVC”+000001开始排序其他类型区域暂以“GVZ”+000001开始排序")
    private String dqGovCode;

    /**
     * sys_dict_data  gov_type
     * 1、地方政府
     * 2、地方主管部门
     * 3、其他
     */
    @Excel(name = "sys_dict_data  gov_type1、地方政府2、地方主管部门3、其他")
    private Integer govType;

    /**
     * sys_dict_data gov_level_big
     */
    @Excel(name = "sys_dict_data gov_level_big")
    private Integer govLevelBig;

    /**
     * sys_dict_data gov_level_small
     */
    @Excel(name = "sys_dict_data gov_level_small")
    private Integer govLevelSmall;

    /**
     * 汇总合并根据企业主体-匹配表中主体所有的曾用名或别称，存在多个时以“、”区分
     * <p>
     * 每次有操作曾用名表的时候，重新更新这个字段
     */
    @Excel(name = "汇总合并根据企业主体-匹配表中主体所有的曾用名或别称，存在多个时以“、”区分每次有操作曾用名表的时候，重新更新这个字段")
    private String govNameHis;

    /**
     * 汇总合并根据企业主体-匹配表中主体所有的曾用名或别称备注，形式为日期+更新人+备注；日期+更新人+备注
     */
    @Excel(name = "汇总合并根据企业主体-匹配表中主体所有的曾用名或别称备注，形式为日期+更新人+备注；日期+更新人+备注")
    private String entityNameHisRemarks;

    /**
     * 失效 0-有效 1-失效
     * 一般来说，一家政府如果升级改名改编码，我们会将原政府实现，然后添加新政府
     */
    @Excel(name = "失效 0-有效 1-失效一般来说，一家政府如果升级改名改编码，我们会将原政府实现，然后添加新政府")
    private Integer invalid;

    /**
     * 失效后的新政府名，如果没有填写就是撤销
     */
    @Excel(name = "失效后的新政府名，如果没有填写就是撤销")
    private String newGovName;

    /**
     * 失效后的新政府德勤code
     */
    @Excel(name = "失效后的新政府德勤code")
    private String newDqCode;
    /**
     * 失效后的新政府德勤code
     */
    @Excel(name = "经济区归属")
    private String economyRegion;
    /**
     * 失效后的新政府德勤code
     */
    @Excel(name = "城市群归属")
    private String cityGroup;
    /**
     * 失效后的新政府德勤code
     */
    @Excel(name = "是否为省会城市 0.否 1.是")
    private Integer provincial;

    /**
     * 失效后的新政府德勤code
     */
    @Excel(name = "是否为国家中心城市 0.否 1.是")
    private Integer countryCenter;

    /**
     * 失效后的新政府code
     */
    @Excel(name = "失效后的新政府code")
    private String newGovCode;

    /**
     * 创建数据的用户名
     */
    @Excel(name = "创建数据的用户名")
    private String creater;

    /**
     * 更新数据的用户名
     */
    @Excel(name = "更新数据的用户名")
    private String updater;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date created;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date updated;
    /**
     * 主体状态 是否生效 0-失效 1-生效
     */
    @Excel(name = "主体状态")
    private Integer status;

    /**
     * 关键备注提醒
     */
    @Excel(name = "关键备注提醒")
    private String remark;

    /**
     * 关键备注提醒
     */
    @Excel(name = "更新记录")
    private String updateRecord;

    public void newUpdateRecord(GovInfoList govInfoList) {
        try {
            String type="创建";
            Date updated = govInfoList.getUpdated();
            long update = updated.getTime();
            long create = govInfoList.getCreated().getTime();
            if (create-update!=0l){
                type="修改";
            }
            String formartDate = TimeFormatUtil.getFormartDate(updated);
            String updater = govInfoList.getUpdater();
            if (ObjectUtils.isEmpty(updater)){
                updater="-";
            }
            this.updateRecord=formartDate+" "+updater+" "+type;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setGovInfo(GovInfo govInfo) {
        this.govNameHis = govInfo.getGovNameHis();
        this.govName = govInfo.getGovName();
        this.entityNameHisRemarks = govInfo.getEntityNameHisRemarks();
        this.dqGovCode = govInfo.getDqGovCode();
        this.govLevelBig = govInfo.getGovLevelBig();
        this.govCode = govInfo.getGovCode();
        this.govType = govInfo.getGovType();
        this.id = govInfo.getId();
        this.preGovCode = govInfo.getPreGovCode();
        this.created = govInfo.getCreated();
        this.creater = govInfo.getCreater();
        this.invalid = govInfo.getInvalid();
        this.status = govInfo.getStatus();
        this.updated = govInfo.getUpdated();
        this.cityGroup = govInfo.getCityGroup();
        this.countryCenter = govInfo.getCountryCenter();
        this.economyRegion = govInfo.getEconomyRegion();
        this.govLevelSmall = govInfo.getGovLevelSmall();
        this.newDqCode = govInfo.getNewDqCode();
        this.newGovCode = govInfo.getNewGovCode();
        this.newGovName = govInfo.getNewGovName();
        this.provincial = govInfo.getProvincial();
        this.updater = govInfo.getUpdater();
        this.remark = govInfo.getRemark();
    }
    //曾用名数量
    private Integer nameUsedNum;

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
