package com.deloitte.data.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 数据配置
 *
 * @author zhengyingxiang
 * @date 2022/11/20
 */
public class ModelEvidenceVo {

    @Data
    @ApiModel("数据配置列表")
    public static class ListVo {

        @ApiModelProperty(value = "id")
        private Integer id;

        @ApiModelProperty(value = "名称")
        private String name;

        @ApiModelProperty(value = "code")
        private String code;

        @ApiModelProperty(value = "数据时间")
        private String reportDate;

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

        @ApiModelProperty(value = "异常值")
        private String abnormalValueHandle;

        @ApiModelProperty(value = "异常值处理(异常列表)")
        private List<ParameterConfigVo.ListVo.AbnormalValueHandleSelectsVo> abnormalValueHandleList;

    }

    @Data
    @ApiModel("财务数据配置列表")
    public static class FinancialDataConfigListVo {

        @ApiModelProperty(value = "id")
        private Integer id;

        @ApiModelProperty(value = "名称")
        private String name;

        @ApiModelProperty(value = "code")
        private String code;

    }

    @Data
    @ApiModel("计算")
    public static class CalculationVo {

        @ApiModelProperty(value = "code")
        private String code;

        @ApiModelProperty(value = "主体代码")
        private String entityCode;

        @ApiModelProperty(value = "中文名称")
        private String formulaDescribe;

        @ApiModelProperty(value = "数据时间")
        private String reportDate;

        @ApiModelProperty(value = "数据列表")
        private List<OtherFieldVo> fields;

        @Data
        @ApiModel("其它字段")
        public static class OtherFieldVo {

            @ApiModelProperty(value = "字段名")
            private String fieldName;

            @ApiModelProperty(value = "字段Code")
            private String fieldCode;

            @ApiModelProperty(value = "字段值")
            private String fieldValue;
        }

    }

    @Data
    @ApiModel("计算-分页")
    public static class CalculationPageVo {

        @ApiModelProperty(value = "总条数")
        private Integer total;

        @ApiModelProperty(value = "总页数")
        private Integer pages;

        @ApiModelProperty(value = "数据列表1")
        private List<CalculationVo> fields;

        @ApiModelProperty(value = "数据列表2")
        private List<CalculationVo.OtherFieldVo> fields2;

    }

    @Data
    @ApiModel("接收查询结果")
    public static class FinDataVo {

        @ApiModelProperty(value = "code")
        private String code;

        @ApiModelProperty(value = "主体代码")
        private String entityCode;

        @ApiModelProperty(value = "值")
        private String suggestValue;

        @ApiModelProperty(value = "数据时间")
        private String reportDate;

        @ApiModelProperty(value = "类型 lag，sag")
        private String type;
    }

}
