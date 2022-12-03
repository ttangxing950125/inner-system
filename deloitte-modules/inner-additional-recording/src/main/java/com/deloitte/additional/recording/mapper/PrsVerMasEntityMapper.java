package com.deloitte.additional.recording.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.deloitte.additional.recording.domain.SynTable;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.PrsVerMasEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (PrsVerMasEntity)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:40
 */
@Mapper
public interface PrsVerMasEntityMapper extends BaseMapper<PrsVerMasEntity> {

    /**
     * 补录平台的id
     */
    Integer META_Id = 8;

    /**
     * 查询crm的敞口映射
     * @param projectName
     * @param productsId
     * @param versionId
     * @author wpp
     * @return
     */
    @DS("crm")
    List<PrsVerMasEntity> findCrmEntityRel(@Param("projectName") String projectName,
                                           @Param("productsId") Integer productsId,
                                           @Param("versionId") Integer versionId,
                                           @Param("synTable") SynTable synTable);

    /**
     * 根据条件查询版本敞口主体关系
     * @param versionId
     * @param modelCode
     * @param entityCode
     * @author wpp
     * @return
     */
    PrsVerMasEntity findByVersionMasterEntity(@Param("versionId") Integer versionId,
                                              @Param("modelCode") String modelCode,
                                              @Param("entityCode") String entityCode);
}
