package com.deloitte.crm.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.crm.annotation.Attrs;
import com.deloitte.crm.constants.Common;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 冉浩岑
 * @date 2022/10/13 15:10
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class EntityBaseBusiInfo {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 主体编码
     */
    @Excel(name = "主体编码")
    private String entityCode;

    /**
     * 注册地址
     */
    @Excel(name = "注册地址")
    @Attrs(attrId = 716,attrName = "注册地址")
    private String regAddr;

    /**
     * 注册地所在省
     */
    @Excel(name = "注册地所在省")
    @Attrs(attrId = 717,attrName = "注册地所在省")
    private String regProvince;

    /**
     * 法人名称
     */
    @Excel(name = "法人名称")
    @Attrs(attrId = 723,attrName = "法人名称")
    private String ceoName;

    /**
     * 法人类型
     */
    @Excel(name = "法人类型 1.自然人")
    @Attrs(attrId = 724,attrName = "法人类型")
    private Integer ceoType;


    /**
     * 公司类型
     */
    @Excel(name = "公司类型 1.民营企业 2.合资企业 3.外企 4.国有企业")
    @Attrs(attrId = 725,attrName = "公司类型")
    private Integer comType;

    /**
     * 成立日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "成立日期 形式为yyyy-mm-dd")
    @Attrs(attrId = 726,attrName = "成立日期")
    private Date establishDate;

    /**
     * 营业期限开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "营业期限开始日期")
    @Attrs(attrId = 727,attrName = "营业期限开始日期")
    private Date busStartDate;

    /**
     * 营业期限截止日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "营业期限截止日期")
    @Attrs(attrId = 728,attrName = "营业期限终止日期")
    private Date busEndDate;

    /** $column.columnComment */
    @Attrs(attrId = 0,attrName = Common.CREATED)
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date created;

    /** $column.columnComment */
    @Attrs(attrId = 0,attrName = Common.UPDATED)
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date updated;

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
