package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.redis.service.RedisService;
import com.deloitte.crm.constants.*;
import com.deloitte.crm.domain.BondInfo;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.dto.AttrValueMapDto;
import com.deloitte.crm.mapper.BondInfoMapper;
import com.deloitte.crm.mapper.EntityAttrValueMapper;
import com.deloitte.crm.service.IEntityAttrService;
import com.deloitte.crm.service.IEntityAttrValueService;
import com.deloitte.crm.service.*;
import com.deloitte.crm.vo.BondEntityInfoVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-23
 */
@Service
public class BondInfoServiceImpl implements IBondInfoService {
    @Resource
    private BondInfoMapper bondInfoMapper;

    @Resource
    private IEntityAttrService entityAttrService;

    @Resource
    private IEntityAttrValueService entityAttrValueService;

    @Resource
    private RedisService redisService;

    @Resource
    private EntityAttrValueMapper entityAttrValueMapper;

    @Resource
    private IEntityInfoService iEntityInfoService;

    @Resource
    private EntityInfoManager entityInfoManager;


    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public BondInfo selectBondInfoById(Long id) {
        return bondInfoMapper.selectBondInfoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param bondInfo 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<BondInfo> selectBondInfoList(BondInfo bondInfo) {
        return bondInfoMapper.selectBondInfoList(bondInfo);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param bondInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertBondInfo(BondInfo bondInfo) {
        return bondInfoMapper.insertBondInfo(bondInfo);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param bondInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateBondInfo(BondInfo bondInfo) {
        return bondInfoMapper.updateBondInfo(bondInfo);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteBondInfoByIds(Long[] ids) {
        return bondInfoMapper.deleteBondInfoByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteBondInfoById(Long id) {
        return bondInfoMapper.deleteBondInfoById(id);
    }

    /**
     * 检查企业债券全称
     *
     * @param fullName
     * @return
     */
    @Override
    public R checkEntityBondFullName(String fullName){
        List<EntityAttrValue> entityAttrValue = entityAttrValueMapper.checkEntityBondFullName(fullName);
        if(entityAttrValue.size()==0){return R.ok(SuccessInfo.ENABLE_CREAT_ENTITY.getInfo());}
        return R.ok(BadInfo.UNABLE_CREAT.getInfo());
    }

    /**
     * 检查企业债券代码
     * @param tradCode
     * @return
     */
    @Override
    public R checkEntityBondTradCode(String tradCode){
        List<EntityAttrValue> entityAttrValue = entityAttrValueMapper.checkEntityBondTradCode(tradCode);
        if(entityAttrValue.size()==0){return R.ok(SuccessInfo.ENABLE_CREAT_ENTITY.getInfo());}
        return R.ok(BadInfo.UNABLE_CREAT.getInfo());
    }

    /**
     * 检查企业债券简称
     * @param shortName
     * @return
     */
    @Override
    public R checkEntityBondShortName(String shortName){
        List<EntityAttrValue> entityAttrValue = entityAttrValueMapper.checkEntityBondShortName(shortName);
        if(entityAttrValue.size()==0){return R.ok(SuccessInfo.ENABLE_CREAT_ENTITY.getInfo());}
        return R.ok(BadInfo.UNABLE_CREAT.getInfo());
    }

    /**
     * keyword 为需要校验的键
     * value 为需要比较的值
     * @param keyword
     * @param value
     * @return
     */
    public R checkEntityBondValue(String keyword,String value){
        HashMap<String, Integer> map = new HashMap<>();
        map.putAll(AttrValueMappingMap.BOND_FULL_NAME.get());
        map.putAll(AttrValueMappingMap.COLLECTIVE_BONDS.get());
        map.putAll(AttrValueMappingMap.BOND_FULL_NAME.get());
        map.putAll(AttrValueMappingMap.WHETHER_VIOLATION_ID.get());

        Integer key = map.get(keyword);
        if(key!=null){
            List<EntityAttrValue> entityAttrValue = entityAttrValueMapper.checkEntityBondValue(key,value);
            if(entityAttrValue.size()!=0){return R.ok(BadInfo.UNABLE_CREAT.getInfo());}
            return R.ok(SuccessInfo.ENABLE_CREAT_ENTITY.getInfo());
        }
        return R.fail(BadInfo.VALID_EMPTY_TARGET);
    }



    /**
     * 根据债券简称查询
     * @param shortName
     * @return
     */
    @Override
    public BondInfo findByShortName(String shortName) {
        BondInfo bondInfo = redisService.getCacheMapValue(CacheName.BOND_CACHE, shortName);
        if (bondInfo==null){
            bondInfo = bondInfoMapper.findByShortName(shortName);
            redisService.setCacheMapValue(CacheName.BOND_CACHE, shortName, bondInfo);
        }

        return bondInfo;
    }

    /**
     * 根据id保存或新增
     *
     * @param bondInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BondInfo saveOrUpdate(BondInfo bondInfo) {
        if (bondInfo.getId() != null) {
            int count = bondInfoMapper.updateBondInfo(bondInfo);
            bondInfo = count > 0 ? bondInfoMapper.selectBondInfoById(bondInfo.getId()) : bondInfo;
        } else {
            //新增债券
            bondInfoMapper.insertBondInfo(bondInfo);
            DecimalFormat g1 = new DecimalFormat("000000");
            String startZeroStr = g1.format(bondInfo.getId());
            bondInfo.setBondCode("BD" + startZeroStr);

            bondInfoMapper.updateBondInfo(bondInfo);
        }

        redisService.redisTemplate.opsForHash().delete(CacheName.BOND_CACHE, bondInfo.getBondShortName());


        return bondInfo;
    }

    /**
     *  查询选择的债券 查询债券的具体信息 by正杰
     * @param bondCode
     * @return
     * @author 正杰
     * @date 2022/9/28
     */
    @Override
    public R<BondEntityInfoVo> findAllDetail(String entityCode, String bondCode) {
        List<AttrValueMapDto> result = new ArrayList<>();
        //组装主体信息 主体属性来自 table = ENTITY_INFO
        EntityInfo entityInfo = iEntityInfoService.getBaseMapper().selectOne(new QueryWrapper<EntityInfo>().lambda()
                .eq(EntityInfo::getEntityCode, entityCode));
        Integer entityId = entityInfo.getId();
        result.add(new AttrValueMapDto(entityId,Common.TABLE_ENTITY_INFO,"债务主体德勤代码",entityCode));
        result.add(new AttrValueMapDto(entityId,Common.TABLE_ENTITY_INFO,"债务主体名称_分离后",entityInfo.getEntityName()));
        result.add(new AttrValueMapDto(entityId,Common.TABLE_ENTITY_INFO,"债务主体名称_原始",entityInfo.getEntityNameHis()));
        result.add(new AttrValueMapDto(entityId,Common.TABLE_ENTITY_INFO,"债务主体统一社会信用代码",entityInfo.getCreditCode()));

        //组装债券信息 债券属性来自 table = BOND_INFO
        BondInfo bondInfo = bondInfoMapper.selectOne(new QueryWrapper<BondInfo>().lambda().eq(BondInfo::getBondCode, bondCode));
        Integer bondInfoId = bondInfo.getId().intValue();
        result.add(new AttrValueMapDto(bondInfoId,Common.TABLE_BOND_INFO,"债券简称",bondInfo.getBondShortName()));
        result.add(new AttrValueMapDto(bondInfoId,Common.TABLE_BOND_INFO,"债券类型",null));
        result.add(new AttrValueMapDto(bondInfoId,Common.TABLE_BOND_INFO,"债务关系有效性 / 存续状态",bondInfo.getBondState().toString(),"债卷状态 0_存续 1_违约 2_已兑付"));

        //组装主体债券的具体属性信息 属性来自 table = ENTITY_ATTR_VALUE

        //是否为ABS
        EntityAttrValue isABS = entityAttrValueMapper.findValueByCodeAndAttrId(617, bondCode);
        if(isABS!=null){
        result.add(new AttrValueMapDto(isABS.getId(),Common.ENTITY_ATTR_VALUE,"是否为ABS",isABS.getValue()
                ,"1、Y：发过集合债\n" +
                "2、N：未曾发过集合债"));
        }else{result.add(new AttrValueMapDto().setName("是否为ABS"));}

        //债券交易代码
        EntityAttrValue tradCode = entityAttrValueMapper.findValueByCodeAndAttrId(804, bondCode);
        if(tradCode!=null) {
            result.add(new AttrValueMapDto(tradCode.getId(), Common.ENTITY_ATTR_VALUE, "债券交易代码", tradCode.getValue()));
        }else{result.add(new AttrValueMapDto().setName("债券交易代码"));}

        //债券全称
        EntityAttrValue bondFullName = entityAttrValueMapper.findValueByCodeAndAttrId(48, bondCode);
        if(bondFullName!=null) {
            result.add(new AttrValueMapDto(bondFullName.getId(), Common.ENTITY_ATTR_VALUE, "债券全称", bondFullName.getValue(), "会随上传文档内容更新而更新"));
        }else{result.add(new AttrValueMapDto().setName("债券全称"));}

        //起息日
        EntityAttrValue valueDate = entityAttrValueMapper.findValueByCodeAndAttrId(52, bondCode);
        if(valueDate!=null){
        result.add(new AttrValueMapDto(valueDate.getId(),Common.ENTITY_ATTR_VALUE,"起息日",valueDate.getValue(),"会随上传文档内容更新而更新"));
        }else{result.add(new AttrValueMapDto().setName("起息日"));}

        //到期兑付日
        EntityAttrValue dueCashingDate = entityAttrValueMapper.findValueByCodeAndAttrId(53, bondCode);
        if(dueCashingDate!=null){
        result.add(new AttrValueMapDto(dueCashingDate.getId(),Common.ENTITY_ATTR_VALUE,"到期兑付日",dueCashingDate.getValue(),"会随上传文档内容更新而更新"));
        }else{result.add(new AttrValueMapDto().setName("到期兑付日"));}
        //TODO 债务关系来源
        result.add(new AttrValueMapDto().setName("债务关系来源"));

        //前发行人名称 此处用的发行人全称
        EntityAttrValue issuerName = entityAttrValueMapper.findValueByCodeAndAttrId(59, bondCode);
        result.add(new AttrValueMapDto(issuerName.getId(),Common.ENTITY_ATTR_VALUE,"前发行人名称",issuerName.getValue(),"会随上传文档内容更新而更新"));

        /**
         * 通过发行人全称 查找到 发行人
         */
        EntityInfo issuerEntity = iEntityInfoService.getBaseMapper().selectOne(new QueryWrapper<EntityInfo>().lambda()
                .eq(EntityInfo::getEntityName, issuerName.getValue()));
        if(issuerEntity!=null){
            //前发行人德勤主体代码
            result.add(new AttrValueMapDto(issuerEntity.getId(),Common.TABLE_ENTITY_INFO,"前发行人德勤主体代码",issuerEntity.getEntityCode()));
            //前发行人统一社会信用代码
            result.add(new AttrValueMapDto(issuerEntity.getId(),Common.TABLE_ENTITY_INFO,"前发行人统一社会信用代码",issuerEntity.getCreditCode()));
        }else{
            //前发行人德勤主体代码
            result.add(new AttrValueMapDto().setName("前发行人德勤主体代码"));
            //前发行人统一社会信用代码
            result.add(new AttrValueMapDto().setName("前发行人统一社会信用代码"));
        }

        //TODO 接收方名称
        result.add(new AttrValueMapDto().setName("接收方名称"));
        //TODO 接收方德勤主体代码
        result.add(new AttrValueMapDto().setName("接收方德勤主体代码"));
        //TODO 接收方统一社会信用代码
        result.add(new AttrValueMapDto().setName("接收方统一社会信用代码"));

        //是否违约
        EntityAttrValue whetherViolation = entityAttrValueMapper.findValueByCodeAndAttrId(108, bondCode);
        if(whetherViolation!=null){
        result.add(new AttrValueMapDto(whetherViolation.getId(),Common.ENTITY_ATTR_VALUE,"是否违约",whetherViolation.getValue(),"来自IB违约运维记录，需要和老周确认从wind哪儿来的"));
        }else{result.add(new AttrValueMapDto().setName("是否违约"));}

        //违约类型
        EntityAttrValue violationType = entityAttrValueMapper.findValueByCodeAndAttrId(111, bondCode);
        if(violationType!=null){
        result.add(new AttrValueMapDto(violationType.getId(),Common.ENTITY_ATTR_VALUE,"违约类型",violationType.getValue(),"来自IB违约运维记录，需要和老周确认从wind哪儿来的"));
        }else{result.add(new AttrValueMapDto().setName("违约类型"));}

        //违约日期
        EntityAttrValue violationDate = entityAttrValueMapper.findValueByCodeAndAttrId(109, bondCode);
        if(violationDate!=null){
        result.add(new AttrValueMapDto(violationDate.getId(),Common.ENTITY_ATTR_VALUE,"违约日期",violationDate.getValue(),"来自IB违约运维记录，需要和老周确认从wind哪儿来的"));
        }else{result.add(new AttrValueMapDto().setName("违约日期"));}

        return R.ok(new BondEntityInfoVo().setList(result));
    }

    /**
     * 修改具体信息 by正杰
     * @param bondInfoEditVo
     * @author 正杰
     * @date 2022/9/28
     */
    @Override
    public R<BondEntityInfoVo> editAllDetail(BondEntityInfoVo bondInfoEditVo) {
        List<AttrValueMapDto> list = bondInfoEditVo.getList();
        if(list.size()==0){return R.ok(bondInfoEditVo,BadInfo.PARAM_PROBABLY_BE_EMPTY.getInfo());}


        return null;
    }

    /**
     * 修改数据通用方法 by正杰
     * @param attrValueMapDto
     * @author 正杰
     * @date 2022/9/28
     */
    public String updateAllData(AttrValueMapDto attrValueMapDto){
        String table = attrValueMapDto.getTable();
        Integer id = attrValueMapDto.getId();
        Assert.notNull(table,BadInfo.PARAM_TABLE_COULD_NOT_BE_NULL.getInfo());
        Assert.notNull(id,BadInfo.PARAM_TABLE_COULD_NOT_BE_NULL.getInfo());
        switch (table){
            case Common.TABLE_ENTITY_INFO:
                EntityInfo entityInfo = iEntityInfoService.getBaseMapper()
                        .selectOne(new QueryWrapper<EntityInfo>().lambda()
                        .eq(EntityInfo::getId, id));
                return entityInfoManager.updateEntityName(entityInfo,attrValueMapDto.getValue(),"");
            case Common.TABLE_BOND_INFO:

            case Common.ENTITY_ATTR_VALUE:

            default:
                return BadInfo.COULD_NOT_MATCH_TABLE.getInfo();
        }

    }

}
