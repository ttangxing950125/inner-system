package com.deloitte.crm.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.redis.service.RedisService;
import com.deloitte.crm.constants.AttrValueMappingMap;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.CacheName;
import com.deloitte.crm.constants.SuccessInfo;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.dto.BondInfoManualDto;
import com.deloitte.crm.mapper.BondInfoMapper;
import com.deloitte.crm.mapper.EntityAttrValueMapper;
import com.deloitte.crm.mapper.EntityInfoMapper;
import com.deloitte.crm.service.*;
import com.deloitte.crm.vo.BondEntityInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

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
@Slf4j
public class BondInfoServiceImpl implements IBondInfoService {
    @Resource
    private BondInfoMapper bondInfoMapper;

    @Resource
    private IEntityAttrService entityAttrService;

    @Resource
    private RedisService redisService;

    @Resource
    private EntityAttrValueMapper entityAttrValueMapper;

    @Resource
    private IEntityInfoService iEntityInfoService;

    @Resource
    private IEntityAttrValueService iEntityAttrValueService;

    @Resource
    private IEntityBondRelService iEntityBondRelService;

    @Resource
    private EntityInfoMapper entityInfoMapper;


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
    public BondInfo findByShortName(String shortName, Boolean isDeleted) {
       /* BondInfo bondInfo = redisService.getCacheMapValue(CacheName.BOND_CACHE, shortName);
        if (bondInfo == null) {
            bondInfo = bondInfoMapper.findByShortName(shortName, isDeleted);
            redisService.setCacheMapValue(CacheName.BOND_CACHE, shortName, bondInfo);
        }*/
        return  bondInfoMapper.findByShortName(shortName, isDeleted);
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
    public R<List<BondEntityInfoVo>> findAllDetail(String entityCode, String bondCode) {
        //组装主体信息 主体属性来自 table = entity_info
        final EntityInfo entityInfo = Optional.ofNullable(iEntityInfoService.getBaseMapper().selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityCode, entityCode)))
                .orElseThrow(() -> new ServiceException(BadInfo.VALID_EMPTY_TARGET.getInfo()));
        //主体相关信息
        List<BondEntityInfoVo> result = new ArrayList<>(getDataFromEntityInfo(entityInfo, null));
        //组装债券信息 债券属性来自 table = bond_info
        BondInfo bondInfo = bondInfoMapper.selectOne(new QueryWrapper<BondInfo>().lambda().eq(BondInfo::getBondCode, bondCode));
        //债券相关信息
        result.addAll(getDataFromEntityInfo(null, bondInfo));


        //TODO 组装主体债券的具体属性信息 属性来自 table = entity_attr_value

        //TODO 债务关系来源
        result.add(new BondEntityInfoVo("债务关系来源", null));

        //前发行人名称 此处用的发行人全称
        result.add(getDataFromEntityAttrValue(59, bondCode));
        EntityAttrValue issuerName = entityAttrValueMapper.findValueByCodeAndAttrId(59, bondCode);
        if(ObjectUtils.isEmpty(issuerName)){ issuerName = new EntityAttrValue(); }

        /**
         * 通过发行人全称 查找到 发行人
         */
        EntityInfo issuerEntity = iEntityInfoService.getBaseMapper().selectOne(new QueryWrapper<EntityInfo>().lambda()
                .eq(EntityInfo::getEntityName, issuerName.getValue()));
        if (issuerEntity != null) {
            //前发行人德勤主体代码
            result.add(new BondEntityInfoVo(issuerEntity.getId(), 1, false, "前发行人德勤主体代码", "entity_code", issuerEntity.getEntityCode()));
            //前发行人统一社会信用代码
            result.add(new BondEntityInfoVo(issuerEntity.getId(), 1, false, "前发行人统一社会信用代码", "credit_code", issuerEntity.getCreditCode()));
        } else {
            result.add(new BondEntityInfoVo("前发行人德勤主体代码", "entity_code"));
            result.add(new BondEntityInfoVo("前发行人统一社会信用代码", "credit_code"));
        }

        //TODO 接收方名称
        result.add(new BondEntityInfoVo("接收方名称", null));

        //TODO 接收方德勤主体代码
        result.add(new BondEntityInfoVo("接收方德勤主体代码", null));

        //TODO 接收方统一社会信用代码
        result.add(new BondEntityInfoVo("接收方统一社会信用代码", null));

        //是否违约
        result.add(getDataFromEntityAttrValue(108, bondCode));

        //违约类型
        result.add(getDataFromEntityAttrValue(111, bondCode).setEnableEdite(false));

        //违约日期
        result.add(getDataFromEntityAttrValue(109, bondCode).setEnableEdite(false));

        return R.ok(result, SuccessInfo.SUCCESS.getInfo());
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
    public R editAllDetail(List<BondEntityInfoVo> bondInfoEditVo) {
        HashMap<String, String> rule = new HashMap<>();
        rule.put("entity_name", "");
        rule.put("coll", "");
        rule.put("abs", "");
        rule.put("bond_name", "");
        rule.put("bond_short_name", "");
        rule.put("raise_type", "");
        rule.put("bond_state", "");
        rule.put("value_date", "");
        rule.put("due_date", "");
        bondInfoEditVo.forEach(row -> this.edit(row, rule));
        return R.ok(SuccessInfo.SUCCESS.getInfo());
    }

    /**
     * 自动更新债券公私募状态、abs状态、集合债状态
     *
     * @param dqBondCode
     * @return
     */
    @Override
    public boolean updateBondType(String dqBondCode) {
        //根据code查询债券
        BondInfo bondInfo = bondInfoMapper.findByDqCode(dqBondCode);
        if (bondInfo == null) {
            return false;
        }

        //59 发行人全称
        EntityAttrValue publisher = entityAttrValueMapper.findValueByCodeAndAttrId(59, dqBondCode);
        if (publisher != null) {
            //集合债，判断规则：基于34行的【发行人全称】字段识别，如字段内容中包含逗号（“,”），判断为“是”；
            String value = publisher.getValue();
            bondInfo.setColl(value.contains(","));
        }

        //Wind债券类型(二级)  85
        //ABS，判断规则：基于60行的【Wind债券类型(二级)】字段识别，如字段内容为以下之一的，判断为“是”：“交易商协会ABN”、“证监会主管ABS”、“银保监会主管ABS”、“项目收益票据”；
        EntityAttrValue abs = entityAttrValueMapper.findValueByCodeAndAttrId(85, dqBondCode);
        List<String> absList = Arrays.asList("交易商协会ABN", "证监会主管ABS", "银保监会主管ABS", "项目收益票据");
        if (abs != null) {
            String value = abs.getValue();
            bondInfo.setAbs(absList.contains(value));
        }

        //72	债券信息
        EntityAttrValue raise = entityAttrValueMapper.findValueByCodeAndAttrId(72, dqBondCode);
        if (raise != null) {
            String value = raise.getValue();
            if (Objects.equals(value, "公募")) {
                bondInfo.setRaiseType(0);
            } else if (Objects.equals(value, "私募")) {
                bondInfo.setRaiseType(1);
            }
        }

        this.saveOrUpdate(bondInfo);

        return true;
    }

    /**
     * 手动添加债券信息
     *
     * @param bondInfoManualDto
     * @return 操作成功与否信息
     * @author 正杰
     * @date 2022/10/11
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R insertBondInfoManual(BondInfoManualDto bondInfoManualDto) {
        BondInfo bondInfo = new BondInfo();

        bondInfo.setBondName(bondInfoManualDto.getBondFullName());
        bondInfo.setBondShortName(bondInfoManualDto.getBondShortName());
        bondInfo.setOriCode(bondInfoManualDto.getOriCode());
        bondInfo.setRaiseType(bondInfoManualDto.getRaiseType());
        bondInfo.setBondState(bondInfoManualDto.getBondState());
        bondInfo.setValueDate(bondInfoManualDto.getValueDate());
        bondInfo.setDueDate(bondInfoManualDto.getDueCashingDate());

        bondInfoMapper.insertBondInfo(bondInfo);

        //生成bond_info的 bondCode 长度为8位 用id拼接若干个0
        String bondCode = iEntityInfoService.appendPrefixDiy("BD", 6, bondInfo.getId().intValue());
        bondInfo.setBondCode(bondCode);

        bondInfoMapper.updateById(bondInfo);

        //债券全称 id 57
        iEntityAttrValueService.insertEntityAttrValue(new EntityAttrValue().setAttrId(57L).setEntityCode(bondCode).setValue(bondInfoManualDto.getBondFullName()));

        //债券交易代码 id 804
        iEntityAttrValueService.insertEntityAttrValue(new EntityAttrValue().setAttrId(804L).setEntityCode(bondCode).setValue(bondInfoManualDto.getOriCode()));

        //债券wind债务类型 一级 id 92
        iEntityAttrValueService.insertEntityAttrValue(new EntityAttrValue().setAttrId(92L).setEntityCode(bondCode).setValue(bondInfoManualDto.getWindTypeOne()));

        //债券wind债务类型 二级 id 85
        iEntityAttrValueService.insertEntityAttrValue(new EntityAttrValue().setAttrId(85L).setEntityCode(bondCode).setValue(bondInfoManualDto.getWindTypeTwo()));

        //起息日 id 52
        iEntityAttrValueService.insertEntityAttrValue(new EntityAttrValue().setAttrId(52L).setEntityCode(bondCode).setValue(bondInfoManualDto.getValueDate()));

        //TODO 到期兑付日 id

        //新增一条关联关系至 entity_bond_rel
        EntityBondRel entityBondRel = new EntityBondRel();
        entityBondRel.setEntityCode(bondInfoManualDto.getEntityCode());
        entityBondRel.setBdCode(bondCode);
        iEntityBondRelService.insertEntityBondRel(entityBondRel);

        return R.ok();
    }

    /**
     * 封装 BondEntityInfoVo 单个对象 => entity_attr_value
     *
     * @param attrId
     * @param code
     * @return
     */
    public BondEntityInfoVo getDataFromEntityAttrValue(Integer attrId, String code) {
        EntityAttr entityAttr = entityAttrService.getBaseMapper().selectOne(new QueryWrapper<EntityAttr>().lambda().eq(EntityAttr::getId, attrId));
        EntityAttrValue entityAttrValue = iEntityAttrValueService.getBaseMapper().selectOne(new QueryWrapper<EntityAttrValue>().lambda().eq(EntityAttrValue::getEntityCode, code).eq(EntityAttrValue::getAttrId, attrId));
        if (entityAttrValue == null) {
            //没查到值的时候
            return new BondEntityInfoVo(entityAttr.getName(), attrId.toString());
        }
        return new BondEntityInfoVo()
                .setId(entityAttrValue.getId())
                .setTable(3)
                .setEnableEdite(true)
                .setName(entityAttr.getName())
                .setFiledName(attrId.toString())
                .setValue(entityAttrValue.getValue());
    }

    /**
     * 封装 BondEntityInfoVo 多个对象
     *
     * @param entityInfo
     * @return
     */
    public List<BondEntityInfoVo> getDataFromEntityInfo(EntityInfo entityInfo, BondInfo bondInfo) {
        List<BondEntityInfoVo> result = new ArrayList<>();
        if (bondInfo == null) {
            BondEntityInfoVo temp = new BondEntityInfoVo().setTable(1).setId(entityInfo.getId());
            //债务主体德勤代码
            result.add(new BondEntityInfoVo(temp, false, "债务主体德勤代码", "", entityInfo.getEntityCode()));
            //债务主体名称
            result.add(new BondEntityInfoVo(temp, true, "债务主体名称", "entity_name", entityInfo.getEntityName()));
            //债务主体名称_原始
            result.add(new BondEntityInfoVo(temp, false, "债务主体名称_原始", "", entityInfo.getEntityNameHis()));
            //债务主体统一社会信用代码
            result.add(new BondEntityInfoVo(temp, false, "债务主体统一社会信用代码", "", entityInfo.getCreditCode()));
            return result;
        } else {
            BondEntityInfoVo temp = new BondEntityInfoVo().setTable(2).setId(bondInfo.getId().intValue());
            //是否为集合债 "1.Y":"0.N"
            Boolean coll = bondInfo.getColl();
            if (ObjectUtils.isEmpty(coll)) {
                result.add(new BondEntityInfoVo(temp, true, "是否为集合债", "coll", null));
            } else if (coll) {
                result.add(new BondEntityInfoVo(temp, true, "是否为集合债", "coll", "Y"));
            } else {
                result.add(new BondEntityInfoVo(temp, true, "是否为集合债", "coll", "N"));
            }
            //是否为ABS "1.Y":"0.N"
            Boolean abs = bondInfo.getAbs();
            if (ObjectUtils.isEmpty(abs)) {
                result.add(new BondEntityInfoVo(temp, true, "是否为ABS", "abs", null));
            } else if (abs) {
                result.add(new BondEntityInfoVo(temp, true, "是否为ABS", "abs", "Y"));
            } else {
                result.add(new BondEntityInfoVo(temp, true, "是否为ABS", "abs", "N"));
            }
            //债券交易代码
            result.add(new BondEntityInfoVo(temp, false, "债券交易代码", "", bondInfo.getOriCode()));
            //债券全称
            result.add(new BondEntityInfoVo(temp, true, "债券全称", "bond_name", bondInfo.getBondName()));
            //债券简称
            result.add(new BondEntityInfoVo(temp, true, "债券简称", "bond_short_name", bondInfo.getBondShortName()));
            //债券类型 "0.公募债券":"1.私募债"
            result.add(new BondEntityInfoVo(temp, true, "债券类型", "raise_type", bondInfo.getRaiseType() == null ? null : bondInfo.getRaiseType().toString()));
            //债务关系有效性 / 存续状态 "0.存续":"1.违约"
            result.add(new BondEntityInfoVo(temp, true, "债务关系有效性 / 存续状态", "bond_state", bondInfo.getBondState() == null ? null : bondInfo.getBondState().toString()));
            //起息日
            result.add(new BondEntityInfoVo(temp, true, "起息日", "value_date", bondInfo.getValueDate() == null ? null : bondInfo.getValueDate()).setTable(4));
            //到期日
            result.add(new BondEntityInfoVo(temp, true, "到期日", "due_date", bondInfo.getDueDate() == null ? null : bondInfo.getValueDate()).setTable(4));
            return result;
        }
    }

    public void edit(BondEntityInfoVo bondEntityInfoVo, Map<String, String> rule) {
        if (!bondEntityInfoVo.getEnableEdite()) {
            return;
        }
        Integer id = Optional.ofNullable(bondEntityInfoVo.getId()).orElseThrow(() -> new ServiceException(BadInfo.PARAM_EMPTY.getInfo()));
        String value = bondEntityInfoVo.getValue();
        String filedName = bondEntityInfoVo.getFiledName();
        Assert.isTrue(bondEntityInfoVo.getTable() == 3 || rule.containsKey(filedName), BadInfo.VALID_PARAM.getInfo());
        switch (bondEntityInfoVo.getTable()) {
            case 1:
                entityInfoMapper.editByBondInfoManager(id, filedName, value);
                break;
            case 2:
                //如果该变动字段是 是否为集合债 那么关键字为 coll Y为1 N为0
                if (Objects.equals(filedName, "coll")) {
                    value = value == "Y" ? "1" : "0";
                }
                //如果该变动字段是 是否为ABS 那么关键字为 abs Y为1 N为0
                if (Objects.equals(filedName, "abs")) {
                    value = value == "Y" ? "1" : "0";
                }
                bondInfoMapper.editByBondInfoManager(id, filedName, value);
                break;
            case 3:
                EntityAttrValue entityAttrValue = Optional.ofNullable(iEntityAttrValueService.getBaseMapper().selectOne(new QueryWrapper<EntityAttrValue>().lambda().eq(EntityAttrValue::getId, id))).orElseThrow(() -> new ServiceException(BadInfo.VALID_EMPTY_TARGET.getInfo()));
                entityAttrValue.setValue(value);
                iEntityAttrValueService.updateById(entityAttrValue);
                break;
            case 4:
                //table 4 表示 债券信息中的 日期字段
                Date date = DateUtil.parseDate(bondEntityInfoVo.getValue());
                bondInfoMapper.editByBondInfoManagerDate(id, filedName, date);
            default:
                break;
        }
    }
    /**
     * 债券退市检测跑批
     *
     * @return void
     * @author 冉浩岑
     * @date 2022/11/3 10:35
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkBondStatus() {
        //修改所有退市日期小于当前日期的正在上市的债券状态
        Date now = new Date();
        List<BondInfo> bondInfos = bondInfoMapper.selectList(new QueryWrapper<BondInfo>().lambda().eq(BondInfo::getBondStatus, 4).le(BondInfo::getDelistdate, now));
        Integer count = bondInfos.size();
        //修改条数为0则不执行后续操作
        if (count<1){
            log.info(" ===> 当日正常兑付债券数量为 "+count+" 个");
            return;
        }
        //需要修改的所有债券的code
        List<String>bondCodes=new ArrayList<>();
        bondInfos.forEach(o->{
            BondInfo bondInfo=new BondInfo();
            bondInfo.setBondStatus(9).setBondState(2).setId(o.getId());
            bondCodes.add(o.getBondCode());
            bondInfoMapper.updateById(bondInfo);
        });
        log.info(" ===> 当日正常兑付债券数量为 count=[{}]个,债券代码 codes=[{}]",count,bondCodes);
        //查询所有被修改状态对应的主体
        List<EntityBondRel>bondRels=iEntityBondRelService.selectEntityBondRelListByBondCodes(bondCodes);
        //所有被修改的所有债券对应的主体Code
        List<String>entityCodes=new ArrayList<>();
        bondRels.forEach(o->{
            if (!entityCodes.contains(o.getEntityCode())){
                entityCodes.add(o.getEntityCode());
            }
        });
        //根据主体code查询并修改债券发行状态
        //当前退市主体code
        List<String>downEntityCodes=new ArrayList<>();
        entityCodes.forEach(o->{
            //查询主体上市中的债券信息
            Integer bondUpCount=bondInfoMapper.selectListByEntityCode(o);
            //没有债券还在上市的主体，则修改主体状态
            if (bondUpCount<1){
                downEntityCodes.add(o);
                EntityInfo entityInfo = new EntityInfo();
                entityInfo.setEntityCode(o).setIssueBonds(0);
                entityInfoMapper.update(entityInfo,new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityCode,o));
            }
        });
        log.info(" ===> 当日债券主体退市数量 count=[{}]个,主体代码 codes=[{}]",downEntityCodes.size(),downEntityCodes);
    }
}
