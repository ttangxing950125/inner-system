package com.deloitte.additional.recording.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.PrsModelMaster;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (PrsModelMaster)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Mapper
public interface PrsModelMasterMapper extends BaseMapper<PrsModelMaster> {

    /**
     * 查询版本下所有的敞口
     * @param versionId 版本id
     * @return List<PrsModelMaster>
     */
    List<PrsModelMaster> listByVersion(@Param("versionId") Integer versionId);
}
