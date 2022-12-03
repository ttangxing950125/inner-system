package com.deloitte.data.platform.controller;


import com.alibaba.fastjson2.JSON;
import com.deloitte.common.core.domain.R;
import com.deloitte.data.platform.dto.FactorCalculateDto;
import com.deloitte.data.platform.service.IFactorEvidenceService;
import com.deloitte.data.platform.service.MasterEntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * 指标执行
 *
 * @author XY
 * @date 2022/11/19
 */
@Api(tags = "指标执行")
@RestController
@RequestMapping("/factor")
public class FactorEvidenceController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IFactorEvidenceService factorEvidenceService;

    @Autowired
    private MasterEntityService masterEntityService;

    @ApiOperation("指标计算")
    @PostMapping("/calculate")
    public R calculate(@RequestBody FactorCalculateDto dto) {
        logger.info("入参：{}", JSON.toJSONString(dto));
        factorEvidenceService.calculate(dto);
        return R.ok();
    }

    @ApiOperation("测试")
    @PostMapping("/test")
    public R<String> test(@RequestBody FactorCalculateDto dto) {
        logger.info("入参：{}", JSON.toJSONString(dto));
        String obj = masterEntityService.openCrm(new ArrayList<>());
        String str = JSON.toJSONString(obj);
        return R.ok(str);
    }


}
