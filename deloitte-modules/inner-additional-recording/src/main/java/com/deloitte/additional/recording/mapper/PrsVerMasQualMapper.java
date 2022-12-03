package com.deloitte.additional.recording.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.PrsVerMasQual;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (PrsVerMasQual)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:40
 */
@Mapper
public interface PrsVerMasQualMapper extends BaseMapper<PrsVerMasQual> {

    /**
     * 查询当前主体缺少的指标
     * @param id
     * @param entityCode
     * @author wpp
     * @return
     */
    List<PrsVerMasQual> findLackQual(@Param("verMasId") Integer id,
                                     @Param("entityCode") String entityCode,
                                     @Param("timeValue") String timeValue);
}
