package com.deloitte.crm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.web.controller.BaseController;
import com.deloitte.crm.domain.EntityInfoLogsUpdated;
import com.deloitte.crm.dto.EntityCaptureSpeedDto;
import com.deloitte.crm.service.EntityCaptureSpeedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.deloitte.common.core.utils.PageUtils.startPage;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/10/27/14:31
 * @Description:
 */
@RestController
@RequestMapping("/entityCaptureSpeed")
public class EntityCaptureSpeedController extends BaseController {
    @Resource
    private EntityCaptureSpeedService entityCaptureSpeedService;

    public static void main(String[] args) {
        String str = "entity_code_wind\n" +
                "entity_name_new\n" +
                "entity_finainstit_type\n" +
                "entity_biz_scope\n" +
                "entity_intro\n" +
                "entity_address_province_name\n" +
                "entity_address_city_name\n" +
                "entity_address_county_name\n" +
                "entity_address\n" +
                "entity_code_credit\n" +
                "entity_stock_sum\n" +
                "entity_bond_sum\n" +
                "entity_name_history\n" +
                "entity_industry_ssc\n" +
                "entity_industry_wind\n" +
                "entity_industry_sw2014\n" +
                "entity_industry_sw2021\n" +
                "entity_industry_cns\n";

        String[] split = str.split("\n");
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i] + " text comment '" + i + "' ,");

        }
        System.out.println(split.length);
    }

    /**
     * @param entityNameOrCode
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/search")
    public R search(String entityNameOrCode, Integer pageNum, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        return R.ok(entityCaptureSpeedService.search(entityNameOrCode, pageNum, pageSize));
    }
}
