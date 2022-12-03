package com.deloitte.data.platform.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deloitte.data.platform.dto.BaseDataDto;
import com.deloitte.data.platform.dto.BaseFinDataCheckDto;
import com.deloitte.data.platform.dto.StatisticalDataAnalysisDto;
import com.deloitte.data.platform.enums.HierarchyEnum;
import com.deloitte.data.platform.enums.SuggestSourceEnum;
import com.deloitte.data.platform.service.IBaseFinDataService;
import com.deloitte.data.platform.vo.BaseDataExtractionVo;
import com.deloitte.data.platform.vo.BaseFinDataVo;
import com.deloitte.data.platform.vo.DataExtractionDetailVo;
import com.deloitte.data.platform.vo.StatisticalDataAnalysisVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class IBaseFinDataServiceTest {

    @Resource
    private IBaseFinDataService iBaseFinDataService;

    @Resource
    private IBaseFinDataService iDataExtractionService;

    @Test
    void getBaseFinDataPage() {
        BaseDataDto baseFinDataDto = new BaseDataDto();
        List<String> years = new ArrayList<>();
        years.add("2022");
        baseFinDataDto.setYears(years);
        baseFinDataDto.setEntityCode("IB000004");
        baseFinDataDto.setPageNum(1);
        baseFinDataDto.setPageSize(10);
        IPage<BaseFinDataVo> result = iBaseFinDataService.getBaseFinDataPage(baseFinDataDto);
        List<BaseFinDataVo> records = result.getRecords();
        for (BaseFinDataVo record : records) {
            System.out.println(record);
        }
    }

    @Test
    void getDataExtractionPage() {
        BaseDataDto baseFinDataDto = new BaseDataDto();
        List<String> years = new ArrayList<>();
        years.add("2022");
        baseFinDataDto.setYears(years);
        baseFinDataDto.setEntityCode("IB000004");
        baseFinDataDto.setPageNum(1);
        baseFinDataDto.setPageSize(10);
        IPage<BaseDataExtractionVo> result = iBaseFinDataService.getBaseDataExtractionPage(baseFinDataDto);
        List<BaseDataExtractionVo> records = result.getRecords();
        for (BaseDataExtractionVo record : records) {
            System.out.println(record);
        }
    }

    @Test
    void getDataExtractionDetailPage() {
        BaseDataDto baseFinDataDto = new BaseDataDto();
        List<String> years = new ArrayList<>();
        years.add("2022");
        baseFinDataDto.setYears(years);
        baseFinDataDto.setPageNum(1);
        baseFinDataDto.setPageSize(10);
        List<String> sources = new ArrayList<>();
        sources.add("1");
        baseFinDataDto.setSources(sources);
        baseFinDataDto.setCode("parent_CF_CFIout_NCFFIA");
        IPage<DataExtractionDetailVo> result = iDataExtractionService.getDataExtractionDetailPage(baseFinDataDto);
        List<DataExtractionDetailVo> records = result.getRecords();
        for (DataExtractionDetailVo record : records) {
            System.out.println(record);
        }
    }

    @Test
    void overview() {
        StatisticalDataAnalysisDto.OverviewDto dto = new StatisticalDataAnalysisDto.OverviewDto();
        List<String> years = new ArrayList<>();
        years.add("2021");
        years.add("2022");

        List<String> sources = new ArrayList<>();
        sources.add(SuggestSourceEnum.WIND.getCode());

        dto.setYears(years);
        dto.setSources(sources);
        dto.setHierarchy(HierarchyEnum.MIDDLE.getCode());
        dto.setPageNum(1);
        dto.setPageSize(10);
        dto.setEntityCode("IB000004");

        IPage<StatisticalDataAnalysisVo.OverviewVo> result = iDataExtractionService.overview(dto);
        List<StatisticalDataAnalysisVo.OverviewVo> records = result.getRecords();
        for (StatisticalDataAnalysisVo.OverviewVo record : records) {
            System.out.println(record);
        }
    }


    /**
     * 科目校验单元测试
     *
     * @aoutor XY
     */
    @Test
    void check() {
        BaseFinDataCheckDto dto = new BaseFinDataCheckDto();
        dto.setYear(2021);
        dto.setEntityCodeList(Arrays.asList("IB000001"));
        this.iBaseFinDataService.check(dto);
    }



}