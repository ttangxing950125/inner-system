package com.deloitte.additional.recording.mapper;

import com.deloitte.additional.recording.dto.PrsQualDataExcelDto;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.PrsQualData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (PrsQualData)表数据库访问层
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
@Mapper
public interface PrsQualDataMapper extends BaseMapper<PrsQualData> {

    /**
     * 查询指标任务导出导入相关数据
     * @param code 指标code
     * @return  List<PrsQualEfficiencyExcelDto>
     */
    List<PrsQualDataExcelDto> findExcelListByCode(@Param("code") String code);


    long countLose(@Param("qualCode") String qualCode, @Param("dataYear") String dataYear);
}
