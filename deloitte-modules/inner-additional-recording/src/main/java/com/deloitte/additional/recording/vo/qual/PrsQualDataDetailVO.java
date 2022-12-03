package com.deloitte.additional.recording.vo.qual;

import com.deloitte.additional.recording.vo.evd.BasEvdInfoDetailVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @创建人 tangx
 * @创建时间 2022/12/2
 * @描述 应用层管理-指标列表-详情响应体
 */
@Data
@ApiModel("应用层管理-指标列表-详情")
public class PrsQualDataDetailVO {

    @ApiModelProperty("指标code")
    private String qualCode;
    @ApiModelProperty("指标名称")
    private String qualName;
    @ApiModelProperty("最后一次更新时间")
    private Date updated;
    @ApiModelProperty("evd列表")
    private List<BasEvdInfoDetailVO> basEvdInfoDetailVOS;

}
