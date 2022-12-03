package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.PrsQualData;
import com.deloitte.additional.recording.domain.PrsVersionMaster;
import com.deloitte.additional.recording.dto.PrsQualDataExcelDto;
import com.deloitte.additional.recording.vo.qual.PrsQualDataDetailVO;
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
     * 给主体绑定对应敞口的指标
     *
     * @param versionMaster
     * @param entityCode
     * @return
     * @author wpp
     */
    boolean bindQualData(PrsVersionMaster versionMaster, String entityCode, Integer taskYear);

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
    PrsQualData getByEntityAndQcodeAndTime(String entityCode, String qualCode, Integer timeValue);

    List<PrsQualDataExcelDto> findExcelListByCode(String code);

    /**
     * 根据指标和年份统计主体总数
     *
     * @param qualCode 指标code
     * @param dataYear 年份
     * @return Integer
     */
    long countEntity(String qualCode, String dataYear);

    /**
     * 统计不同挡位的主体数量
     *
     * @param value    挡位
     * @param dataYear
     * @param value
     * @return
     */
    long countByValue(String qualCode, String dataYear, String value);

    /**
     * 统计缺失
     *
     * @param qualCode 指标code
     * @param dataYear 年份
     * @return long
     */
    long countLose(String qualCode, String dataYear);


    List<PrsQualData> listByCodeAndTimeAndValueIsNotNull(String qualCode, String dataYear);


    /**
     * 查询详情（指标详情 和 指标下的evd）
     *
     * @param qualCOde
     * @return
     */
    PrsQualDataDetailVO getByCode(String qualCOde);
}
