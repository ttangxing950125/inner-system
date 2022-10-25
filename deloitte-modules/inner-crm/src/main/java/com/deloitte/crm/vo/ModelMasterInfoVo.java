package com.deloitte.crm.vo;

import com.deloitte.crm.dto.AttrValueMapDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author 正杰
 * @description: ModleMasterInfoVo
 * @date 2022/9/29
 */
@Data
@Accessors(chain = true)
public class ModelMasterInfoVo {

    /**
     * 企业名称
     */
    @ApiModelProperty(name = "entityName" ,value ="企业名称")
    private String entityName;

    /**
     * 统一社会信用代码
     */
    @ApiModelProperty(name = "creditCode" ,value ="统一社会信用代码")
    private String creditCode;

    /**
     * 来源
     */
    @ApiModelProperty(name = "source" ,value ="来源")
    private String source;

    /**
     * 用作 wind 和 申万 的值展示与修改
     */
    @ApiModelProperty(name = "attrs" ,value ="暂留数据，该数据作废")
    private Map<String,AttrValueMapDto> attrs;

    /**
     * wind行业划分
     */
    @ApiModelProperty(name = "windMaster" ,value ="wind行业划分")
    private String windMaster;

    /**
     * 申万行业划分
     */
    @ApiModelProperty(name = "shenWanMaster" ,value ="申万行业划分")
    private String shenWanMaster;

}
