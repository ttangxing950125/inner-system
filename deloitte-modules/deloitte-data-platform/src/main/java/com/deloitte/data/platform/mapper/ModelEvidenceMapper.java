package com.deloitte.data.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.data.platform.domian.ModelEvidence;
import com.deloitte.data.platform.dto.ModelEvidenceDto;
import com.deloitte.data.platform.dto.TopDataMenuDto;
import com.deloitte.data.platform.dto.ParameterConfigDto;
import com.deloitte.data.platform.vo.ModelEvidenceVo;
import com.deloitte.data.platform.vo.ParameterConfigVo;
import com.deloitte.data.platform.vo.TopDataMenuVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Evidence模型配置
 *
 * @author XY
 * @date 2022/11/20
 */
public interface ModelEvidenceMapper extends BaseMapper<ModelEvidence> {

    IPage<ModelEvidenceVo.ListVo> getModelEvidenceListPage(IPage<ModelEvidenceVo.ListVo> page,@Param("dto") ModelEvidenceDto.ListDto dto);

    /**
     * 根据关键字搜索Evidence信息
     * @param dto
     * @return
     */
    List<TopDataMenuVo> getTopDataMenu(@Param("dto") TopDataMenuDto dto);
    /**
     * 参数配置列表
     * @param dto
     * @return
     */
    IPage<ParameterConfigVo.ListVo> getParameterConfigListPage(IPage<ParameterConfigVo.ListVo> page,@Param("dto") ParameterConfigDto.ListDto dto);
}
