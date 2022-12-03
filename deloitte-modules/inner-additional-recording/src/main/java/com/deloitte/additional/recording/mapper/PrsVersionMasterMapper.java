package com.deloitte.additional.recording.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.additional.recording.domain.PrsModelMaster;
import com.deloitte.additional.recording.domain.PrsVersionMaster;
import com.deloitte.additional.recording.vo.VersionMasterEvdBackVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (PrsVersionMaster)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:40
 */
@Mapper
public interface PrsVersionMasterMapper extends BaseMapper<PrsVersionMaster> {

    Page<PrsModelMaster> selectMasterByPage(Page<PrsModelMaster> pageInfo, @Param("versionId") Integer versionId, @Param("searchData") String searchData, @Param("dataYear") String dataYear);

    Page<VersionMasterEvdBackVo> getVersionEvdByMasters(Page<VersionMasterEvdBackVo> pageInfo, @Param("prjId") Integer prjId, @Param("modelCodes")List<String> modelCodes);
}
