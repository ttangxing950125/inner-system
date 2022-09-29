package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.BondInfo;
import com.deloitte.crm.dto.EntityAttrDetailDto;
import com.deloitte.crm.service.IBondInfoService;
import com.deloitte.crm.service.IEntityInfoService;
import com.deloitte.crm.vo.TargetEntityBondsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    private IBondInfoService iBondInfoService;

    /**
     * 查询债卷信息 模糊匹配
     * @param name    entity_name || bond_short_name
     * @param keyword 请传入常量 ENTITY || BOND
     * @return R<List<TargetEntityBondsVo>>
     * @author 正杰
     * @date 2022/9/25
     */
    @ApiOperation(value="模糊匹配 查询债券信息 by正杰")
    @ApiImplicitParams({
        @ApiImplicitParam(name="name",value="请传入主体名||债券名 entity_name || bond_short_name",paramType = "query",dataType = "String"),
        @ApiImplicitParam(name="keyword",value="请传入常量 ENTITY || BOND",paramType="query",dataType = "String")
    })
    @PostMapping("/findBondOrEntity")
    public R<List<TargetEntityBondsVo>> findBondOrEntity(String name,String keyword) {
        //模糊匹配 查询主体||债券信息
        return iEntityInfoService.findBondOrEntity(name,keyword);
    }

    /**
     * 查询债券或是主体下相关的主体或是债券信息 by正杰
     * @param code
     * @param keyword
     * @return
     * @author 正杰
     * @date 2022/9/25
     */
    @ApiOperation(value="查询债券或是主体下相关的主体或是债券信息 by正杰")
    @ApiImplicitParams({
            @ApiImplicitParam(name="code",value="请传入 主体的entityCode || 债券的bondCode ",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name="keyword",value="请传入常量 ENTITY_CODE || BOND_CODE",paramType="query",dataType = "String")
    })
    @PostMapping("/findRelationEntityOrBond")
    public R<List<TargetEntityBondsVo>> findRelationEntityOrBond(String code,String keyword){
        //TODO 查询主体||债券信息
        return iEntityInfoService.findRelationEntityOrBond(code,keyword);
    }

    /**
     *  查询选择的债券 查询债券的具体信息 by正杰
     * @param bondCode
     * @return
     * @author 正杰
     * @date 2022/9/28
     */
    @ApiOperation(value="查询选择的债券 查询债券的具体信息 by正杰")
    @ApiImplicitParam(name="bondCode",value="请传入 bondCode ",paramType = "query",dataType = "String")
    @PostMapping("/findAllDetail")
    public R<EntityAttrDetailDto> findAllDetail(String bondCode){
        return iBondInfoService.findAllDetail(bondCode);
    }


}
