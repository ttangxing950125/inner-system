package com.deloitte.additional.recording.service.center;

import com.deloitte.additional.recording.dto.ModerQuanAndQualFactorDto;

import java.util.List;

/**
 * @创建人 tangx
 * @创建时间 2022/11/17
 * @描述 中新库 定性/定量 / 政府 service
 */
public interface ModelQualQuanGovFactorService {

    /**
     * 查询中心库指标
     * @param tarMid 中心库敞口code
     * @param prefix 表前缀
     * @param type 类型
     * @return
     */
    List<ModerQuanAndQualFactorDto> getQualListFromDataCenter(String tarMid, String prefix, String type);

    /**
     * 根据中心库指标id查询 code
     *
     * @param prefix    表前缀
     * @param tarQualid 中心库指标id
     * @param tarType
     * @return
     */
    String  getQualFromDataCenter(String prefix, String tarQualid, String tarType);
}
