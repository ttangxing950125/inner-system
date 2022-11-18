package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.crm.service.EntityAttrIntypeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 冉浩岑
 * @date 2022/09/29 17:53
 */
@RestController
@RequestMapping("/intype")
public class EntityAttrIntypeController {
    @Autowired
    private EntityAttrIntypeService entityAttrIntypeService;

    /**
     * 根据attrId查询选项
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/9/29 13:56
     */
    @ApiOperation(value = "根据attrId查询选项")
    @ApiImplicitParam(name = "attrId", value = "attrId", paramType = "query", example = "", dataType = "Integer")
    @PostMapping("/getTypeByAttrId")
    public R getTypeByAttrId(Integer attrId) {
        return R.ok(entityAttrIntypeService.getTypeByAttrId(attrId));
    }

}
