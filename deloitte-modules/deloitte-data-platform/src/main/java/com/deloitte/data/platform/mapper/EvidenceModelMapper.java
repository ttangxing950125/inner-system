package com.deloitte.data.platform.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.data.platform.domian.EvidenceModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.data.platform.dto.MiddleDataConfigDto;
import com.deloitte.data.platform.dto.MiddleQualityDto;
import com.deloitte.data.platform.vo.MiddleDataConfigVo;
import com.deloitte.data.platform.vo.MiddleQualityVo;
import org.apache.ibatis.annotations.Param;

/**
 * Evidence模型配置  Mapper 接口
 *
 * @author fangliu
 * @date 2022/11/10 11:40:39
 */
public interface EvidenceModelMapper extends BaseMapper<EvidenceModel> {
    /**
     * 中间层数据字典分页
     * @param page
     * @param dto
     * @return
     */
    IPage<MiddleDataConfigVo> getMiddleDataConfigPage(IPage<MiddleDataConfigVo> page, @Param("dto") MiddleDataConfigDto dto);

    /**
     * 中间层质检结果
     * @param page
     * @param dto
     * @return
     */
    IPage<MiddleQualityVo> getMiddleQualityPage(IPage<MiddleQualityVo> page, @Param("dto") MiddleQualityDto dto);
}
