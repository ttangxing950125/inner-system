package com.deloitte.data.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.data.platform.domian.DataCheckResult;
import com.deloitte.data.platform.dto.HookArticulationDto;
import com.deloitte.data.platform.vo.HookArticulationVo;
import org.apache.ibatis.annotations.Param;

/**
 * 数据校验结果表  Mapper 接口
 *
 * @author fangliu
 * @date 2022/11/16 17:40:39
 */
public interface DataCheckResultMapper extends BaseMapper<DataCheckResult> {
    /**
     * 统计分析 勾稽关系分页查询
     * @param page
     * @param dto
     * @return
     */
    IPage<HookArticulationVo> getHookArticulationPage(IPage<HookArticulationVo> page , @Param("dto") HookArticulationDto dto);
}
