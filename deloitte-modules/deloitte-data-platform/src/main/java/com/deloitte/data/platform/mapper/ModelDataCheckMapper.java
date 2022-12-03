package com.deloitte.data.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.data.platform.domian.ModelDataCheck;
import com.deloitte.data.platform.dto.ParameterConfigDto;
import com.deloitte.data.platform.vo.ParameterConfigVo;
import org.apache.ibatis.annotations.Param;

/**
 * 数据校验模型表  Mapper 接口
 *
 * @author fangliu
 * @date 2022/11/16 17:40:39
 */
public interface ModelDataCheckMapper extends BaseMapper<ModelDataCheck> {

    /**
     * 质检规则列表
     * @param dto
     * @return
     */
    IPage<ParameterConfigVo.ModelDataCheckListVo> getModelDataCheckListPage(IPage<ParameterConfigVo.ModelDataCheckListVo> page,@Param("dto") ParameterConfigDto.modelDataCheckListPageDto dto);
}
