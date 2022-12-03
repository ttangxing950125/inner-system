package com.deloitte.additional.recording.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.deloitte.additional.recording.dto.EntityInfoByFinDto;
import com.deloitte.additional.recording.dto.MainBodyPageDto;
import com.deloitte.additional.recording.dto.PrincipalManifestVo;
import com.deloitte.additional.recording.vo.EntityInfoByFinVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.EntityInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (EntityInfo)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Mapper
public interface EntityInfoMapper extends BaseMapper<EntityInfo> {

    IPage<EntityInfo> queryByPage(@Param("page") Page page, @Param("id") Integer id,@Param("entityName") String entityName,@Param("modelCode") String modelCode);

    List<PrincipalManifestVo> queryPrincipalManifestPage(Page<PrincipalManifestVo> page, @Param("mainBodyDto") MainBodyPageDto mainBodyDt);


}
