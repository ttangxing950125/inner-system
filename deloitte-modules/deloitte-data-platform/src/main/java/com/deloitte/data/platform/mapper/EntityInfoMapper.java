package com.deloitte.data.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.data.platform.domian.EntityInfo;
import com.deloitte.data.platform.dto.EntityInfoDto;
import com.deloitte.data.platform.dto.StatisticalDataAnalysisDto;
import com.deloitte.data.platform.dto.TopDataMenuDto;
import com.deloitte.data.platform.vo.EntityInfoVo;
import com.deloitte.data.platform.vo.StatisticalDataAnalysisVo;
import com.deloitte.data.platform.vo.TopDataMenuVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author fangliu
 * @date 2022/11/14 11:40:39
 */
public interface EntityInfoMapper extends BaseMapper<EntityInfo> {
    /**
     * 分页查询数据质检信息
     * @param page
     * @param dto
     * @return
     */
    IPage<EntityInfoVo> getEntityInfoPage(IPage<EntityInfoVo> page, @Param("dto") EntityInfoDto dto);

    /**
     * 分页查询客户列表
     * @param page
     * @param dto
     * @return
     */
    IPage<StatisticalDataAnalysisVo.CustomerListVo> getCustomerPage(IPage<StatisticalDataAnalysisVo.CustomerListVo> page,@Param("dto") StatisticalDataAnalysisDto.CustomerListDto dto);

    /**
     * 根据关键字搜索主体信息
     * @param dto
     * @return
     */
    List<TopDataMenuVo> getTopDataMenu( @Param("dto") TopDataMenuDto dto);
}
