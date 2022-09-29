package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.lang.annotation.Annotation;
import java.text.DecimalFormat;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.annotation.Excel;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.GlobalException;
import com.deloitte.common.core.utils.StrUtil;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.domain.EntityAttr;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.mapper.EntityAttrValueMapper;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.mapper.*;
import com.deloitte.crm.service.IEntityAttrService;
import com.deloitte.crm.service.IEntityAttrValueService;
import com.deloitte.crm.utils.AttrValueUtils;
import com.deloitte.crm.vo.EntityByIondVo;
import com.deloitte.crm.vo.EntityStockInfoVo;
import org.springframework.stereotype.Service;
import com.deloitte.crm.service.IEntityAttrValueService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Service
public class EntityAttrValueServiceImpl extends ServiceImpl<EntityAttrValueMapper, EntityAttrValue> implements IEntityAttrValueService {


    @Resource
    private EntityAttrValueMapper entityAttrValueMapper;

    @Resource
    private IEntityAttrService entityAttrService;

    @Resource
    private EntityInfoMapper entityInfoMapper;
    @Resource
    private StockCnInfoMapper stockCnInfoMapper;

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
    private  EntityStockThkRelMapper EntityStockThkRelMapper;

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
     *
     * @param bondCode
     * @param obj
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBondAttr(String bondCode, Object obj) {
        return this.updateAttrValue(bondCode, obj, 3, Excel.class, "name");
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
        return this.updateAttrValue(stockDqCode, secIssInfo, 4, Excel.class, "name");
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
        return this.updateAttrValue(code, item, 5, Excel.class, "name");
    }

    @Override
    public Integer addEntityAttrValues(List<EntityAttrValue> valueList) {
        valueList.stream().forEach(o -> {
            QueryWrapper<EntityAttrValue> query = new QueryWrapper<>();
            String attrId = o.getAttrId().toString();
            if (attrId.equals(Common.WHETHER_ATTR_NAME_SW_ATTR_CATE_ID.toString())
                    || attrId.equals(Common.WWHETHER_ATTR_NAME_WIND_ATTR_CATE_ID.toString())) {
                entityAttrValueMapper.update(o, query.lambda().eq(EntityAttrValue::getEntityCode, o.getEntityCode()));
            } else {
                entityAttrValueMapper.insert(o);
            }
        });
        return valueList.size();
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
     *新增债券主体(社会信用代码和债券简称重复不能进行新增)
     *
     * @param entityByIondVo
     * @return R
     * @author penTang
     * @date 2022/9/27 20:40
     */
    @Transactional
    @Override
    public R createBondEntity(EntityByIondVo entityByIondVo){
      //creditCode和bondShortName进行查重操作
        EntityInfo entityInfo1 = entityInfoMapper.selectOne(new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getCreditCode, entityByIondVo.getCreditCode()));
        BondInfo byShortName = bondInfoMapper.findByShortName(entityByIondVo.getBondShortName());

        if (!ObjectUtils.isEmpty(byShortName) || !ObjectUtils.isEmpty(entityInfo1)) {
            return R.fail("新增失败：社会信用代码或债券简称重复不能进行新增");
        }
        //新增entity_info
        EntityInfo entityInfo = new EntityInfo();
        entityInfo.setCreditCode(entityByIondVo.getCreditCode());
        entityInfo.setEntityName(entityByIondVo.getEntityName());
        if (Objects.equals(entityByIondVo.getCreditError(),"0")) {
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
        //新增bond_info
        BondInfo bondInfo = new BondInfo();
        DecimalFormat g1=new DecimalFormat("000000");
        String startZeroStr = g1.format(bondInfo.getId());
        bondInfo.setBondCode("BD"+startZeroStr);
        bondInfo.setBondShortName(entityByIondVo.getBondShortName());
        bondInfoMapper.insertBondInfo(bondInfo);
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
        entityBondRel.setBdCode("BD"+startZeroStr);
        entityBondRel.setEntityCode(entityInfo.getEntityCode());
        entityBondRelMapper.insertEntityBondRel(entityBondRel);
        //新增entity_attr_value
        ArrayList<EntityAttrValue> entityAttrValues = new ArrayList<EntityAttrValue>();
        EntityAttrValue entityAttrValueStockCode = new EntityAttrValue();
        //TODO 债券全称
        entityAttrValueStockCode.setEntityCode("BD"+startZeroStr);
        entityAttrValueStockCode.setAttrId(Common.BOND_NAME_ID.longValue());
        entityAttrValueStockCode.setValue(entityByIondVo.getBondName());
        entityAttrValues.add(entityAttrValueStockCode);
        //TODO 起息日
        EntityAttrValue entityAttrValueStartDate = new EntityAttrValue();
        entityAttrValueStartDate.setAttrId(Common.START_XI_DATE_ID.longValue());
        entityAttrValueStartDate.setEntityCode("BD"+startZeroStr);
        entityAttrValueStartDate.setValue(entityByIondVo.getStartXiDate());
        entityAttrValues.add(entityAttrValueStartDate);
        //TODO 到期日
        EntityAttrValue entityAttrValueEndDate = new EntityAttrValue();
        entityAttrValueEndDate.setAttrId(Common.STRING_END_DATE_ID.longValue());
        entityAttrValueEndDate.setEntityCode("BD"+startZeroStr);
        entityAttrValueEndDate.setValue(entityByIondVo.getEndDate());
        entityAttrValues.add(entityAttrValueEndDate);
        //TODO 债券类型
        EntityAttrValue entityAttrValueBondType = new EntityAttrValue();
        entityAttrValueBondType.setAttrId(Common.BOND_TYPE_ID.longValue());
        entityAttrValueBondType.setEntityCode("BD"+startZeroStr);
        entityAttrValueBondType.setValue(entityByIondVo.getBondType());
        entityAttrValues.add(entityAttrValueBondType);
        //TODO 年报类型
        EntityAttrValue entityAttrValueReportType = new EntityAttrValue();
        entityAttrValueReportType.setAttrId(Common.AN_RPORT_TYPE.longValue());
        entityAttrValueReportType.setEntityCode("BD"+startZeroStr);
        entityAttrValueReportType.setValue(entityByIondVo.getAnRportType());
        entityAttrValues.add(entityAttrValueReportType);
        //TODO 金融机构子行业
        EntityAttrValue financeSubIndu = new EntityAttrValue();
        financeSubIndu.setAttrId(Common.FINANCE_SUB_INDU_ID.longValue());
        financeSubIndu.setEntityCode("BD"+startZeroStr);
        financeSubIndu.setValue(entityByIondVo.getFinanceSubIndu());
        entityAttrValues.add(financeSubIndu);
        // 债券代码
        EntityAttrValue entityAttrValueBondCode = new EntityAttrValue();
        entityAttrValueBondCode.setAttrId(Common.STOCK_CODE_ID.longValue());
        entityAttrValueBondCode.setEntityCode("BD"+startZeroStr);
        entityAttrValueBondCode.setValue(entityByIondVo.getStockCode());
        entityAttrValues.add(entityAttrValueBondCode);
        //入库
        saveBatch(entityAttrValues);
        entityAttrValues.clear();
        return R.ok("新增成功");
    }
    /**
     *A股新增
     *
     * @param entityStockInfoVo
     * @return R
     * @author penTang
     * @date 2022/9/29 9:59
    */
    @Override
    public R createStockEntity(EntityStockInfoVo entityStockInfoVo){
        EntityInfo entityInfo = entityInfoMapper.selectOne(new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getCreditCode, entityStockInfoVo.getCreditCode()));
        StockCnInfo stockSrotName = stockCnInfoMapper.selectOne(new LambdaQueryWrapper<StockCnInfo>().eq(StockCnInfo::getStockShortName, entityStockInfoVo.getStockShortName()));
        StockCnInfo stockCode = stockCnInfoMapper.selectOne(new LambdaQueryWrapper<StockCnInfo>().eq(StockCnInfo::getStockCode, entityStockInfoVo.getStockCode()));
        if (!ObjectUtils.isEmpty(entityInfo) || !ObjectUtils.isEmpty(stockSrotName) || !ObjectUtils.isEmpty(stockCode)){
            return R.fail("新增失败：社会信用代码或股票简称或股票代码重复不能进行新增");
        }

        //新增entity_info
        EntityInfo entityInfoBystock = new EntityInfo();
        entityInfoBystock.setCreditCode(entityStockInfoVo.getCreditCode());
        entityInfoBystock.setEntityName(entityStockInfoVo.getEntityName());
        if (!ObjectUtils.isEmpty(entityStockInfoVo.getCreditError())) {
            entityInfoBystock.setCreditError(0);
        }
        //曾用名
        entityInfo.setEntityNameHis(entityStockInfoVo.getEntityNameHis());
        //创建人
        entityInfo.setCreater(SecurityUtils.getUsername());
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

        // 新增 entity_name_his
        EntityNameHis entityNameHis = new EntityNameHis();
        entityNameHis.setEntityType(1);
        entityNameHis.setDqCode(entityInfo.getEntityCode());
        entityNameHis.setOldName(entityInfo.getEntityNameHis());
        entityNameHis.setSource(3);
        entityNameHis.setHappenDate(new Date());
        entityNameHis.setCreater(SecurityUtils.getUsername());
        entityNameHisMapper.insertEntityNameHis(entityNameHis);

        //新增Stock_cn_info
        StockCnInfo stockCnInfo = new StockCnInfo();
        stockCnInfo.setStockShortName(entityStockInfoVo.getStockShortName());
        stockCnInfo.setStockCode(entityStockInfoVo.getStockCode());
        int insert = stockCnInfoMapper.insert(stockCnInfo);
        DecimalFormat g1=new DecimalFormat("000000");
        String startZeroStr = g1.format(stockCnInfo.getId());
        stockCnInfo.setStockDqCode("SA"+startZeroStr);
        LambdaUpdateWrapper<StockCnInfo> Wrapper = new LambdaUpdateWrapper<>();
        Wrapper.eq(StockCnInfo :: getId,stockCnInfo.getId())
                .set(StockCnInfo :: getStockDqCode,stockCnInfo.getStockDqCode());
        stockCnInfoMapper.update(stockCnInfo,Wrapper);
        //新增关联关系entity_stock_cn_rel
        EntityStockCnRel entityStockCnRel = new EntityStockCnRel();
        entityStockCnRel.setStockDqCode("SA"+startZeroStr);
        entityStockCnRel.setEntityCode(entityInfo.getEntityCode());
        entityStockCnRelMapper.insert(entityStockCnRel);
        //新增entity_attr_value
        ArrayList<EntityAttrValue> entityAttrValues = new ArrayList<EntityAttrValue>();
        //TODO 年报类型
        EntityAttrValue entityAttrValueReportType = new EntityAttrValue();
        entityAttrValueReportType.setAttrId(Common.AN_RPORT_TYPE.longValue());
        entityAttrValueReportType.setEntityCode(entityStockInfoVo.getStockCode());
        entityAttrValueReportType.setValue(entityStockInfoVo.getAnRportType());
        entityAttrValues.add(entityAttrValueReportType);
        //TODO 金融机构子行业
        EntityAttrValue financeSubIndu = new EntityAttrValue();
        financeSubIndu.setAttrId(Common.FINANCE_SUB_INDU_ID.longValue());
        financeSubIndu.setEntityCode(entityStockInfoVo.getStockCode());
        financeSubIndu.setValue(entityStockInfoVo.getFinanceSubIndu());
        entityAttrValues.add(financeSubIndu);
        //TODO 上市日期
        EntityAttrValue entityAttrValueStartDate = new EntityAttrValue();
        entityAttrValueStartDate.setAttrId(Common.SHANSI_DATE_ID.longValue());
        entityAttrValueStartDate.setEntityCode(entityStockInfoVo.getStockCode());
        entityAttrValueStartDate.setValue(entityStockInfoVo.getStartXiDate());
        entityAttrValues.add(entityAttrValueStartDate);
        //TODO 退市日期
        EntityAttrValue entityAttrValueEndDate = new EntityAttrValue();
        entityAttrValueEndDate.setAttrId(Common.TUISI_DATE_ID.longValue());
        entityAttrValueEndDate.setEntityCode(entityStockInfoVo.getStockCode());
        entityAttrValueEndDate.setValue(entityStockInfoVo.getEndDate());
        entityAttrValues.add(entityAttrValueEndDate);
        //上市版
        EntityAttrValue entityAttrValuelisSec = new EntityAttrValue();
        entityAttrValuelisSec.setAttrId(Common.STOCK_SHANXI.longValue());
        entityAttrValuelisSec.setEntityCode(entityStockInfoVo.getStockCode());
        entityAttrValuelisSec.setValue(entityStockInfoVo.getLisSec());
        entityAttrValues.add(entityAttrValuelisSec);
        //TODO 交易所
        EntityAttrValue entityAttrValueExange = new EntityAttrValue();
        entityAttrValueExange.setAttrId(Common.EXCHANGE_ID.longValue());
        entityAttrValuelisSec.setEntityCode(entityStockInfoVo.getStockCode());
        entityAttrValuelisSec.setValue(entityStockInfoVo.getLisSec());
        entityAttrValues.add(entityAttrValueExange);
        return R.ok("新增成功");
    }

       /**
        *港股新增
        *
        * @param entityStockInfoVo
        * @return R
        * @author penTang
        * @date 2022/9/29 9:59
       */
       @Override
        public R createStockEntityG(EntityStockInfoVo entityStockInfoVo){
        EntityInfo entityInfo = entityInfoMapper.selectOne(new LambdaQueryWrapper<EntityInfo>().eq(EntityInfo::getCreditCode, entityStockInfoVo.getCreditCode()));
        StockThkInfo stockThkCode = stockThkInfoMapper.selectOne(new LambdaQueryWrapper<StockThkInfo>().eq(StockThkInfo::getStockCode, entityStockInfoVo.getStockCode()));
         if (!ObjectUtils.isEmpty(entityInfo) || !ObjectUtils.isEmpty(stockThkCode)){
             return R.fail("新增失败：社会信用代码或股票代码重复不能进行新增");
            }
            //新增entity_info
            EntityInfo entityInfoBystockHk = new EntityInfo();
            entityInfoBystockHk.setCreditCode(entityStockInfoVo.getCreditCode());
            entityInfoBystockHk.setEntityName(entityStockInfoVo.getEntityName());
            if (!ObjectUtils.isEmpty(entityStockInfoVo.getCreditError())) {
                entityInfoBystockHk.setCreditError(0);
            }
            entityInfoBystockHk.setCreditError(1);
            entityInfoBystockHk.setCreditErrorRemark(entityStockInfoVo.getCreditErrorRemark());
            entityInfoBystockHk.setFinance(entityStockInfoVo.getFinance());
           //曾用名
           entityInfo.setEntityNameHis(entityStockInfoVo.getEntityNameHis());
           //创建人
           entityInfo.setCreater(SecurityUtils.getUsername());

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
           entityNameHis.setDqCode(entityInfo.getEntityCode());
           entityNameHis.setOldName(entityInfo.getEntityNameHis());
           entityNameHis.setSource(3);
           entityNameHis.setHappenDate(new Date());
           entityNameHis.setCreater(SecurityUtils.getUsername());
           entityNameHisMapper.insertEntityNameHis(entityNameHis);
           // 新增 Stock_Thk_Info
            StockThkInfo stockThkInfo = new StockThkInfo();
            stockThkInfo.setStockCode(entityStockInfoVo.getStockCode());
            int insert = stockThkInfoMapper.insert(stockThkInfo);
            DecimalFormat g1=new DecimalFormat("000000");
            String startZeroStr = g1.format(stockThkInfo.getId());
            stockThkInfo.setStockDqCode("HK"+startZeroStr);
            LambdaUpdateWrapper<StockThkInfo> Wrapper = new LambdaUpdateWrapper<>();
            Wrapper.eq(StockThkInfo :: getId,stockThkInfo.getId())
                    .set(StockThkInfo :: getStockDqCode,stockThkInfo.getStockDqCode());
            stockThkInfoMapper.update(stockThkInfo,Wrapper);
            //新增关联关系entity_stock_thk_rel
           EntityStockThkRel entityStockThkRel = new EntityStockThkRel();
           entityStockThkRel.setStockDqCode("HK"+startZeroStr);
           entityStockThkRel.setEntityCode(entityInfo.getEntityCode());
           EntityStockThkRelMapper.insert(entityStockThkRel);
            //新增entity_attr_value
            ArrayList<EntityAttrValue> entityAttrValues = new ArrayList<EntityAttrValue>();
            //TODO 年报类型
            EntityAttrValue entityAttrValueReportType = new EntityAttrValue();
            entityAttrValueReportType.setAttrId(Common.AN_RPORT_TYPE.longValue());
            entityAttrValueReportType.setEntityCode(entityStockInfoVo.getStockCode());
            entityAttrValueReportType.setValue(entityStockInfoVo.getAnRportType());
            entityAttrValues.add(entityAttrValueReportType);
            //TODO 金融机构子行业
            EntityAttrValue financeSubIndu = new EntityAttrValue();
            financeSubIndu.setAttrId(Common.FINANCE_SUB_INDU_ID.longValue());
            financeSubIndu.setEntityCode(entityStockInfoVo.getStockCode());
            financeSubIndu.setValue(entityStockInfoVo.getFinanceSubIndu());
            entityAttrValues.add(financeSubIndu);
            //TODO 上市日期
            EntityAttrValue entityAttrValueStartDate = new EntityAttrValue();
            entityAttrValueStartDate.setAttrId(Common.STOCK_SHANXI_DATE_HK_ID.longValue());
            entityAttrValueStartDate.setEntityCode(entityStockInfoVo.getStockCode());
            entityAttrValueStartDate.setValue(entityStockInfoVo.getStartXiDate());
            entityAttrValues.add(entityAttrValueStartDate);
            //TODO 退市日期
            EntityAttrValue entityAttrValueEndDate = new EntityAttrValue();
            entityAttrValueEndDate.setAttrId(Common.TUISI_DATE_HK_ID.longValue());
            entityAttrValueEndDate.setEntityCode(entityStockInfoVo.getStockCode());
            entityAttrValueEndDate.setValue(entityStockInfoVo.getEndDate());
            entityAttrValues.add(entityAttrValueEndDate);
            //上市版
            EntityAttrValue entityAttrValuelisSec = new EntityAttrValue();
            entityAttrValuelisSec.setAttrId(Common.STOCK_SHANXI_DATE_HK_ID.longValue());
            entityAttrValuelisSec.setEntityCode(entityStockInfoVo.getStockCode());
            entityAttrValuelisSec.setValue(entityStockInfoVo.getLisSec());
            entityAttrValues.add(entityAttrValuelisSec);
            //TODO 交易所
            EntityAttrValue entityAttrValuelisEx = new EntityAttrValue();
            entityAttrValuelisEx.setAttrId(Common.EXCHANGE_HK_ID.longValue());
            entityAttrValuelisEx.setEntityCode(entityStockInfoVo.getStockCode());
            entityAttrValuelisEx.setValue(entityStockInfoVo.getExchange());
            entityAttrValues.add(entityAttrValuelisEx);
        return null;
    }

}
