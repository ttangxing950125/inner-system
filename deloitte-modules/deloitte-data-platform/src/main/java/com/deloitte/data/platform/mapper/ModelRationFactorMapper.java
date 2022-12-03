package com.deloitte.data.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.data.platform.domian.ModelRationFactor;
import com.deloitte.data.platform.dto.ApplyDataConfigDto;
import com.deloitte.data.platform.dto.ApplyQualityDto;
import com.deloitte.data.platform.dto.ModelEvidenceDto;
import com.deloitte.data.platform.dto.TopDataMenuDto;
import com.deloitte.data.platform.dto.ParameterConfigDto;
import com.deloitte.data.platform.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 定量指标模型  Mapper 接口
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
public interface ModelRationFactorMapper extends BaseMapper<ModelRationFactor> {
    /**
     * 更具指标名称查询编码
     * @param factorName
     * @return
     */
    Set<String> getFactorCode(String factorName);
    /**
     * 指标层数据字典分页
     * @param page
     * @param dto
     * @return
     */
    IPage<ApplyDataConfigVo> getApplyDataConfigPage(IPage<ApplyDataConfigVo> page, @Param("dto") ApplyDataConfigDto dto);

    /**
     * 指标层质检结果
     * @param page
     * @param dto
     * @return
     */
    IPage<ApplyQualityVo> getApplyQualityPage(IPage<ApplyQualityVo> page,@Param("dto") ApplyQualityDto dto);

    /**
     * 数据配置列表
     * @param page
     * @param dto
     * @return
     */
    IPage<ModelEvidenceVo.ListVo> getModelRationListPage(IPage<ModelEvidenceVo.ListVo> page, @Param("dto") ModelEvidenceDto.ListDto dto);

    /**
     * 根据关键字搜索指标信息
     * @param dto
     * @return
     */
    List<TopDataMenuVo> getTopDataMenu(@Param("dto") TopDataMenuDto dto);

    /**
     * 根据指标编码查询指标名称
     * @param factorCodes
     * @return
     */
    List<ApplyDataExtractionVo> getApplyDataExtractions(@Param("factorCodes") Set<String> factorCodes);

    /**
     * 参数配置列表
     * @param page
     * @param dto
     * @return
     */
    IPage<ParameterConfigVo.ListVo> getParameterConfigListPage(IPage<ParameterConfigVo.ListVo> page,@Param("dto") ParameterConfigDto.ListDto dto);

    /**
     * 数据配置列表
     * @param dto
     * @return
     */
    IPage<ModelEvidenceVo.ListVo> getModelEvidenceListPage(IPage<ModelEvidenceVo.ListVo> page,@Param("dto") ModelEvidenceDto.ListDto dto);
}
