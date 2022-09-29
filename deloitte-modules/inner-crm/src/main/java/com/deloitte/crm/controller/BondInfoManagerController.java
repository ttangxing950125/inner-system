package com.deloitte.crm.controller;

import com.deloitte.common.core.domain.R;
import com.deloitte.crm.dto.AttrValueMapDto;
import com.deloitte.crm.service.IBondInfoService;
import com.deloitte.crm.service.IEntityInfoService;
import com.deloitte.crm.vo.BondEntityInfoVo;
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
     * @param id
     * @param keyword
     * @return
     * @author 正杰
     * @date 2022/9/25
     */
    @ApiOperation(value="查询债券或是主体下相关的主体或是债券信息 by正杰")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="传入 id ",paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name="keyword",value="请传入常量 ENTITY || BOND",paramType="query",dataType = "String")
    })
    @PostMapping("/findRelationEntityOrBond")
    public R findRelationEntityOrBond(Integer id,String keyword){
        // 查询主体||债券信息
        return iEntityInfoService.findRelationEntityOrBond(id,keyword);
    }

    /**
     * 查询债券的具体信息 by正杰
     * @param bondCode
     * @author 正杰
     * @date 2022/9/28
     */
    @ApiOperation(value="查询选择的债券 查询债券的具体信息 by正杰")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "entityCode", value = "请传入 主体代码:IB000001 ", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "bondCode", value = "请传入 债券代码:BD000001 ", paramType = "query", dataType = "String")
    })
    @PostMapping("/findAllDetail")
    public R<BondEntityInfoVo> findAllDetail(String entityCode, String bondCode){
        return iBondInfoService.findAllDetail(entityCode,bondCode);
    }

    /**
     * 修改具体信息 by正杰
     * @param bondInfoEditVo
     * @author 正杰
     * @date 2022/9/28
     */
    @ApiOperation(value="查询选择的债券 查询债券的具体信息 by正杰")
    @ApiImplicitParam(name = "bondInfoEditVo", value = "传入bondInfoEditVo 中 list需要修改的参数", paramType = "query", dataType = "body",dataTypeClass = BondEntityInfoVo.class)
    @PostMapping("/editAllDetail")
    public R<BondEntityInfoVo> editAllDetail(@RequestBody BondEntityInfoVo bondInfoEditVo){
        return iBondInfoService.editAllDetail(bondInfoEditVo);
    }

}
