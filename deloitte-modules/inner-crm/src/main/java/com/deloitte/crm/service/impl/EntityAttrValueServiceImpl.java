package com.deloitte.crm.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.GlobalException;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.dto.AttrValueMapDto;
import com.deloitte.crm.mapper.*;
import com.deloitte.crm.service.*;
import com.deloitte.crm.utils.AttrValueUtils;
import com.deloitte.crm.vo.EntityByIondVo;
import com.deloitte.crm.vo.EntityStockInfoVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Service
@AllArgsConstructor
public class EntityAttrValueServiceImpl extends ServiceImpl<EntityAttrValueMapper, EntityAttrValue> implements IEntityAttrValueService {
    @Resource
    private EntityAttrValueMapper entityAttrValueMapper;

    private IEntityAttrService entityAttrService;

    private ICrmSupplyTaskService iCrmSupplyTaskService;
    @Resource
    private EntityInfoMapper entityInfoMapper;
    @Resource
    private StockCnInfoMapper stockCnInfoMapper;
    @Resource
    private IBondInfoService bondInfoService;

    @Resource
    private BondInfoMapper bondInfoMapper;
    @Resource
    private EntityBondRelMapper entityBondRelMapper;
    @Resource
    private StockThkInfoMapper stockThkInfoMapper;
    @Resource
    private EntityNameHisMapper entityNameHisMapper;
    @Resource
    private EntityStockCnRelMapper entityStockCnRelMapper;
    @Resource
    private EntityStockThkRelMapper EntityStockThkRelMapper;

    private EntityAttrIntypeService entityAttrIntypeService;

    private EntityFinancialMapper entityFinancialmapper;

    private EntityInfoLogsMapper entityInfoLogsMapper;

    private EntityMasterMapper entityMasterMapper;

    private ExecutorService singleThreadPoll;

    private EntityGovRelMapper entityGovRelMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public EntityAttrValue selectEntityAttrValueById(Long id) {
        return entityAttrValueMapper.selectEntityAttrValueById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param entityAttrValue 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<EntityAttrValue> selectEntityAttrValueList(EntityAttrValue entityAttrValue) {
        return entityAttrValueMapper.selectEntityAttrValueList(entityAttrValue);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param entityAttrValue 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertEntityAttrValue(EntityAttrValue entityAttrValue) {
        return entityAttrValueMapper.insertEntityAttrValue(entityAttrValue);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param entityAttrValue 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateEntityAttrValue(EntityAttrValue entityAttrValue) {
        return entityAttrValueMapper.updateEntityAttrValue(entityAttrValue);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityAttrValueByIds(Long[] ids) {
        return entityAttrValueMapper.deleteEntityAttrValueByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityAttrValueById(Long id) {
        return entityAttrValueMapper.deleteEntityAttrValueById(id);
    }


    /**
     * 更新entityAttrValue表中债券的相关信息
     * 反射获取obj里的属性，key 为 Excel 注解 的name 属性, value 为实体类的值
     * 默认该方法异步调用， 需要同步请调用 com.deloitte.crm.service.impl.EntityAttrValueServiceImpl#updateBondAttr(java.lang.String, java.lang.Object, boolean)
     *
     * @param bondCode
     * @param obj
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBondAttr(String bondCode, Object obj) {
        return this.updateBondAttr(bondCode, obj, true);
    }

    /**
     * @param bondCode
     * @param obj
     * @param async
     * @return 正常情况返回更新的行数， async为true固定返回-1
     */
    private int updateBondAttr(String bondCode, Object obj, boolean async) {
        if (async) {
            singleThreadPoll.execute(() -> {
                this.updateAttrValue(bondCode, obj, 3, Excel.class, "name");

                bondInfoService.updateBondType(bondCode);
            });

            return -1;
        } else {
            int count = this.updateAttrValue(bondCode, obj, 3, Excel.class, "name");

            bondInfoService.updateBondType(bondCode);

            return count;
        }
    }

    /**
     * 更新entityAttrValue表中债券的相关信息
     *
     * @param entityCode entityAttrValue的entityCode
     * @param obj        反射获取属性的对象
     * @param attrType
     * @param anno
     * @param annoFiled
     * @return
     * @Param anno         anno 中的 annoFiled的值作为entityAttr的name
     * @Param annoFiled
     * @Param entityAttr - attrType
     */
    @Override
    public int updateAttrValue(String entityCode, Object obj, Integer attrType, Class<? extends Annotation> anno, String annoFiled) {
        //更新成功的条数
        int updateCount = 0;

        Map<String, Object> data = new HashMap<>();
        try {
            data = AttrValueUtils.parseObj(obj, anno, annoFiled);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
        Set<String> keySet = data.keySet();
        for (String key : keySet) {
            Object value = data.get(key);
            if (StrUtil.isBlankCast(value)) {
                continue;
            }
            EntityAttr attr = entityAttrService.findByNameType(key, attrType);
            if (attr == null) {
                continue;
            }
            Long id = attr.getId();

            EntityAttrValue attrValue = new EntityAttrValue();
            attrValue.setEntityCode(entityCode);
            attrValue.setValue(value.toString());
            attrValue.setAttrId(id);
            int upCount = this.insertUpdateCondition(attrValue);
            updateCount += upCount;
        }


        return updateCount;
    }

    /**
     * 更新entityAttrValue表中港股的相关信息
     *
     * @param stockDqCode 港股code
     * @param secIssInfo  ThkSecIssInfo 对象
     * @return
     */
    @Override
    public int updateStockThkAttr(String stockDqCode, Object secIssInfo) {
        return this.updateStockThkAttr(stockDqCode, secIssInfo, true);
    }


    /**
     * @param stockDqCode
     * @param obj
     * @param async
     * @return 正常情况返回更新的行数， async为true固定返回-1
     */
    private int updateStockThkAttr(String stockDqCode, Object obj, boolean async) {
        if (async) {
            singleThreadPoll.execute(() -> {
                this.updateAttrValue(stockDqCode, obj, 4, Excel.class, "name");
            });

            return -1;
        } else {
            return this.updateAttrValue(stockDqCode, obj, 4, Excel.class, "name");
        }
    }

    /**
     * 更新entityAttrValue表中a股的相关信息
     *
     * @param code a股德勤code
     * @param item a股相关表任意对象
     * @return
     */
    @Override
    public int updateStockCnAttr(String code, Object item) {
        return updateStockCnAttr(code, item, true);
    }

    /**
     * 更新entityAttrValue表中a股的相关信息
     *
     * @param code a股德勤code
     * @param item a股相关表任意对象
     * @return
     */
    public int updateStockCnAttr(String code, Object item, boolean async) {
        if (async) {
            singleThreadPoll.execute(() -> {
                this.updateAttrValue(code, item, 5, Excel.class, "name");
            });

            return -1;
        } else {
            return this.updateAttrValue(code, item, 5, Excel.class, "name");
        }
    }

    /**
     * ****************
     * *    通用方法   *
     * ****************
     * <p>
     * 查询 attr&attr_value 的泛用查询
     *
     * @param entityCode
     * @param attrId
     * @return
     */
    @Override
    public Map<String, AttrValueMapDto> findAttrValue(String entityCode, Integer attrId) {
        EntityAttr entityAttr = Optional.ofNullable(entityAttrService.getBaseMapper().selectOne(new QueryWrapper<EntityAttr>().lambda().eq(EntityAttr::getId, attrId))).orElseThrow(() -> new ServiceException(BadInfo.VALID_EMPTY_TARGET.getInfo()));

        HashMap<String, AttrValueMapDto> res = new HashMap<>();
        if (entityAttr.getMultiple()) {
            List<EntityAttrIntype> entityAttrIntypes = entityAttrIntypeService.getBaseMapper().selectList(new QueryWrapper<EntityAttrIntype>().lambda().eq(EntityAttrIntype::getAttrId, attrId));
            List<EntityAttrValue> valueList = baseMapper.selectList(new QueryWrapper<EntityAttrValue>().lambda().eq(EntityAttrValue::getEntityCode, entityCode));
            List<EntityAttrIntype> targetList = entityAttrIntypes.stream().filter(item -> valueList.stream().collect(Collectors.toMap(EntityAttrValue::getId, row -> row)).containsKey(item.getId())).collect(Collectors.toList());

            AttrValueMapDto temp = new AttrValueMapDto().setAttrId(attrId).setName(entityAttr.getName()).setRemarks(entityAttr.getRemarks());
            List<AttrValueMapDto> tempList = targetList.stream().map(row -> temp.setValueId(row.getId()).setValue(row.getValue())).collect(Collectors.toList());
            Map<Integer, AttrValueMapDto> tempMap = tempList.stream().collect(Collectors.toMap(AttrValueMapDto::getValueId, row -> row));
            ArrayList<AttrValueMapDto> result = new ArrayList<>();
            entityAttrIntypes.forEach(row -> {
                if (row.getPId() != null) {
                    tempMap.get(row.getPId()).getChildren().add(tempMap.get(row.getId()));
                } else {
                    result.add(tempMap.get(row.getId()));
                }
            });
            res.put(temp.getName(), temp.setChildren(result));
        } else {
            EntityAttrValue entityAttrValue = baseMapper.selectOne(new QueryWrapper<EntityAttrValue>().lambda().eq(EntityAttrValue::getAttrId, attrId).eq(EntityAttrValue::getEntityCode, entityCode));
            AttrValueMapDto attrValueMapDto = new AttrValueMapDto(attrId, entityAttr.getName(), entityAttr.getRemarks(), entityAttrValue == null ? null : entityAttrValue.getId(), entityAttrValue == null ? null : entityAttrValue.getValue());
            res.put(entityAttr.getName(), attrValueMapDto);
        }
        return res;
    }

    /**
     * ****************
     * *    通用方法   *
     * ****************
     * <p>
     * 修改或新增 attr_value
     *
     * @param attrValueMapDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveAttrValue(String entityCode, AttrValueMapDto attrValueMapDto) {
        Integer valueId = attrValueMapDto.getValueId();
        Integer attrId = attrValueMapDto.getAttrId();
        final EntityAttr entityAttr = Optional.ofNullable(entityAttrService.getBaseMapper().selectOne(new QueryWrapper<EntityAttr>().lambda().eq(EntityAttr::getId, attrId))).orElseThrow(() -> new ServiceException(BadInfo.VALID_EMPTY_TARGET.getInfo()));
        if (valueId == null) {
            if (entityAttr.getMultiple()) {
                List<AttrValueMapDto> targetList = new ArrayList<>();
                targetList.addAll(this.getAllChildrenId(attrValueMapDto, targetList));
                targetList.forEach(row -> {
                    baseMapper.insertEntityAttrValue(new EntityAttrValue()
                            .setEntityCode(entityCode)
                            .setAttrId(row.getAttrId().longValue())
                            .setValue(row.getValueId().toString()));
                });
                return true;
            } else {
                baseMapper.insertEntityAttrValue(new EntityAttrValue()
                        .setEntityCode(entityCode)
                        .setAttrId(attrValueMapDto.getAttrId().longValue())
                        .setValue(attrValueMapDto.getValueId().toString()));
            }
            return true;
        } else {
            if (entityAttr.getMultiple()) {
                List<AttrValueMapDto> targetList = new ArrayList<>();
                targetList.addAll(this.getAllChildrenId(attrValueMapDto, targetList));
                targetList.forEach(row -> {
                    EntityAttrValue entityAttrValue = Optional.ofNullable(baseMapper.selectById(row.getValueId())).orElseThrow(() -> new ServiceException(BadInfo.VALID_EMPTY_TARGET.getInfo()));
                    entityAttrValue.setValue(row.getValueId().toString());
                    baseMapper.updateById(entityAttrValue);
                });
                return true;
            } else {
                EntityAttrValue entityAttrValue = baseMapper.selectOne(new QueryWrapper<EntityAttrValue>().lambda().eq(EntityAttrValue::getId, valueId));
                entityAttrValue.setValue(attrValueMapDto.getValue());
                baseMapper.updateById(entityAttrValue);
                return true;
            }
        }
    }

    /**
     * 批量导入附表数据至 entity_attr_value by 正杰
     *
     * @param entityCode
     * @param attrId
     * @param value
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void runBatchToAttrValue(String entityCode, Integer attrId, String value) {
        baseMapper.insertEntityAttrValue(new EntityAttrValue().setEntityCode(entityCode).setAttrId(attrId.longValue()).setValue(value));
    }

    public List<AttrValueMapDto> getAllChildrenId(AttrValueMapDto attrValueMapDto, List<AttrValueMapDto> list) {
        List<AttrValueMapDto> children = attrValueMapDto.getChildren();
        if (children.size() != 0) {
            for (AttrValueMapDto child : children) {
                list.add(attrValueMapDto);
                list.addAll(this.getAllChildrenId(child, list));
                return list;
            }
        } else {
            list.add(attrValueMapDto);
            return list;
        }
        return list;
    }

    //根据 key 名称，获取 attrId
    private Long getAttrId(String key) {
        Long id = 0L;
        switch (key) {
            //统一
            case "wind行业划分":
                id = 652L;
                break;
            case "申万行业划分":
                id = 650L;
                break;
            //金融机构表
            case "所属地区":
                id = 983L;
                break;
            case "所属辖区":
                id = 658L;
                break;
            case "对口监管机构":
                id = 657L;
                break;
            //城投机构表
            case "城府持股方式":
                id = 676L;
                break;
            case "政府对当前城投支持力度":
                id = 677L;
                break;
            case "政府对当前城投支持力度判断依据":
                id = 981L;
                break;
            case "政府部门实际持股比例":
                id = 678L;
                break;
            case "政府部门实际持股比例-年份":
                id = 982L;
                break;
            //财报收数
            case "财报列示类型":
                id = 855L;
                break;
            case "关注报告类":
                id = 854L;
                break;
            default:
                id = 0L;
        }
        return id;
    }

    /**
     * 新增或修改
     *
     * @return
     */
    @Override
    public int insertUpdateCondition(EntityAttrValue attrValue) {
        String value = attrValue.getValue();

        EntityAttrValue dbAttrValue = entityAttrValueMapper.findByAttrCode(attrValue);

        if (dbAttrValue == null) {
            return entityAttrValueMapper.insert(attrValue);
        }

        dbAttrValue.setValue(value);

        return entityAttrValueMapper.updateById(dbAttrValue);
    }


    private final String IB = "IB";

    private final String ZERO = "0";

    private final Integer CODE_NUMBER = 6;

    /**
     * 新增债券主体(社会信用代码和债券简称重复不能进行新增)
     *
     * @param entityByIondVo
     * @return R
     * @author penTang
     * @date 2022/9/27 20:40
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public R createBondEntity(EntityByIondVo entityByIondVo) {
        //creditCode和bondShortName进行查重操作
        if (entityByIondVo.getCreditCode() != null) {
            EntityInfo entityInfo1 = entityInfoMapper.selectOne(new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getCreditCode, entityByIondVo.getCreditCode()));
            if (!ObjectUtils.isEmpty(entityInfo1)) {
                return R.fail("社会信用代码或债券简称重复不能进行新增");
            }
        }

        if (entityByIondVo.getCreditCode() == null && entityByIondVo.getCreditError() == 0) {
            return R.fail("社会信用代码不能为空");
        }
        BondInfo byShortName = bondInfoMapper.findByShortName(entityByIondVo.getBondShortName(), Boolean.FALSE);
        if (!ObjectUtils.isEmpty(byShortName)) {
            return R.fail("债券简称重复不能进行新增");
        }

        //新增bond_info
        BondInfo bondInfo = new BondInfo();
        EntityInfo entityInfo = new EntityInfo();
        bondInfo.setOriCode(entityByIondVo.getStockCode());
        bondInfo.setBondShortName(entityByIondVo.getBondShortName());
        bondInfo.setBondName(entityByIondVo.getBondName());
        bondInfo.setValueDate(entityByIondVo.getStartXiDate());
        bondInfo.setDueDate(entityByIondVo.getEndDate());
        bondInfo.setRaiseType(entityByIondVo.getBondType());
        if(Objects.equals(entityByIondVo.getIsDefaultOrRoll(),0)){
            //起息日
            DateTime StartDateTime = DateUtil.parseDate(entityByIondVo.getStartXiDate());
            //到息日
            DateTime endDateTime = DateUtil.parseDate(entityByIondVo.getStartXiDate());
            Date newDate = new Date();
            if(StartDateTime.getTime() <= newDate.getTime() && endDateTime.getTime() > newDate.getTime()){
                bondInfo.setBondStatus(4);
                entityInfo.setIssueBonds(1);
                entityInfo.setEntityBondTag("1");
            } else if(StartDateTime.getTime() > newDate.getTime()) {
                bondInfo.setBondStatus(2);
                entityInfo.setIssueBonds(1);
                entityInfo.setEntityBondTag("1");
            } else if (endDateTime.getTime() <= newDate.getTime()){
                bondInfo.setBondStatus(9);
                entityInfo.setIssueBonds(1);
                entityInfo.setEntityBondTag("1");
            }
        }else {
            bondInfo.setBondStatus(7);
            bondInfo.setDefaultDate(entityByIondVo.getDefaultDate());
            entityInfo.setIssueBonds(0);
            entityInfo.setEntityBondTag("1");
        }
        bondInfoMapper.insertBondInfo(bondInfo);
        DecimalFormat g1 = new DecimalFormat("000000");
        String startZeroStr = g1.format(bondInfo.getId());
        bondInfo.setBondCode("BD" + startZeroStr);
        bondInfoMapper.updateById(bondInfo);

        //新增entity_info
        entityInfo.setCreditCode(entityByIondVo.getCreditCode());
        entityInfo.setEntityName(entityByIondVo.getEntityName());
        entityInfo.setListType(entityByIondVo.getAnRportType());
        if (Objects.equals(entityByIondVo.getCreditError(), "0")) {
            entityInfo.setCreditError(0);
        }
        entityInfo.setCreditError(1);
        entityInfo.setCreditErrorRemark(entityByIondVo.getCreditErrorRemark());
        entityInfo.setFinance(entityByIondVo.getFinance());
        //曾用名
        entityInfo.setEntityNameHis(entityByIondVo.getEntityNameHis());
        //创建人
        entityInfo.setCreater(SecurityUtils.getUsername());
        entityInfoMapper.insert(entityInfo);
        //根据主键id生成code
        Integer id = entityInfo.getId();
        StringBuilder sb = new StringBuilder(IB);
        for (int j = 0; j < CODE_NUMBER - String.valueOf(id).length(); j++) {
            sb.append(ZERO);
        }
        entityInfo.setEntityCode(sb.toString() + id);

        UpdateWrapper<EntityInfo> wrapper = new UpdateWrapper<>();
        wrapper.lambda()
                .eq(EntityInfo::getId, id)
                .set(EntityInfo::getEntityCode, entityInfo.getEntityCode());
        entityInfoMapper.update(entityInfo, wrapper);

        // 新增 entity_name_his
        EntityNameHis entityNameHis = new EntityNameHis();
        entityNameHis.setEntityType(1);
        entityNameHis.setDqCode(entityInfo.getEntityCode());
        entityNameHis.setOldName(entityInfo.getEntityNameHis());
        entityNameHis.setSource(3);
        entityNameHis.setHappenDate(new Date());
        entityNameHis.setCreater(SecurityUtils.getUsername());
        entityNameHisMapper.insertEntityNameHis(entityNameHis);
        //新增关联关系entity_bond_rel
        EntityBondRel entityBondRel = new EntityBondRel();
        entityBondRel.setBdCode("BD" + startZeroStr);

        entityBondRel.setEntityCode(entityInfo.getEntityCode());
        entityBondRelMapper.insertEntityBondRel(entityBondRel);
        //新增entityfinancal
        EntityFinancial entityFinancial = new EntityFinancial();
        entityFinancial.setEntityCode(sb.toString() + id);
        entityFinancial.setMince(entityByIondVo.getFinanceSubIndu());
        entityFinancialmapper.insert(entityFinancial);
        if(Objects.equals(entityByIondVo.getIsInvestment(),1)){
            EntityGovRel entityGovRel = new EntityGovRel();
            entityGovRel.setEntityCode(entityInfo.getEntityCode());
            entityGovRel.setDqGovCode(entityByIondVo.getDqGovCode());
            entityGovRelMapper.insert(entityGovRel);

        }
        //日志入库
        EntityInfoLogs entityInfoLogs = new EntityInfoLogs();
        entityInfoLogs.setEntityCode(sb.toString() + id);
        entityInfoLogs.setDeCode("BD" + startZeroStr);
        entityInfoLogs.setCode(entityByIondVo.getStockCode());
        entityInfoLogs.setOperType(3);
        entityInfoLogs.setEntityName(entityByIondVo.getEntityName());
        entityInfoLogs.setOperName(SecurityUtils.getUsername());
        entityInfoLogs.setSource(1);
        entityInfoLogsMapper.insert(entityInfoLogs);
        return R.ok("新增成功");
    }

    /**
     * A股新增
     *
     * @param entityStockInfoVo
     * @return R
     * @author penTang
     * @date 2022/9/29 9:59
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R createStockEntity(EntityStockInfoVo entityStockInfoVo) {
        if (entityStockInfoVo.getCreditCode() != null) {
            EntityInfo entityInfo = entityInfoMapper.selectOne(new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getCreditCode, entityStockInfoVo.getCreditCode()));
            if (!ObjectUtils.isEmpty(entityInfo)) {
                return R.fail("新增失败：统一社会性代码重复");
            }

        }
        if (entityStockInfoVo.getCreditCode() == null && entityStockInfoVo.getCreditError() == 0) {
            return R.fail("社会信用代码不能为空");
        }

        StockCnInfo stockSrotName = stockCnInfoMapper.selectOne(new LambdaQueryWrapper<StockCnInfo>().eq(StockCnInfo::getStockShortName, entityStockInfoVo.getStockShortName())
                .eq(StockCnInfo::getIsDeleted, Boolean.FALSE)
        );
        StockCnInfo stockCode = stockCnInfoMapper.selectOne(new LambdaQueryWrapper<StockCnInfo>().eq(StockCnInfo::getStockCode, entityStockInfoVo.getStockCode())
                .eq(StockCnInfo::getIsDeleted, Boolean.FALSE)
        );
        if (!ObjectUtils.isEmpty(stockSrotName) || !ObjectUtils.isEmpty(stockCode)) {
            return R.fail("新增失败:股票简称或股票代码重复不能进行新增");
        }

        //新增Stock_cn_info
        StockCnInfo stockCnInfo = new StockCnInfo();
        EntityInfo entityInfoBystock = new EntityInfo();
        stockCnInfo.setStockShortName(entityStockInfoVo.getStockShortName());
        stockCnInfo.setStockCode(entityStockInfoVo.getStockCode());
        stockCnInfo.setListDate(entityStockInfoVo.getStartXiDate());
        if (!entityStockInfoVo.getEndDate().equals("")){
            stockCnInfo.setDelistingDate(entityStockInfoVo.getEndDate());
        }
        stockCnInfo.setExchange(entityStockInfoVo.getExchange());
        //上市时间
        DateTime StartDateTime = DateUtil.parseDate(entityStockInfoVo.getStartXiDate());
        //退市时间
        DateTime endDateTime = DateUtil.parseDate(entityStockInfoVo.getEndDate());
        Date newDate = new Date();
        if (endDateTime == null) {
            if (StartDateTime.getTime() <= newDate.getTime()){
                stockCnInfo.setStockStatus(6);
                entityInfoBystock.setList(1);
                entityInfoBystock.setEntityStockTag("1");
            }else {
                stockCnInfo.setStockStatus(5);
                entityInfoBystock.setList(0);
                entityInfoBystock.setEntityStockTag("0");
            }
        }else {
            if(StartDateTime.getTime() <= newDate.getTime() && endDateTime.getTime() > newDate.getTime()){
                //成功上市
                stockCnInfo.setStockStatus(6);
                entityInfoBystock.setList(1);
                entityInfoBystock.setEntityStockTag("1");
            } else if(endDateTime.getTime() <= newDate.getTime()) {
                //退市
                stockCnInfo.setStockStatus(9);
                entityInfoBystock.setList(0);
                entityInfoBystock.setEntityStockTag("1");

            } else if(StartDateTime.getTime() > newDate.getTime()){
                //发行中
                stockCnInfo.setStockStatus(5);
                entityInfoBystock.setList(0);
                entityInfoBystock.setEntityStockTag("0");
            }
        }

        int insert = stockCnInfoMapper.insert(stockCnInfo);
        DecimalFormat g1 = new DecimalFormat("000000");
        String startZeroStr = g1.format(stockCnInfo.getId());
        stockCnInfo.setStockDqCode("SA" + startZeroStr);
        LambdaUpdateWrapper<StockCnInfo> Wrapper = new LambdaUpdateWrapper<>();
        Wrapper.eq(StockCnInfo::getId, stockCnInfo.getId())
                .set(StockCnInfo::getStockDqCode, stockCnInfo.getStockDqCode());
        stockCnInfoMapper.update(stockCnInfo, Wrapper);


        //新增entity_info
        entityInfoBystock.setCreditCode(entityStockInfoVo.getCreditCode());
        entityInfoBystock.setEntityName(entityStockInfoVo.getEntityName());
        if (!ObjectUtils.isEmpty(entityStockInfoVo.getCreditError())) {
            entityInfoBystock.setCreditError(0);
        }
        //曾用名
        entityInfoBystock.setEntityNameHis(entityStockInfoVo.getEntityNameHis());
        entityInfoBystock.setListType(entityStockInfoVo.getAnRportType());
        //创建人
        entityInfoBystock.setCreater(SecurityUtils.getUsername());
        entityInfoBystock.setCreditError(1);
        entityInfoBystock.setCreditErrorRemark(entityStockInfoVo.getCreditErrorRemark());
        entityInfoBystock.setFinance(entityStockInfoVo.getFinance());
        entityInfoMapper.insert(entityInfoBystock);
        //根据主键id生成code
        Integer id = entityInfoBystock.getId();
        StringBuilder sb = new StringBuilder(IB);
        for (int j = 0; j < CODE_NUMBER - String.valueOf(id).length(); j++) {
            sb.append(ZERO);
        }
        entityInfoBystock.setEntityCode(sb.toString() + id);

        UpdateWrapper<EntityInfo> wrapper = new UpdateWrapper<>();
        wrapper.lambda()
                .eq(EntityInfo::getId, id)
                .set(EntityInfo::getEntityCode, entityInfoBystock.getEntityCode());
        entityInfoMapper.update(entityInfoBystock, wrapper);
        //新增 entity_name_his
        EntityNameHis entityNameHis = new EntityNameHis();
        entityNameHis.setEntityType(1);
        entityNameHis.setDqCode(entityInfoBystock.getEntityCode());
        entityNameHis.setOldName(entityInfoBystock.getEntityNameHis());
        entityNameHis.setSource(3);
        entityNameHis.setHappenDate(new Date());
        entityNameHis.setCreater(SecurityUtils.getUsername());
        entityNameHisMapper.insertEntityNameHis(entityNameHis);
        //新增关联关系entity_stock_cn_rel
        EntityStockCnRel entityStockCnRel = new EntityStockCnRel();
        entityStockCnRel.setStockDqCode("SA" + startZeroStr);
        entityStockCnRel.setEntityCode(entityInfoBystock.getEntityCode());
        entityStockCnRelMapper.insert(entityStockCnRel);
        //新增entityfinancal
        EntityFinancial entityFinancial = new EntityFinancial();
        entityFinancial.setEntityCode(sb.toString() + id);
        entityFinancial.setMince(entityStockInfoVo.getFinanceSubIndu());
        entityFinancialmapper.insert(entityFinancial);
        //新增masterCode
        EntityMaster entityMaster = new EntityMaster();
        entityMaster.setEntityCode(sb.toString() + id);
        entityMaster.setMasterCode(entityStockInfoVo.getMasterCode());
        entityMasterMapper.insert(entityMaster);
        //日志入库
        EntityInfoLogs entityInfoLogs = new EntityInfoLogs();
        entityInfoLogs.setEntityCode(sb.toString() + id);
        entityInfoLogs.setDeCode("SA" + startZeroStr);
        entityInfoLogs.setCode(entityStockInfoVo.getStockCode());
        entityInfoLogs.setOperType(1);
        entityInfoLogs.setEntityName(entityStockInfoVo.getEntityName());
        entityInfoLogs.setOperName(SecurityUtils.getUsername());
        entityInfoLogs.setCreateTime(DateUtil.getNowDate());
        entityInfoLogs.setSource(2);
        entityInfoLogsMapper.insert(entityInfoLogs);
        return R.ok("新增成功");
    }

    /**
     * 港股新增
     *
     * @param entityStockInfoVo
     * @return R
     * @author penTang
     * @date 2022/9/29 9:59
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R createStockEntityG(EntityStockInfoVo entityStockInfoVo) {
        EntityInfo entityInfo = entityInfoMapper.selectOne(new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getCreditCode, entityStockInfoVo.getCreditCode()));
        StockThkInfo stockThkCode = stockThkInfoMapper.selectOne(new LambdaQueryWrapper<StockThkInfo>().eq(StockThkInfo::getStockCode, entityStockInfoVo.getStockCode()));
        if (!ObjectUtils.isEmpty(entityInfo) || !ObjectUtils.isEmpty(stockThkCode)) {
            return R.fail("新增失败：社会信用代码或股票代码重复不能进行新增");
        }

        // 新增 Stock_Thk_Info
        StockThkInfo stockThkInfo = new StockThkInfo();
        EntityInfo entityInfoBystockHk = new EntityInfo();
        stockThkInfo.setStockCode(entityStockInfoVo.getStockCode());

        //上市时间
        DateTime StartDateTime = DateUtil.parseDate(entityStockInfoVo.getStartXiDate());
        //退市时间
        DateTime endDateTime = DateUtil.parseDate(entityStockInfoVo.getEndDate());
        Date newDate = new Date();
        if (endDateTime == null) {
            if (StartDateTime.getTime() <= newDate.getTime()){
                stockThkInfo.setStockStatus(6);
                entityInfoBystockHk.setList(1);
                entityInfoBystockHk.setEntityStockTag("1");
            }else {
                stockThkInfo.setStockStatus(5);
                entityInfoBystockHk.setList(0);
                entityInfoBystockHk.setEntityStockTag("0");
            }
        }else
        {
            if(StartDateTime.getTime() <= newDate.getTime() && endDateTime.getTime() > newDate.getTime()){
                //成功上市
                stockThkInfo.setStockStatus(6);
                entityInfoBystockHk.setList(1);
                entityInfoBystockHk.setEntityStockTag("1");
            } else if(endDateTime.getTime() <= newDate.getTime()) {
                //退市
                stockThkInfo.setStockStatus(9);
                entityInfoBystockHk.setList(0);
                entityInfoBystockHk.setEntityStockTag("1");

            } else if(StartDateTime.getTime() > newDate.getTime()){
                //发行中
                stockThkInfo.setStockStatus(5);
                entityInfoBystockHk.setList(0);
                entityInfoBystockHk.setEntityStockTag("0");
            }

        }

        int insert = stockThkInfoMapper.insert(stockThkInfo);
        DecimalFormat g1 = new DecimalFormat("000000");
        String startZeroStr = g1.format(stockThkInfo.getId());
        stockThkInfo.setStockDqCode("HK" + startZeroStr);
        LambdaUpdateWrapper<StockThkInfo> Wrapper = new LambdaUpdateWrapper<>();
        Wrapper.eq(StockThkInfo::getId, stockThkInfo.getId())
                .set(StockThkInfo::getStockDqCode, stockThkInfo.getStockDqCode());
        //上市时间
        stockThkInfo.setListDate(entityStockInfoVo.getStartXiDate());
        //退市时间
        if (!entityStockInfoVo.getEndDate().equals("")){
            stockThkInfo.setDelistingDate(entityStockInfoVo.getEndDate());
        }
        //交易所
        stockThkInfo.setExchange(entityStockInfoVo.getExchange());
        stockThkInfoMapper.update(stockThkInfo, Wrapper);
        //新增entity_info

        entityInfoBystockHk.setCreditCode(entityStockInfoVo.getCreditCode());
        entityInfoBystockHk.setEntityName(entityStockInfoVo.getEntityName());

        if (!ObjectUtils.isEmpty(entityStockInfoVo.getCreditError())) {
            entityInfoBystockHk.setCreditError(0);
        }
        entityInfoBystockHk.setCreditError(1);
        entityInfoBystockHk.setCreditErrorRemark(entityStockInfoVo.getCreditErrorRemark());
        entityInfoBystockHk.setFinance(entityStockInfoVo.getFinance());
        //年报示例类型*
        entityInfoBystockHk.setListType(entityStockInfoVo.getAnRportType());
        //曾用名
        entityInfoBystockHk.setEntityNameHis(entityStockInfoVo.getEntityNameHis());
        //创建人
        entityInfoBystockHk.setCreater(SecurityUtils.getUsername());
        entityInfoMapper.insert(entityInfoBystockHk);
        //根据主键id生成code
        Integer id = entityInfoBystockHk.getId();
        StringBuilder sb = new StringBuilder(IB);
        for (int j = 0; j < CODE_NUMBER - String.valueOf(id).length(); j++) {
            sb.append(ZERO);
        }
        entityInfoBystockHk.setEntityCode(sb.toString() + id);

        UpdateWrapper<EntityInfo> wrapper = new UpdateWrapper<>();
        wrapper.lambda()
                .eq(EntityInfo::getId, id)
                .set(EntityInfo::getEntityCode, entityInfoBystockHk.getEntityCode());
        entityInfoMapper.update(entityInfoBystockHk, wrapper);
        // 新增 entity_name_his
        EntityNameHis entityNameHis = new EntityNameHis();
        entityNameHis.setEntityType(1);
        entityNameHis.setDqCode(entityInfoBystockHk.getEntityCode());
        entityNameHis.setOldName(entityInfoBystockHk.getEntityNameHis());
        entityNameHis.setSource(3);
        entityNameHis.setHappenDate(new Date());
        entityNameHis.setCreater(SecurityUtils.getUsername());
        entityNameHisMapper.insertEntityNameHis(entityNameHis);
        //新增关联关系entity_stock_thk_rel
        EntityStockThkRel entityStockThkRel = new EntityStockThkRel();
        entityStockThkRel.setStockDqCode("HK" + startZeroStr);
        entityStockThkRel.setEntityCode(entityInfoBystockHk.getEntityCode());
        EntityStockThkRelMapper.insert(entityStockThkRel);
        //金融机构子行业
        EntityFinancial entityFinancial = new EntityFinancial();
        entityFinancial.setEntityCode(sb.toString() + id);
        entityFinancial.setMince(entityStockInfoVo.getFinanceSubIndu());
        entityFinancialmapper.insert(entityFinancial);
        //新增masterCode
        EntityMaster entityMaster = new EntityMaster();
        entityMaster.setEntityCode(sb.toString() + id);
        entityMaster.setMasterCode(entityStockInfoVo.getMasterCode());
        entityMasterMapper.insert(entityMaster);
        //日志入库
        EntityInfoLogs entityInfoLogs = new EntityInfoLogs();
        entityInfoLogs.setEntityCode(sb.toString() + id);
        entityInfoLogs.setDeCode("HK" + startZeroStr);
        entityInfoLogs.setCode(entityStockInfoVo.getStockCode());
        entityInfoLogs.setOperType(2);
        entityInfoLogs.setEntityName(entityStockInfoVo.getEntityName());
        entityInfoLogs.setOperName(SecurityUtils.getUsername());
        entityInfoLogs.setCreateTime(DateUtil.getNowDate());
        entityInfoLogs.setSource(2);
        entityInfoLogsMapper.insert(entityInfoLogs);
        return R.ok("新增成功");
    }


}
