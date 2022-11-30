package com.deloitte.additional.recording.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.additional.recording.domain.PrsQualEfficiency;
import com.deloitte.additional.recording.vo.qual.PrsQualEfficiencyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @创建人 tangx
 * @创建时间 2022/11/24
 * @描述 PrsQualEfficiency 数据层
 */
public interface PrsQualEfficiencyMapper extends BaseMapper<PrsQualEfficiency> {

    /**
     * 分页查询
     *
     * @param versionId 版本id
     * @param modelCode 敞口
     * @param riskLevel 风险等级
     * @param userYear  年份
     * @param boo
     * @param userId
     * @param page      当前页码
     * @param pageSize  当前页面数据大小
     * @return
     */
    List<PrsQualEfficiencyVO> page(@Param("versionId") Integer versionId, @Param("modelCode")String modelCode , @Param("searchKey")String searchKey, @Param("riskLevel")Integer riskLevel, @Param("userYear")String userYear, @Param("boo") boolean boo, @Param("userId") String userId, @Param("page")Integer page, @Param("pageSize")Integer pageSize);

   long pageCount(@Param("versionId") Integer versionId, @Param("modelCode")String modelCode ,@Param("searchKey")String searchKey,@Param("riskLevel")Integer riskLevel, @Param("userYear")String userYear,@Param("boo") boolean boo, @Param("userId") String userId);

    /**
     * 根据选择的指标查询
     *
     * @param codes  指标codes
     * @param boo
     * @param userId
     * @return List<PrsQualEfficiencyVO>
     */
    List<PrsQualEfficiencyVO> pageByCodes(@Param("codes") String[] codes, @Param("searchKey")String searchKey, @Param("riskLevel")Integer riskLevel, @Param("boo") boolean boo, @Param("userId") String userId, @Param("page")Integer page, @Param("pageSize")Integer pageSize);


    long countByCodes(@Param("codes") String[] codes,@Param("searchKey")String searchKey,@Param("riskLevel")Integer riskLevel,@Param("boo") boolean boo,@Param("userId") String userId);
}
