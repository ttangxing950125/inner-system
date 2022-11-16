package com.deloitte.additional.recording.mapper;

import com.deloitte.additional.recording.dto.TranspreQualinfo3rdDto;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.TranspreQualinfo3rd;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TranspreQualinfo3rd)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:41
 */
@Mapper
public interface TranspreQualinfo3rdMapper extends BaseMapper<TranspreQualinfo3rd> {

    List<TranspreQualinfo3rdDto> page(@Param("useYear") String useYear, @Param("tarType") String tarType, @Param("masterId")Integer masterId, @Param("versionId")Integer versionId, @Param("searchData")String searchData, @Param("page")Integer page, @Param("pageSize") Integer pageSize);

    long pageCount(@Param("useYear") String useYear,@Param("tarType") String tarType, @Param("masterId")Integer masterId, @Param("versionId")Integer versionId, @Param("searchData")String searchData, @Param("page")Integer page,@Param("pageSize") Integer pageSize);
}
