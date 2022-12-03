package com.deloitte.data.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.data.platform.domian.ModelEvidence;
import com.deloitte.data.platform.dto.ModelEvidenceDto;
import com.deloitte.data.platform.dto.ParameterConfigDto;
import com.deloitte.data.platform.dto.TopDataMenuDto;
import com.deloitte.data.platform.vo.DataPlatformMenuVo;
import com.deloitte.data.platform.vo.ModelEvidenceVo;
import com.deloitte.data.platform.vo.ParameterConfigVo;
import com.deloitte.data.platform.vo.TopDataMenuVo;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Evidence模型配置
 *
 * @author XY
 * @date 2022/11/20
 */
public interface IModelEvidenceService extends IService<ModelEvidence> {

    /**
     * 数据配置列表
     * @param dto
     * @return
     */
    IPage<ModelEvidenceVo.ListVo> getModelEvidenceListPage(ModelEvidenceDto.ListDto dto);

    /**
     * 财务配置列表
     * @param dto
     * @return
     */
    IPage<ModelEvidenceVo.FinancialDataConfigListVo> getFinancialDataConfigList(ModelEvidenceDto.FinancialDataConfigListDto dto);

    /**
     * 根据关键字查询Evidence模型配置
     * @param keyWord
     * @return
     */
    Map<String,ModelEvidence> getModelEvidenceMap(String keyWord);

    /**
     * 根据关键字查询EvidenceCode
     * @param keyWord
     * @return
     */
    Set<String> getModelEvidenceCode(String keyWord);

    /**
     * 删除数据配置
     * @param dto
     * @return
     */
    R deleteById(ModelEvidenceDto.DeleteDto dto);

    /**
     * 新增数据配置
     * @param dto
     * @return
     */
    R add(ModelEvidenceDto.AddDto dto);

    /**
     * 新增数据配置
     * @param dto
     * @return
     */
    R updateModelById(ModelEvidenceDto.UpdateDto dto);

    /**
     * 计算
     * @param dto
     * @return
     */
    IPage<ModelEvidenceVo.CalculationVo> calculation(ModelEvidenceDto.CalculationDto dto) throws ParseException;

    /**
     * 数据配置详情
     * @param dto
     * @return
     */
    ModelEvidenceVo.ListVo detail(ModelEvidenceDto.DeleteDto dto);

    /**
     * 参数配置列表
     * @param dto
     * @return
     */
    IPage<ParameterConfigVo.ListVo> getParameterConfigListPage(ParameterConfigDto.ListDto dto);

    /**
     * 根据关键字搜索Evidence信息
     * @param dto
     * @return
     */
    List<TopDataMenuVo> getTopDataMenu(TopDataMenuDto dto);

    /**
     * 修改参数配置(基础层)
     * @param dto
     * @return
     */
    R updateBase(ParameterConfigDto.UpdateBaseDto dto);

    /**
     * 修改参数配置(中间层)
     * @param dto
     * @return
     */
    R updateMiddle(ParameterConfigDto.UpdateMiddleDto dto);

    /**
     * 修改参数配置(指标层层)
     * @param dto
     * @return
     */
    R updateApply(ParameterConfigDto.UpdateApplyDto dto);

    /**
     * 修改参数配置(基础层)
     * @param dto
     * @return
     */
    R addOrUpdateBase(ParameterConfigDto.UpdateBaseDto dto);

    /**
     * 修改参数配置(中间层)
     * @param dto
     * @return
     */
    R addOrUpdateMiddle(ParameterConfigDto.UpdateMiddleDto dto);

    /**
     * 修改参数配置(指标层)
     * @param dto
     * @return
     */
    R addOrUpdateApply(ParameterConfigDto.UpdateApplyDto dto);

    /**
     * 质检规则列表
     * @param dto
     * @return
     */
    IPage<ParameterConfigVo.ModelDataCheckListVo> getModelDataCheckListPage(ParameterConfigDto.modelDataCheckListPageDto dto);

    /**
     * 修改/新增规则
     * @param dto
     * @return
     */
    R updateOrAdd(ParameterConfigDto.UpdateOrAddDto dto);

    /**
     * 校验规则
     * @param dto
     * @return
     */
    Boolean checkData(ParameterConfigDto.CheckDataDto dto);

    /**
     * 获取质检规则菜单
     * @param
     * @return
     */
    DataPlatformMenuVo getBaseConfigDictSubordinate();
}
