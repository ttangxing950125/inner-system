package com.deloitte.data.platform.controller;


import com.alibaba.fastjson2.JSON;
import com.deloitte.common.core.domain.R;
import com.deloitte.data.platform.dto.BaseFinDataCheckDto;
import com.deloitte.data.platform.service.IBaseFinDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 财报数据
 *
 * @author XY
 * @date 2022/11/27
 */
@Api(tags = "财报数据")
@RestController
@RequestMapping("/base-fin-data")
public class BaseFinDataController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IBaseFinDataService baseFinDataService;


    @ApiOperation("财报数据校验")
    @PostMapping("/check")
    public R check(@RequestBody BaseFinDataCheckDto dto) {
        logger.info("入参：{}", JSON.toJSONString(dto));
        baseFinDataService.check(dto);
        return R.ok();
    }


}
