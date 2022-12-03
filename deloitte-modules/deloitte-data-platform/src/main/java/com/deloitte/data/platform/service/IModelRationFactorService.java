package com.deloitte.data.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.data.platform.domian.ModelRationFactor;
import com.deloitte.data.platform.dto.ApplyDataConfigDto;
import com.deloitte.data.platform.dto.ApplyQualityDto;
import com.deloitte.data.platform.dto.ModelEvidenceDto;
import com.deloitte.data.platform.dto.TopDataMenuDto;
import com.deloitte.data.platform.dto.ParameterConfigDto;
import com.deloitte.data.platform.vo.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 定量指标模型  服务类
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
public interface IModelRationFactorService extends IService<ModelRationFactor> {
    /**
     * 更具指标名称查询编码
     * @param factorName
     * @return
     */
    Set<String> getFactorCode(String factorName);
    /**
     * 指标层数据字典分页
     * @param dto
     * @return
     */
    IPage<ApplyDataConfigVo> getApplyDataConfigPage(ApplyDataConfigDto dto);

    /**
     * 指标层质检结果
     * @param dto
     * @return
     */
    IPage<ApplyQualityVo> getApplyQualityPage(ApplyQualityDto dto);

    /**
     * 根据指标编码查询指标名称
     * @param codes
     * @return
     */
    Map<String, ApplyDataExtractionVo>  getApplyDataExtractionMap(Set<String> codes);

    /**
     * 数据配置列表
     * @param dto page
     * @return
     */
    IPage<ModelEvidenceVo.ListVo> getModelRationListPage(IPage<ModelEvidenceVo.ListVo> page, ModelEvidenceDto.ListDto dto);

    /**
     * 删除数据配置列表
     * @param id
     * @return i
     */
    int deleteById(Integer id);

    /**
     * 删除数据配置列表
     * @param modelRationFactor
     * @return i
     */
    int insert(ModelRationFactor modelRationFactor);

    /**
     * 根据关键字搜索指标信息
     * @param dto
     * @return
     */
    List<TopDataMenuVo> getTopDataMenu(TopDataMenuDto dto);

    /**
     * 参数配置列表
     * @param dto
     * @return
     */
    IPage<ParameterConfigVo.ListVo> getParameterConfigListPage(IPage<ParameterConfigVo.ListVo> page, ParameterConfigDto.ListDto dto);

    /**
     * 修改参数配置
     * @param dto
     * @return
     */
    R updateModelRation(ParameterConfigDto.UpdateApplyDto dto);

    /**
     * 新增参数配置
     * @param dto
     * @return
     */
    R addModelRation(ParameterConfigDto.UpdateApplyDto dto);

    /**
     * 数据配置列表
     * @param dto
     * @return
     */
    IPage<ModelEvidenceVo.ListVo> getModelEvidenceListPage(IPage<ModelEvidenceVo.ListVo> page, ModelEvidenceDto.ListDto dto);
}
