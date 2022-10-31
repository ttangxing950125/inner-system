package com.deloitte.crm.dto;

import com.deloitte.crm.domain.GovNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author 正杰
 * @description: MasDto
 * @date 2022/10/9
 */
@Data
@Accessors(chain = true)
public class MasDto {

    /** 主体名称 */
    @ApiModelProperty(value = "主体名称")
    private String entityName;

    /** 统一社会信用代码 */
    @ApiModelProperty(value = "统一社会信用代码")
    private String creditCode;

    /** 来源 */
    @ApiModelProperty(value = "来源")
    private String source;

    /**
     * 数据来源
     */
    @ApiModelProperty(value = "数据来源")
    private String sourceName;

    /**
     * mas 的 id
     */
    @ApiModelProperty(value = "mas 的 id")
    private Integer id;

    /**
     * wind 数据
     */
    @ApiModelProperty(value = "wind 数据")
    private String wind;

    /**
     * 申万
     */
    @ApiModelProperty(value = "申万")
    private String shenWan;

    /**
     * 主体code
     */
    @ApiModelProperty(value = "主体code")
    @NotNull(message = "企业的主体code可能 无效 或是 空")
    private String entityCode;

    /**
     * 640
     * 是否为金融机构（名单判断）
     * 1、Y：若在已有目前名单中存在，即为金融机构
     * 2、N：若在已有目前名单中不存在，即为非名单判断金融机构
     */
    @ApiModelProperty(value = "是否为金融机构（名单判断）")
    @NotNull(message = "是否为金融机构 不能为空")
    private String isFinance;

    /**
     *
     * 金融机构细分行业
     */
    @ApiModelProperty(value = "金融机构细分行业")
    private String financeSegmentation;

    /**
     * 644
     * 是否为城投机构（YY)
     * 1、Y：是YY口径下城投机构
     * 2、N：不是YY口径下城投机构
     */
    @ApiModelProperty(value = "是否为城投机构（YY)")
    @NotNull(message = "是否为城投机构 不能为空")
    private String city;

    /**
     * 645
     * 中诚信-是否为城投机构
     * 1、Y：是中诚信口径下城投机构
     * 2、N：不是中诚信口径下城投机构
     */
    @ApiModelProperty(value = "中诚信-是否为城投机构")
    @NotNull(message = "中诚信-是否为城投机构 不能为空")
    private String cityZhong;

    /**
     * 642
     * IB-是否为城投机构
     * 1、Y：是YY口径下城投机构
     * 2、N：不是YY口径下城投机构
     */
    @ApiModelProperty(value = "IB-是否为城投机构")
    @NotNull(message = "IB-是否为城投机构 不能为空")
    private String cityIb;

    @ApiModelProperty(value = "政府名称节点")
    private GovNode govNode;

    /** 官方行政代码，六位数字，各地方唯一 */
    @ApiModelProperty(value = "官方行政代码，六位数字，各地方唯一")
    private String govCode;

    /**
     * 德勤政府code
     * 需要通过这个字段取 entity gov rel 新增一个 entitycode和 它的关联关系
     */
    @ApiModelProperty(value = "德勤政府code")
    private String dqGovCode;

    /**
     * 敞口的code
     *  需要通过这个字段取 entity_master 新增一个 entitycode和 它的关联关系
     */
    @ApiModelProperty(value = "敞口的code")
    @NotNull(message = "敞口的code 不能为空")
    private String masterCode;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remarks;
}
