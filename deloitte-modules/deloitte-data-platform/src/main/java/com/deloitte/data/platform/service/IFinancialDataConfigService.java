package com.deloitte.data.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.common.core.domain.R;
import com.deloitte.data.platform.domian.FinancialDataConfig;
import com.deloitte.data.platform.dto.BaseDataConfigDto;
import com.deloitte.data.platform.dto.ModelEvidenceDto;
import com.deloitte.data.platform.dto.ParameterConfigDto;
import com.deloitte.data.platform.vo.BaseDataConfigVo;
import com.deloitte.data.platform.vo.ModelEvidenceVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.Set;

/**
 * 财务数据配置  服务类
 *
 * @author fangliu
 * @date 2022/11/10 17:40:39
 */
public interface IFinancialDataConfigService extends IService<FinancialDataConfig> {
    /**
     * 基础字段数据字典分页信息
     * @param dto
     * @return
     */
    IPage<BaseDataConfigVo> getFinancialBaseDataConfigPage(BaseDataConfigDto dto);

    /**
     * 根据codes查询财务数据配置
     * @param codes
     * @return
     */
    Map<String,FinancialDataConfig> getFinancialDataConfig(Set<String> codes);

    /**
     * 根据关键查询财务数据配置code
     * @param keyWord
     * @return
     */
    Set<String> getFinancialDataConfigCode(String keyWord);

    /**
     * 修改配置
     * @param
     * @return
     */
    R updateBase(ParameterConfigDto.UpdateBaseDto dto);

    /**
     * 新增配置
     * @param
     * @return
     */
    R addBase(ParameterConfigDto.UpdateBaseDto dto);

    /**
     * 财务数据配置列表
     * @param
     * @return
     */
    IPage<ModelEvidenceVo.FinancialDataConfigListVo> getFinancialDataConfigList(IPage<ModelEvidenceVo.FinancialDataConfigListVo> page,ModelEvidenceDto.FinancialDataConfigListDto dto);
}
