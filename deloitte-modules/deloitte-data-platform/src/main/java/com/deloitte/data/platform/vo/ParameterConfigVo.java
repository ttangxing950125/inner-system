package com.deloitte.data.platform.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 参数配置
 *
 * @author zhengyingxiang
 * @date 2022/11/21
 */
public class ParameterConfigVo {

    @Data
    @ApiModel("参数配置列表")
    public static class ListVo {

        @ApiModelProperty(value = "id")
        private Integer id;

        @ApiModelProperty(value = "字段名称")
        private String name;

        @ApiModelProperty(value = "字段代码")
        private String code;

        @ApiModelProperty(value = "数据来源")
        private String dataSource;

        @ApiModelProperty(value = "公式")
        private String formula;

        @ApiModelProperty(value = "公式描述")
        private String formulaDescribe;

        @ApiModelProperty(value = "变动率上限")
        private BigDecimal changeRateUpper;

        @ApiModelProperty(value = "值阈")
        private String thresholdValue;

        @ApiModelProperty(value = "精度")
        private Integer accuracy;

        @ApiModelProperty(value = "人工补录优先级")
        private String artificialRecordingSeq;

        @ApiModelProperty(value = "自动化补录优先级")
        private String ocrSeq;

        @ApiModelProperty(value = "wind优先级")
        private String windSeq;

        @ApiModelProperty(value = "同花顺优先级")
        private String flushSeq;

        @ApiModelProperty(value = "使用场景")
        private String businessScene;

        @ApiModelProperty(value = "异常值处理(Json字符串)")
        private String abnormalValueHandle;

        @ApiModelProperty(value = "异常值处理(下拉列表)")
        private List<AbnormalValueHandleSelectsVo> abnormalValueHandleSelects;

        @ApiModelProperty(value = "异常值处理(异常列表)")
        private List<AbnormalValueHandleSelectsVo> abnormalValueHandleList;

        @Data
        @ApiModel("异常值处理(下拉列表)")
        public static class AbnormalValueHandleSelectsVo {

            @ApiModelProperty(value = "code")
            private String code;

            @ApiModelProperty(value = "名称")
            private String name;

            @ApiModelProperty(value = "符号")
            private String symbol;

            @ApiModelProperty(value = "值")
            private String value;

        }

    }

    @Data
    @ApiModel("质检规则列表")
    public static class ModelDataCheckListVo {

        @ApiModelProperty(value = "id")
        private Integer id;

        @ApiModelProperty(value = "公式")
        private String checkFormula;

        @ApiModelProperty(value = "描述")
        private String checkDescribe;

    }




}
