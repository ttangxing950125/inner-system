package com.deloitte.data.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.data.platform.domian.DataCheckResultItem;
import com.deloitte.data.platform.dto.HookArticulationDetailDto;
import org.apache.ibatis.annotations.Param;

/**
 * 数据校验结果表  Mapper 接口
 *
 * @author fangliu
 * @date 2022/11/16 17:40:39
 */
public interface DataCheckResultItemMapper extends BaseMapper<DataCheckResultItem> {
    /**
     * 数据校验结果 分页查询
     * @param page
     * @param dto
     * @return
     */
    IPage<DataCheckResultItem> getDataCheckResultItemPage(IPage<DataCheckResultItem> page , @Param("dto") HookArticulationDetailDto dto);
}
