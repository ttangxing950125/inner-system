package com.deloitte.additional.recording.mapper;

import com.deloitte.additional.recording.domain.BasEvdInfo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.PrsModelQualEvd;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (PrsModelQualEvd)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Mapper
public interface PrsModelQualEvdMapper extends BaseMapper<PrsModelQualEvd> {

    /**
     * 查询缺少的evd
     * @param verMasId
     * @param entityCode
     * @param taskYear
     * @author wpp
     * @return
     */
    List<BasEvdInfo> findLackEvd(@Param("verMasId") Integer verMasId,
                                 @Param("entityCode") String entityCode,
                                 @Param("taskYear") Integer taskYear);
}
