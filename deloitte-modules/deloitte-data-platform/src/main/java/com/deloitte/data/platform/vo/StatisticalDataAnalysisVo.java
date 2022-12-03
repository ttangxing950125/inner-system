package com.deloitte.data.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 数据统计分析
 *
 * @author zhengyingxiang
 * @date 2022/11/11 13:48:35
 */
public class StatisticalDataAnalysisVo {

    @Data
    @ApiModel("总览")
    public static class OverviewVo{

        @ApiModelProperty(value = "主体名称")
        private String entityName;

        @ApiModelProperty(value = "主体code")
        private String entityCode;

        @ApiModelProperty(value = "字段代码")
        private String code;

        @ApiModelProperty(value = "字段中文名称")
        private String name;

        @ApiModelProperty(value = "1:基础层 2:中间层 3:指标层")
        private Integer hierarchy;

        @ApiModelProperty(value = "数据时间")
        private String reportDate;

        @ApiModelProperty(value = "精度")
        private double accuracy;

        @ApiModelProperty(value = "数据优先级")
        private String dataPriority;

        @ApiModelProperty(value = "使用场景")
        private String useScenarios;

        @ApiModelProperty(value = "推荐数据")
        private String suggestValue;

        @ApiModelProperty(value = "推荐数据缺失率")
        private String suggestMissRate;

        @ApiModelProperty(value = "是否人工补录  0 否，1 是")
        private String isArtificialRecording;

        @ApiModelProperty(value = "人工补录数据")
        private String artificialRecordingData;

        @ApiModelProperty(value = "自动化补录数据")
        private String ocrValue;

        @ApiModelProperty(value = "wind")
        private String windValue;

        @ApiModelProperty(value = "同花顺")
        private String flushValue;

    }

    @Data
    @ApiModel("总览-数据来源覆盖度")
    public static class CoverageVo{

        @ApiModelProperty(value = "wind",hidden = true)
        private String windRate;

        @ApiModelProperty(value = "同花顺",hidden = true)
        private String flushRate;

        @ApiModelProperty(value = "ocr自动化",hidden = true)
        private String ocrRate;

        @ApiModelProperty(value = "人工补录",hidden = true)
        private String artificialAddRecordRate;

        @ApiModelProperty(value = "标题")
        private List<String> title;

        @ApiModelProperty(value = "值")
        private List<String> value;

    }

    @Data
    @ApiModel("业务场景")
    public static class BusinessScenarioVo{

        @ApiModelProperty(value = "主体名称")
        private String entityName;

        @ApiModelProperty(value = "主体code")
        private String entityCode;

        @ApiModelProperty(value = "字段代码")
        private String code;

        @ApiModelProperty(value = "字段中文名称")
        private String name;

        @ApiModelProperty(value = "1:基础层 2:中间层 3:指标层")
        private Integer hierarchy;

        @ApiModelProperty(value = "数据时间")
        private String reportDate;

        @ApiModelProperty(value = "精度")
        private double accuracy;

        @ApiModelProperty(value = "数据优先级")
        private String dataPriority;

        @ApiModelProperty(value = "使用场景")
        private String useScenarios;

        @ApiModelProperty(value = "推荐数据")
        private String suggestValue;

        @ApiModelProperty(value = "推荐数据缺失率")
        private String suggestMissRate;

        @ApiModelProperty(value = "是否人工补录  0 否，1 是")
        private String isArtificialRecording;

        @ApiModelProperty(value = "人工补录数据")
        private String artificialRecordingData;

        @ApiModelProperty(value = "自动化补录数据")
        private String ocrValue;

    }

    @Data
    @ApiModel("业务场景-客户列表")
    public static class CustomerListVo{

        @ApiModelProperty(value = "主体名称")
        private String entityName;

        @ApiModelProperty(value = "主体code")
        private String entityCode;

    }

    @Data
    @ApiModel("业务场景-缺失率统计")
    public static class BusinessScenarioMissRateVo{

        @ApiModelProperty(value = "缺失率0%",hidden = true)
        private String MissRate1;

        @ApiModelProperty(value = "缺失率0%-30%",hidden = true)
        private String MissRate2;

        @ApiModelProperty(value = "缺失率30%-60%",hidden = true)
        private String MissRate3;

        @ApiModelProperty(value = "缺失率60%-90%",hidden = true)
        private String MissRate4;

        @ApiModelProperty(value = "缺失率90%-100%",hidden = true)
        private String MissRate5;

        @ApiModelProperty(value = "缺失率100%",hidden = true)
        private String MissRate6;

        @ApiModelProperty(value = "标题")
        private List<String> title;

        @ApiModelProperty(value = "值")
        private List<String> value;

    }

    @Data
    @ApiModel("业务场景-数据缺失率总占比统计")
    public static class BusinessScenarioMissRateAllVo{

        @ApiModelProperty(value = "数据缺失")
        private Double dataMissRate;

        @ApiModelProperty(value = "未通过质检")
        private Double noArtificialInspection;

    }

    @Data
    @ApiModel("补录总占比统计")
    public static class RecordingAllVo{

        @ApiModelProperty(value = "补录中")
        private Double recordingIng;

        @ApiModelProperty(value = "已人工补录")
        private Double alredyRecording;

    }

    @Data
    @ApiModel("使用场景")
    public static class GetDataMenuVo{

        @ApiModelProperty(value = "代码")
        private String code;

        @ApiModelProperty(value = "名称")
        private String name;

    }

    @Data
    @ApiModel("人工补录字段情况-条形图表")
    public static class ArtificialRecordingBarVo{

        @ApiModelProperty(value = "标题")
        private List<String> title;

        @ApiModelProperty(value = "已人工补录")
        private List<String> alreadyArtificialRecordinge;

        @ApiModelProperty(value = "未人工补录")
        private List<String> noArtificialRecordinge;

    }

    @Data
    @ApiModel("人工补录字段情况-圆形图表")
    public static class ArtificialRecordingCircularVo{

        @ApiModelProperty(value = "自动化")
        private Double ocr;

        @ApiModelProperty(value = "人工补录")
        private Double artificialRecordIng;

        @ApiModelProperty(value = "已人工补录")
        private Double alredyRecording;

        @ApiModelProperty(value = "已自动化填充")
        private Double ocrIng;

        @ApiModelProperty(value = "人工补录中")
        private Double recordingIng;

        @ApiModelProperty(value = "自动化校验未通过")
        private Double dataCheckFailed;

    }

}
