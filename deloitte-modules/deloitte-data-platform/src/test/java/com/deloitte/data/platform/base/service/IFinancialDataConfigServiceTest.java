package com.deloitte.data.platform.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.data.platform.domian.FinancialDataConfig;
import com.deloitte.data.platform.dto.BaseDataConfigDto;
import com.deloitte.data.platform.service.IFinancialDataConfigService;
import com.deloitte.data.platform.vo.BaseDataConfigVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
public class IFinancialDataConfigServiceTest {

    @Resource
    private IFinancialDataConfigService iFinancialDataConfigService;

    @Test
    void getFinancialBaseDataConfigPage() {
        BaseDataConfigDto baseFinDataDto = new BaseDataConfigDto();
        baseFinDataDto.setEntityType("wind");
        baseFinDataDto.setPageNum(1);
        baseFinDataDto.setPageSize(10);
        IPage<BaseDataConfigVo> result = iFinancialDataConfigService.getFinancialBaseDataConfigPage(baseFinDataDto);
        List<BaseDataConfigVo> records = result.getRecords();
        for (BaseDataConfigVo record : records) {
            System.out.println(record);
        }
    }

    @Test
    void getFinancialDataConfig() {
        Set<String> codes = new HashSet<>();
        codes.add("source_seq");
        Map<String, FinancialDataConfig> financialDataConfig = iFinancialDataConfigService.getFinancialDataConfig(codes);
        System.out.println(financialDataConfig.get("source_seq"));
    }
}