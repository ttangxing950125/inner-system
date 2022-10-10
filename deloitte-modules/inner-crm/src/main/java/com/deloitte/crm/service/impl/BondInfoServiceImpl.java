package com.deloitte.crm.service.impl;

import java.text.DecimalFormat;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.redis.service.RedisService;
import com.deloitte.crm.constants.*;
import com.deloitte.crm.domain.EntityAttr;
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
import java.util.*;

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
    private IEntityAttrValueService iEntityAttrValueService;


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
    public R checkEntityBondFullName(String fullName) {
        List<EntityAttrValue> entityAttrValue = entityAttrValueMapper.checkEntityBondFullName(fullName);
        if (entityAttrValue.size() == 0) {
            return R.ok(SuccessInfo.ENABLE_CREAT_ENTITY.getInfo());
        }
        return R.ok(BadInfo.UNABLE_CREAT.getInfo());
    }

    /**
     * 检查企业债券代码
     *
     * @param tradCode
     * @return
     */
    @Override
    public R checkEntityBondTradCode(String tradCode) {
        List<EntityAttrValue> entityAttrValue = entityAttrValueMapper.checkEntityBondTradCode(tradCode);
        if (entityAttrValue.size() == 0) {
            return R.ok(SuccessInfo.ENABLE_CREAT_ENTITY.getInfo());
        }
        return R.ok(BadInfo.UNABLE_CREAT.getInfo());
    }

    /**
     * 检查企业债券简称
     *
     * @param shortName
     * @return
     */
    @Override
    public R checkEntityBondShortName(String shortName) {
        List<EntityAttrValue> entityAttrValue = entityAttrValueMapper.checkEntityBondShortName(shortName);
        if (entityAttrValue.size() == 0) {
            return R.ok(SuccessInfo.ENABLE_CREAT_ENTITY.getInfo());
        }
        return R.ok(BadInfo.UNABLE_CREAT.getInfo());
    }

    /**
     * keyword 为需要校验的键
     * value 为需要比较的值
     *
     * @param keyword
     * @param value
     * @return
     */
    public R checkEntityBondValue(String keyword, String value) {
        HashMap<String, Integer> map = new HashMap<>();
        map.putAll(AttrValueMappingMap.BOND_FULL_NAME.get());
        map.putAll(AttrValueMappingMap.COLLECTIVE_BONDS.get());
        map.putAll(AttrValueMappingMap.BOND_FULL_NAME.get());
        map.putAll(AttrValueMappingMap.WHETHER_VIOLATION_ID.get());

        Integer key = map.get(keyword);
        if (key != null) {
            List<EntityAttrValue> entityAttrValue = entityAttrValueMapper.checkEntityBondValue(key, value);
            if (entityAttrValue.size() != 0) {
                return R.ok(BadInfo.UNABLE_CREAT.getInfo());
            }
            return R.ok(SuccessInfo.ENABLE_CREAT_ENTITY.getInfo());
        }
        return R.fail(BadInfo.VALID_EMPTY_TARGET);
    }


    /**
     * 根据债券简称查询
     *
     * @param shortName
     * @return
     */
    @Override
    public BondInfo findByShortName(String shortName) {
        BondInfo bondInfo = redisService.getCacheMapValue(CacheName.BOND_CACHE, shortName);
        if (bondInfo == null) {
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
            int count = bondInfoMapper.updateById(bondInfo);
            bondInfo = count > 0 ? bondInfoMapper.selectById(bondInfo.getId()) : bondInfo;
        } else {
            //新增债券
            bondInfoMapper.insertBondInfo(bondInfo);
            DecimalFormat g1 = new DecimalFormat("000000");
            String startZeroStr = g1.format(bondInfo.getId());
            bondInfo.setBondCode("BD" + startZeroStr);

            bondInfoMapper.updateById(bondInfo);
        }

        redisService.redisTemplate.opsForHash().delete(CacheName.BOND_CACHE, bondInfo.getBondShortName());


        return bondInfo;
    }

    /**
     * 查询选择的债券 查询债券的具体信息 by正杰
     *
     * @param bondCode
     * @return
     * @author 正杰
     * @date 2022/9/28
     */
    @Override
    public R<BondEntityInfoVo> findAllDetail(String entityCode, String bondCode) {
        BondEntityInfoVo result = new BondEntityInfoVo();
        Map<String, AttrValueMapDto> attrs = result.getAttrs();
        //组装主体信息 主体属性来自 table = entity_info
        EntityInfo entityInfo = iEntityInfoService.getBaseMapper().selectOne(new QueryWrapper<EntityInfo>().lambda()
                .eq(EntityInfo::getEntityCode, entityCode));
        result.setEntityInfo(entityInfo);

        //组装债券信息 债券属性来自 table = bond_info
        BondInfo bondInfo = bondInfoMapper.selectOne(new QueryWrapper<BondInfo>().lambda().eq(BondInfo::getBondCode, bondCode));
        result.setBondInfo(bondInfo);

        //TODO 组装主体债券的具体属性信息 属性来自 table = entity_attr_value
        //是否为ABS
        attrs.putAll(iEntityAttrValueService.findAttrValue(bondCode, 617));

        //债券交易代码
        attrs.putAll(iEntityAttrValueService.findAttrValue(bondCode, 804));

        //债券全称
        attrs.putAll(iEntityAttrValueService.findAttrValue(bondCode, 48));

        //起息日
        attrs.putAll(iEntityAttrValueService.findAttrValue(bondCode, 52));

        //到期兑付日
        attrs.putAll(iEntityAttrValueService.findAttrValue(bondCode, 53));

        //TODO 债务关系来源
        attrs.put("债务关系来源",new AttrValueMapDto().setName("债务关系来源"));

        //前发行人名称 此处用的发行人全称
        attrs.putAll(iEntityAttrValueService.findAttrValue(bondCode, 59));
        EntityAttrValue issuerName = entityAttrValueMapper.findValueByCodeAndAttrId(59, bondCode);

        /**
         * 通过发行人全称 查找到 发行人
         */
        EntityInfo issuerEntity = iEntityInfoService.getBaseMapper().selectOne(new QueryWrapper<EntityInfo>().lambda()
                .eq(EntityInfo::getEntityName, issuerName.getValue()));
        //前发行人德勤主体代码
        attrs.put("前发行人德勤主体代码",new AttrValueMapDto().setValue(issuerEntity.getEntityCode()).setValueId(issuerEntity.getId()));
        //前发行人统一社会信用代码
        attrs.put("前发行人统一社会信用代码",new AttrValueMapDto().setValue(issuerEntity.getCreditCode()).setValueId(issuerEntity.getId()));


        //TODO 接收方名称
        attrs.put("接收方名称",new AttrValueMapDto().setName("接收方名称"));

        //TODO 接收方德勤主体代码
        attrs.put("接收方德勤主体代码",new AttrValueMapDto().setName("接收方德勤主体代码"));

        //TODO 接收方统一社会信用代码
        attrs.put("接收方统一社会信用代码",new AttrValueMapDto().setName("接收方统一社会信用代码"));

        //是否违约
        attrs.putAll(iEntityAttrValueService.findAttrValue(bondCode,108));

        //违约类型
        attrs.putAll(iEntityAttrValueService.findAttrValue(bondCode,111));

        //违约日期
        attrs.putAll(iEntityAttrValueService.findAttrValue(bondCode,109));

        return R.ok(result.setAttrs(attrs));
    }

    /**
     * 修改具体信息 by正杰
     *
     * @param bondInfoEditVo
     * @author 正杰
     * @date 2022/9/28
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R editAllDetail(BondEntityInfoVo bondInfoEditVo) {
        BondInfo bondInfo = bondInfoEditVo.getBondInfo();
        Assert.notNull(bondInfo,BadInfo.PARAM_EMPTY.getInfo());

        EntityInfo entityInfo = bondInfoEditVo.getEntityInfo();
        Assert.notNull(entityInfo,BadInfo.PARAM_EMPTY.getInfo());

        Map<String, AttrValueMapDto> attrs = bondInfoEditVo.getAttrs();
        Assert.notNull(attrs,BadInfo.PARAM_EMPTY.getInfo());

        bondInfoMapper.updateById(bondInfo);
        iEntityInfoService.updateById(entityInfo);

        for (Map.Entry<String, AttrValueMapDto> next : attrs.entrySet()) {
            AttrValueMapDto value = next.getValue();
            if (value != null) {
                // 此处只做修改 不做新增 无需 entity_code
                iEntityAttrValueService.SaveAttrValue(null,value);
            }
        }
        return R.ok(SuccessInfo.SUCCESS.getInfo());
    }

    /**
     * 自动更新债券公私募状态、abs状态、集合债状态
     * @param dqBondCode
     * @return
     */
    @Override
    public boolean updateBondType(String dqBondCode) {
        //根据code查询债券
        BondInfo bondInfo = bondInfoMapper.findByDqCode(dqBondCode);
        if (bondInfo==null){
            return false;
        }

        //59 发行人全称
        EntityAttrValue publisher = entityAttrValueMapper.findValueByCodeAndAttrId(59, dqBondCode);
        if (publisher!=null){
            //集合债，判断规则：基于34行的【发行人全称】字段识别，如字段内容中包含逗号（“,”），判断为“是”；
            String value = publisher.getValue();
            bondInfo.setColl(value.contains(","));
        }

        //Wind债券类型(二级)  85
        //ABS，判断规则：基于60行的【Wind债券类型(二级)】字段识别，如字段内容为以下之一的，判断为“是”：“交易商协会ABN”、“证监会主管ABS”、“银保监会主管ABS”、“项目收益票据”；
        EntityAttrValue abs = entityAttrValueMapper.findValueByCodeAndAttrId(85, dqBondCode);
        List<String> absList = Arrays.asList("交易商协会ABN", "证监会主管ABS", "银保监会主管ABS", "项目收益票据");
        if (abs!=null){
            String value = abs.getValue();
            bondInfo.setAbs(absList.contains(value));
        }

        //72	债券信息
        EntityAttrValue raise = entityAttrValueMapper.findValueByCodeAndAttrId(72, dqBondCode);
        if (raise!=null){
            String value = raise.getValue();
            if (Objects.equals(value,"公募")){
                bondInfo.setRaiseType(0);
            }else if (Objects.equals(value,"私募")) {
                bondInfo.setRaiseType(1);
            }
        }

        this.saveOrUpdate(bondInfo);

        return true;
    }

}
