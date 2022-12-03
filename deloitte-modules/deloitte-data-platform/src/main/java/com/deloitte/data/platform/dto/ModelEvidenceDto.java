package com.deloitte.data.platform.dto;

import com.deloitte.data.platform.common.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据配置
 *
 * @author zhengyingxiang
 * @date 2022/11/20
 */
public abstract class ModelEvidenceDto {

    @Data
    @ApiModel("数据配置列表")
    public static class ListDto extends BasePage {

        @ApiModelProperty(value = "数据层级 2:中间层 3:指标层")
        private Integer hierarchy;

        @ApiModelProperty(value = "搜索关键字")
        private String searchName;

    }

    @Data
    @ApiModel("财务数据配置列表")
    public static class FinancialDataConfigListDto extends BasePage {

        @ApiModelProperty(value = "搜索关键字")
        private String searchName;

    }

    @Data
    @ApiModel("删除配置")
    public static class DeleteDto {

        @ApiModelProperty(value = "数据层级 2:中间层 3:指标层")
        private Integer hierarchy;

        @ApiModelProperty(value = "id")
        private Integer id;

    }

    @Data
    @ApiModel("新增/修改数据配置")
    public static class AddDto{

        @ApiModelProperty(value = "ID 修改传")
        private Integer id;

        @ApiModelProperty(value = "数据层级 2:中间层 3:指标层")
        private Integer hierarchy;

        @ApiModelProperty(value = "名称")
        private String name;

        @ApiModelProperty(value = "code")
        private String code;

        @ApiModelProperty(value = "年份")
        private String year;

        @ApiModelProperty(value = "文字公式")
        private String formulaDescribe;

        @ApiModelProperty(value = "公式")
        private String formula;

        @ApiModelProperty(value = "单位")
        private String unit;

        @ApiModelProperty(value = "精度")
        private Integer accuracy;

        @ApiModelProperty(value = "使用场景")
        private String businessScene;
    }

    @Data
    @ApiModel("修改数据配置")
    public static class UpdateDto{

        @ApiModelProperty(value = "id")
        private Integer id;

        @ApiModelProperty(value = "数据层级 2:中间层 3:指标层")
        private Integer hierarchy;

        @ApiModelProperty(value = "名称")
        private String name;

        @ApiModelProperty(value = "code")
        private String code;

        @ApiModelProperty(value = "年份")
        private String year;

        @ApiModelProperty(value = "文字公式")
        private String formulaDescribe;

        @ApiModelProperty(value = "公式")
        private String formula;

        @ApiModelProperty(value = "单位")
        private String unit;

        @ApiModelProperty(value = "精度")
        private Integer accuracy;

        @ApiModelProperty(value = "使用场景")
        private String businessScene;
    }

    @Data
    @ApiModel("计算")
    public static class CalculationDto extends BasePage {

        @ApiModelProperty(value = "数据层级 2:中间层 3:指标层")
        private Integer hierarchy;

        @ApiModelProperty(value = "配置id")
        private String id;

        @ApiModelProperty(value = "年份（新增，编辑的时候传）")
        private String year;

        @ApiModelProperty(value = "公式（新增，编辑的时候传）")
        private String formula;

        @ApiModelProperty(value = "名称（新增，编辑的时候传）")
        private String formulaName;
    }
}
