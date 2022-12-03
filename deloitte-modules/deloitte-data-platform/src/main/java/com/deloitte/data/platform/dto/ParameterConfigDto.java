package com.deloitte.data.platform.dto;

import com.deloitte.common.core.annotation.Excel;
import com.deloitte.data.platform.common.BasePage;
import com.deloitte.data.platform.vo.ParameterConfigVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 参数配置
 *
 * @author zhengyingxiang
 * @date 2022/11/21 13:48:35
 */
public class ParameterConfigDto {

    @Data
    @ApiModel("参数配置列表")
    public static class ListDto extends BasePage {

        @ApiModelProperty(value = "搜索关键字")
        private String searchName;

        @ApiModelProperty(value = "数据层级 1:基础层 2:中间层 3:指标层")
        private Integer hierarchy;

        @ApiModelProperty(value = "主体类型")
        private String entityType;

    }

    @Data
    @ApiModel("修改(基础层)")
    public static class UpdateBaseDto{

        @ApiModelProperty(value = "id(非必传)")
        private Integer id;

        @ApiModelProperty(value = "字段名称")
        private String name;

        @ApiModelProperty(value = "字段代码")
        private String code;

        @ApiModelProperty(value = "人工补录优先级")
        private String artificialRecordingSeq;

        @ApiModelProperty(value = "自动化补录优先级")
        private String ocrSeq;

        @ApiModelProperty(value = "wind优先级")
        private String windSeq;

        @ApiModelProperty(value = "同花顺优先级")
        private String flushSeq;

        @ApiModelProperty(value = "变动率上限")
        private BigDecimal changeRateUpper;

        @ApiModelProperty(value = "值阈")
        private String thresholdValue;

        @ApiModelProperty(value = "精度")
        private Integer accuracy;
    }

    @Data
    @ApiModel("修改(中间层)")
    public static class UpdateMiddleDto{

        @ApiModelProperty(value = "id(非必传)")
        private Integer id;

        @ApiModelProperty(value = "字段名称")
        private String name;

        @ApiModelProperty(value = "字段代码")
        private String code;

        @ApiModelProperty(value = "变动率上限")
        private BigDecimal changeRateUpper;

        @ApiModelProperty(value = "值阈")
        private String thresholdValue;

        @ApiModelProperty(value = "精度")
        private Integer accuracy;

        @ApiModelProperty(value = "公式")
        private String formula;

        @ApiModelProperty(value = "异常值处理(Json字符串)")
        private String abnormalValueHandle;

//        @ApiModelProperty(value = "异常值处理(下拉列表)")
//        private List<ParameterConfigVo.ListVo.AbnormalValueHandleSelectsVo> abnormalValueHandleSelects;
//
//        @ApiModelProperty(value = "异常值处理(异常列表)")
//        private List<ParameterConfigVo.ListVo.AbnormalValueHandleSelectsVo> abnormalValueHandleList;
    }

    @Data
    @ApiModel("修改(指标层)")
    public static class UpdateApplyDto{

        @ApiModelProperty(value = "id(非必传)")
        private Integer id;

        @ApiModelProperty(value = "字段名称")
        private String name;

        @ApiModelProperty(value = "字段代码")
        private String code;

        @ApiModelProperty(value = "变动率上限")
        private BigDecimal changeRateUpper;

        @ApiModelProperty(value = "值阈")
        private String thresholdValue;

        @ApiModelProperty(value = "精度")
        private Integer accuracy;

        @ApiModelProperty(value = "公式")
        private String formula;

        @ApiModelProperty(value = "使用场景")
        private String businessScene;

        @ApiModelProperty(value = "异常值处理(Json字符串)")
        private String abnormalValueHandle;

        @ApiModelProperty(value = "异常值处理(下拉列表)")
        private List<ParameterConfigVo.ListVo.AbnormalValueHandleSelectsVo> abnormalValueHandleSelects;

        @ApiModelProperty(value = "异常值处理(异常列表)")
        private List<ParameterConfigVo.ListVo.AbnormalValueHandleSelectsVo> abnormalValueHandleList;
    }

    @Data
    @ApiModel("质检规则列表")
    public static class modelDataCheckListPageDto extends BasePage{

        @ApiModelProperty(value = "字段名称")
        private String searchName;

        @ApiModelProperty(value = "主体类型")
        private String entityType;

    }

    @Data
    @ApiModel("新增/修改质检规则")
    public static class UpdateOrAddDto {

        @ApiModelProperty(value = "id(非必传)")
        private Integer id;

        @ApiModelProperty(value = "公式")
        private String checkFormula;

        @ApiModelProperty(value = "描述")
        private String checkDescribe;

    }

    @Data
    @ApiModel("质检规则校验")
    public static class CheckDataDto {

        @ApiModelProperty(value = "公式")
        private String checkFormula;

    }
}
