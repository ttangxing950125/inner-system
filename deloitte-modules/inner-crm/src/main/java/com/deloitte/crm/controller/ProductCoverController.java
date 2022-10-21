package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.GovInfo;
import com.deloitte.crm.service.IGovInfoService;
import com.deloitte.crm.service.ProductsCoverService;
import com.deloitte.crm.vo.EntityOrGovByAttrVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resources;

/**
 * @author PenTang
 * @date 2022/10/13 11:00
 */

@RestController
@RequestMapping("/ProductsCover")
@Api(tags = "产品相关数据")
public class ProductCoverController {


    @Autowired
    private ProductsCoverService productsCoverService;
    @Autowired
    private IGovInfoService iGovInfoService;
    /***
     *覆盖查询主体
     *
     * @param entityOrGovByAttrVo
     * @return R
     * @author penTang
     * @date 2022/10/9 15:57
     */
    @ApiOperation(value = "查询覆盖情况")
    @PostMapping("/getCov")
    @ApiImplicitParam(name = "getProCov", value = "", paramType = "body", example = "", dataTypeClass = EntityOrGovByAttrVo.class)

    public R getProCov(@Validated @RequestBody EntityOrGovByAttrVo entityOrGovByAttrVo) {
        //查询政府覆盖
        if (ObjectUtils.equals(entityOrGovByAttrVo.getEntityType(), "GV")) {
            return iGovInfoService.getGovEntityResult(entityOrGovByAttrVo);
        //查询企业覆盖情况
        } else {
            return R.ok(productsCoverService.getProducts(entityOrGovByAttrVo));
        }

    }
}
