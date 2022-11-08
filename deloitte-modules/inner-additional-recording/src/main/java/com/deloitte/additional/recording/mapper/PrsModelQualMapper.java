package com.deloitte.additional.recording.mapper;

import com.deloitte.additional.recording.vo.DataListPageTataiVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.PrsModelQual;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (PrsModelQual)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Mapper
public interface PrsModelQualMapper extends BaseMapper<PrsModelQual> {

    List<DataListPageTataiVo> queryByPageStatsdetail(@Param("modelCode") String modelCode, @Param("timeValue") String timeValue, @Param("name") String name);
}
