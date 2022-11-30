package com.deloitte.additional.recording.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deloitte.additional.recording.domain.PrsQualEfficiency;
import com.deloitte.additional.recording.dto.PrsQualDataExcelDto;
import com.deloitte.additional.recording.vo.qual.PrsQualEfficiencyVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @创建人 tangx
 * @创建时间 2022/11/24
 * @描述 PrsQualEfficiency 业务逻辑处理
 */
public interface PrsQualEfficiencyService  extends IService<PrsQualEfficiency> {


    /**
     * 分页查询指标-任务列表
     *
     * @param token
     * @param versionId 版本id
     * @param modelCode 敞口code
     * @param qualCodes 指标code 用,隔开
     * @param userYear  年份
     * @param page      当前页码
     * @param pageSize  当前页面数据大小
     * @return
     */
    Page<PrsQualEfficiencyVO> page(String token, Integer versionId, String modelCode, String qualCodes, String qualName, Integer riskLevel, String userYear, Integer page, Integer pageSize);


    /**
     * 查询不同指标下的主体信息
     * @param qualCodes 指标code 用,分隔拼接成的字符串
     * @return  Map<String, List<PrsQualEfficiencyExcelVO>>
     */
    Map<String, List<PrsQualDataExcelDto>> findByCodes(String qualCodes,String token);

    /**
     * 导入指标任务信息
     * @param serviceFile 文件
     */
    void importExcel(MultipartFile serviceFile,String token);
}
