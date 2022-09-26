package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.crm.service.IEntityInfoService;
import com.deloitte.crm.vo.TargetEntityBondsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 正杰
 * @description: BondInfoManagerController
 * @date 2022/9/25
 */
@RestController
@Api(tags = "债券信息管理")
@RequestMapping("/bondInfoManager")
@AllArgsConstructor
public class BondInfoManagerController {

    private IEntityInfoService iEntityInfoService;

    /**
     * 查询债卷信息 模糊匹配
     * @param name    entity_name || bond_short_name
     * @param keyword 请传入常量 ENTITY || BOND
     * @return R<List<TargetEntityBondsVo>>
     * @author 正杰
     * @date 2022/9/25
     */
    @ApiOperation(value="查询债券信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value="请传入主体名||债券名 entity_name || bond_short_name",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name="keyword",value="请传入常量 ENTITY || BOND",paramType="query",dataType = "String")
    })
    @GetMapping("/findBondOrEntity")
    public R<List<TargetEntityBondsVo>> findBondOrEntity(@PathVariable String name, @PathVariable String keyword) {
        //TODO 模糊匹配 查询主体||债券信息
        return iEntityInfoService.findBondOrEntity(name,keyword);
    }
}
