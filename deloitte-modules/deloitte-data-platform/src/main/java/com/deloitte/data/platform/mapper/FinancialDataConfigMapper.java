package com.deloitte.data.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.data.platform.domian.FinancialDataConfig;
import com.deloitte.data.platform.dto.BaseDataConfigDto;
import com.deloitte.data.platform.dto.ModelEvidenceDto;
import com.deloitte.data.platform.vo.BaseDataConfigVo;
import com.deloitte.data.platform.vo.ModelEvidenceVo;
import org.apache.ibatis.annotations.Param;

/**
 * 财务数据配置  Mapper 接口
 *
 * @author fangliu
 * @date 2022/11/10 17:40:39
 */
public interface FinancialDataConfigMapper extends BaseMapper<FinancialDataConfig> {
    /**
     * 基础字段数据字典分页信息
     * @param page
     * @param dto
     * @return
     */
    IPage<BaseDataConfigVo> getFinancialBaseDataConfigPage(IPage<BaseDataConfigVo> page,@Param("dto") BaseDataConfigDto dto);

    /**
     * 财务数据配置列表
     * @param page
     * @param dto
     * @return
     */
    IPage<ModelEvidenceVo.FinancialDataConfigListVo> getFinancialDataConfigList(IPage<ModelEvidenceVo.FinancialDataConfigListVo> page,@Param("dto") ModelEvidenceDto.FinancialDataConfigListDto dto);
}
