package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.PrsQualData;
import com.deloitte.additional.recording.dto.PrsQualDataExcelDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * (PrsQualData)表服务接口
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:39
 */
public interface PrsQualDataService extends IService<PrsQualData> {

    /**
     * 导入指标数据
     *
     * @param serviceFile 文件
     */
    void importQualFromExcel(MultipartFile serviceFile);

    /**
     * 根据实体code 指标code 年份查询
     *
     * @param entityCode 实体code
     * @param qualCode   指标code
     * @param timeValue  年份
     * @return
     */
    PrsQualData getByEntityAndQcodeAndTime(String entityCode, String qualCode, String timeValue);

    List<PrsQualDataExcelDto> findExcelListByCode(String code);

    /**
     * 根据指标和年份统计主体总数
     * @param qualCode 指标code
     * @param dataYear 年份
     * @return Integer
     */
    long countEntity(String qualCode, String dataYear);

    /**
     * 统计不同挡位的主体数量
     *
     * @param value 挡位
     * @param dataYear
     * @param value
     * @return
     */
    long countByValue(String qualCode, String dataYear, String value);

    /**
     * 统计缺失
     * @param qualCode 指标code
     * @param dataYear 年份
     * @return long
     */
    long countLose(String qualCode, String dataYear);
}
