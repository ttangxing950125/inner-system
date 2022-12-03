package com.deloitte.data.platform.dto;

import com.baomidou.mybatisplus.core.injector.methods.Insert;
import com.deloitte.data.platform.common.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * 数据统计分析
 *
 * @author zhengyingxiang
 * @date 2022/11/11 13:48:35
 */
public abstract class StatisticalDataAnalysisDto {

    @Data
    @ApiModel("总览")
    public static class OverviewDto extends BasePage{

        @ApiModelProperty(value = "搜索关键字",required = false)
        private String searchName;

        @ApiModelProperty(value = "年份")
        private List<String> years;

        @ApiModelProperty(value = "数据层级 1:基础层 2:中间层 3:指标层")
        private Integer hierarchy;

        @ApiModelProperty(value = "数据来源（基础层）1:wind数据库，3:wind客户端 4:同花顺，5:OCR ")
        private List<String> sources;

        @ApiModelProperty(value = "使用场景")
        private String businessScene;

        @ApiModelProperty(value = "客户/单个主体")
        private String entityCode;

        @ApiModelProperty(value = "主体类型")
        private String entityType;

        @ApiModelProperty(value = "单个字段")
        private String code;

        @ApiModelProperty(value = "数据来源图表 1:全部数据 2:推荐数据")
        private Integer coverageType;

    }

    @Data
    @ApiModel("业务场景")
    public static class BusinessScenarioDto extends BasePage{

        @ApiModelProperty(value = "搜索关键字")
        private String searchName;

        @ApiModelProperty(value = "年份")
        private List<String> years;

        @ApiModelProperty(value = "数据层级 1:基础层 2:中间层 3:指标层")
        private Integer hierarchy;

        @ApiModelProperty(value = "客户")
        private String entityCode;

        @ApiModelProperty(value = "主体类型")
        private String entityType;

        @ApiModelProperty(value = "数据来源图表 1:全部数据 2:推荐数据")
        private Integer coverageType;

    }

    @Data
    @ApiModel("业务场景-客户列表")
    public static class CustomerListDto extends BasePage{

        @ApiModelProperty(value = "搜索名字")
        private String searchName;
    }

    @Data
    @ApiModel("单个主体")
    public static class SingleEntityDto extends BasePage{

        @ApiModelProperty(value = "搜索关键字")
        private String searchName;

        @ApiModelProperty(value = "年份")
        private List<String> years;

        @ApiModelProperty(value = "数据层级 1:基础层 2:中间层 3:指标层")
        private Integer hierarchy;

        @ApiModelProperty(value = "使用场景")
        private String businessScene;

    }

    @Data
    @ApiModel("使用场景")
    public static class GetDataMenuDto{

        @ApiModelProperty(value = "数据层级 1:基础层 2:中间层 3:指标层")
        private Integer hierarchy;

    }

}
