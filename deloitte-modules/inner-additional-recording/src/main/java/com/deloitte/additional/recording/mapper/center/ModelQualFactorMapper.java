package com.deloitte.additional.recording.mapper.center;

import com.deloitte.additional.recording.dto.ModerQuanAndQualFactorDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @创建人 tangx
 * @创建时间 2022/11/17
 * @描述
 */
@Mapper
public interface ModelQualFactorMapper {

    /**
     * 查定性
     * @param prefix 表前缀
     * @param modelCode 敞口code
     * @return
     */
    public List<ModerQuanAndQualFactorDto> findQualByPre(@Param("prefix") String prefix, @Param("modelCode") String modelCode );


    /**
     * 查询定量
     * @param prefix 表前缀
     * @param modelCode 敞口code
     * @return
     */
    public List<ModerQuanAndQualFactorDto> findQuanByPre(@Param("prefix") String prefix, @Param("modelCode") String modelCode );

    public List<ModerQuanAndQualFactorDto> findGovByPre(@Param("prefix") String prefix, @Param("modelCode") String modelCode);

    String getQualByPre(@Param("prefix") String prefix, @Param("id") String id);

    String getQuanByPre(@Param("prefix") String prefix, @Param("id") String id);

    String getGovQualByPre(@Param("prefix") String prefix, @Param("id") String id);
}
