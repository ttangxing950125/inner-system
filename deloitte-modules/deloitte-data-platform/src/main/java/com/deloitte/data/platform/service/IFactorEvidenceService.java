package com.deloitte.data.platform.service;

import com.deloitte.data.platform.dto.FactorCalculateDto;

/**
 * 指标和Evidence计算
 *
 * @author XY
 * @date 2022/11/18
 */
public interface IFactorEvidenceService {

    void calculate(FactorCalculateDto dto);

}
