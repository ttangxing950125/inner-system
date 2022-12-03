package com.deloitte.data.platform.base.service;

import com.deloitte.data.platform.dto.FactorCalculateDto;
import com.deloitte.data.platform.service.IFactorEvidenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

/**
 * 指标与Evidence
 *
 * @author XY
 * @date 2022/11/19
 */
@SpringBootTest
public class IFactorEvidenceServiceTest {

    @Autowired
    private IFactorEvidenceService factorEvidenceService;

    @Test
    void calculate() {
        FactorCalculateDto dto = new FactorCalculateDto();
 //       dto.setEntityCode("IB000113");
        dto.setYear(2021);
//        dto.setReportDate(LocalDate.parse("2021-12-31"));
//        dto.setFactorCode("M071_Profitability5");
        factorEvidenceService.calculate(dto);
    }


}