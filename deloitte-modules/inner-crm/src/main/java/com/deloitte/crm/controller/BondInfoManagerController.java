package com.deloitte.crm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.domain.EntityAttrIntype;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.dto.BondInfoManualDto;
import com.deloitte.crm.service.EntityAttrIntypeService;
import com.deloitte.crm.service.IBondInfoService;
import com.deloitte.crm.service.IEntityInfoService;
import com.deloitte.crm.vo.BondEntityInfoVo;
import com.deloitte.crm.vo.TargetEntityBondsVo;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
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
@Slf4j
public class BondInfoManagerController {

    private IEntityInfoService iEntityInfoService;

    private IBondInfoService iBondInfoService;

    private EntityAttrIntypeService entityAttrIntypeService;

    /**
     * 查询债卷信息 模糊匹配
     * @param name    entity_name || bond_short_name
     * @param keyword 请传入常量 ENTITY || BOND
     * @return R<List<TargetEntityBondsVo>>
     * @author 正杰
     * @date 2022/9/25
     */
    @ApiOperation(value="一级匹配 by正杰")
    @ApiImplicitParams({
        @ApiImplicitParam(name="name",value="请传入主体名||债券名 entity_name || bond_short_name",paramType = "query",dataType = "String"),
        @ApiImplicitParam(name="keyword",value="请传入常量 ENTITY || BOND",paramType="query",dataType = "String"),
        @ApiImplicitParam(name="pageNum",value="翻页控制",paramType="query",dataType = "Integer")
    })
    @PostMapping("/findBondOrEntity")
    public R <Page<TargetEntityBondsVo>> findBondOrEntity(String name, String keyword, Integer pageNum, Integer pageSize) {
        //模糊匹配 查询主体||债券信息
        log.info("  >>>> 债券信息管理 - 开始  <<<<  ");
        log.info("  =>> 模糊匹配查询  <<=");
        return iEntityInfoService.findBondOrEntity(name,keyword,pageNum,pageSize);
    }

    /**
     * 查询债券或是主体下相关的主体或是债券信息 by正杰
     * @param id
     * @param keyword
     * @return
     * @author 正杰
     * @date 2022/9/25
     */
    @ApiOperation(value="二级匹配 by正杰")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="传入 id ",paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name="keyword",value="请传入常量 ENTITY || BOND",paramType="query",dataType = "String")
    })
        @PostMapping("/findRelationEntityOrBond")
    public R findRelationEntityOrBond(Integer id,String keyword,Integer pageNum,Integer pageSize){
        // 查询主体||债券信息
        log.info("  >>>> 债券信息管理 - 开始  <<<<  ");
        log.info("  =>> 二级匹配查询  <<=");
        return iEntityInfoService.findRelationEntityOrBond(id,keyword,pageNum,pageSize);
    }

    /**
     * 查询债券的具体信息 by正杰
     * @param bondCode
     * @author 正杰
     * @date 2022/9/28
     */
    @ApiOperation(value="查询具体信息 by正杰")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "entityCode", value = "请传入 主体代码:IB000001 ", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "bondCode", value = "请传入 债券代码:BD000001 ", paramType = "query", dataType = "String")
    })
    @ApiResponse(code = 200,message = "操作成功",response =BondEntityInfoVo.class)
    @PostMapping("/findAllDetail")
    public R<List<BondEntityInfoVo>> findAllDetail(@NotNull(message = "主体代码不能为空") String entityCode,@NotNull(message = "债券代码不能为空") String bondCode){
        log.info("  >>>> 债券信息管理 - 开始  <<<<  ");
        log.info("  =>> 具体信息查询  <<=");
        return iBondInfoService.findAllDetail(entityCode,bondCode);
    }

    /**
     * 修改具体信息 by正杰
     * @param bondInfoEditVo
     * @author 正杰
     * @date 2022/9/28
     */
    @ApiOperation(value="修改具体信息 by正杰")
    @ApiImplicitParam(name = "bondInfoEditVo", value = "传入bondInfoEditVo 中 list需要修改的参数", paramType = "body", dataType = "body",dataTypeClass = BondEntityInfoVo.class)
    @PostMapping("/editAllDetail")
    public R editAllDetail(@RequestBody List<BondEntityInfoVo> bondInfoEditVo){
        log.info("  >>>> 债券信息管理 - 开始  <<<<  ");
        log.info("  =>> 修改具体信息  <<=");
        return iBondInfoService.editAllDetail(bondInfoEditVo);
    }

    /**
     * 手动添加债券信息
     * @author 正杰
     * @date 2022/10/11
     * @param bondInfoManualDto
     * @return 操作成功与否信息
     */
    @ApiOperation(value="手动添加债券信息 by正杰")
    @ApiImplicitParam(name = "bondInfoManualDto", value = "bondInfoManualDto对象", paramType = "body", dataType = "body",dataTypeClass = BondInfoManualDto.class)
    @PostMapping("/insertBondInfoManual")
    public R insertBondInfoManual(@RequestBody BondInfoManualDto bondInfoManualDto){
        log.info("  >>>> 债券信息管理 - 开始  <<<<  ");
        log.info("  =>> 新增债券信息  <<=");
        return iBondInfoService.insertBondInfoManual(bondInfoManualDto);
    }

    /**
     * 通过 id 查询 wind 一二级 类型 一级attr_id 为92 二级为85
     * @author 正杰
     * @date 2022/10/11
     * @param id
     * @return
     */
    @ApiOperation(value="wind下拉框 by正杰")
    @ApiImplicitParam(name = "id", value = "一级传null，二级传id", paramType = "query", dataType = "Integer")
    @PostMapping("/getWindBondType")
    public R<List<EntityAttrIntype>> getWindBondType(Integer id){
        return entityAttrIntypeService.getWindBondType(id);
    }

    /**
     * 根据统一社会信用代码查询该主体 by正杰
     * @param creditCode
     * @return
     */
    @ApiOperation(value="查询主体信息 by正杰")
    @ApiImplicitParam(name = "creditCode", value = "统一社会信用代码", paramType = "query", dataType = "String")
    @PostMapping("/getEntityInfo")
    public R<EntityInfo> getEntityInfo(String creditCode){
        EntityInfo entityInfo = iEntityInfoService.getEntityInfoByCreditCode(creditCode);
        if(entityInfo==null){return R.ok(null,BadInfo.VALID_EMPTY_TARGET.getInfo());}
        return R.ok(entityInfo);
    }

}
