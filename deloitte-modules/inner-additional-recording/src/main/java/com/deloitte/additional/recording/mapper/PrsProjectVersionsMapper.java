package com.deloitte.additional.recording.mapper;

import com.deloitte.additional.recording.vo.DataListFindPrsProjectVersionsByYearVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.PrsProjectVersions;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (PrsProjectVersions)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Mapper
public interface PrsProjectVersionsMapper extends BaseMapper<PrsProjectVersions> {

    List<DataListFindPrsProjectVersionsByYearVo> finPrsProjectVersionsByYear(Integer[]  year);
}
