package com.deloitte.crm.strategy.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.constants.DataChangeType;
import com.deloitte.crm.constants.StockThkStatus;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.mapper.EntityBaseBusiInfoMapper;
import com.deloitte.crm.mapper.EntityStockThkRelMapper;
import com.deloitte.crm.service.*;
import com.deloitte.crm.strategy.WindTaskContext;
import com.deloitte.crm.strategy.WindTaskStrategy;
import com.deloitte.crm.strategy.enums.WindTaskEnum;
import com.deloitte.crm.utils.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static com.deloitte.common.core.utils.DateUtil.YYYY_MM_DD;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/01/13:52
 * @Description: <证券发行-港股-已退市证券一览/>
 */
@Slf4j
@Component
public class ThkSecRetiredInfoStorategy implements WindTaskStrategy {

    @Resource
    private ThkSecRetiredInfoService thkSecRetiredInfoService;
    @Resource
    private StockThkInfoService stockThkInfoService;
    @Resource
    private CrmTypeInfoService crmTypeInfoService;
    @Resource
    private IEntityInfoService entityInfoService;
    @Resource
    private IBondInfoService iBondInfoService;

    /**
     * 处理导入的数据
     *
     * @param thkSecRetiredInfos
     * @param timeNow
     * @param windTask
     * @return
     */
    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Future<Object> doImport(ThkSecRetiredInfo thkSecRetiredInfos, Date timeNow, CrmWindTask windTask) {
        try {
            //设置属性
            thkSecRetiredInfos.setTaskId(windTask.getId());
            //查询证券代码是否存在
            String secIssInfoCode = thkSecRetiredInfos.getCode();

            StockThkInfo stockThkInfo = stockThkInfoService.findByCode(secIssInfoCode);
            if (stockThkInfo == null) {
                stockThkInfo = new StockThkInfo();
            }

            stockThkInfo.setDelistingDate(Optional.ofNullable(thkSecRetiredInfos.getDelistingDate()).map(e -> DateUtil.format(e, YYYY_MM_DD)).orElse(null));
            stockThkInfo.setStockCode(secIssInfoCode);
            stockThkInfo.setStockName(thkSecRetiredInfos.getName());
            stockThkInfo.setCompanyEnglish(thkSecRetiredInfos.getCompanyEnglish());
            stockThkInfo.setStatutoryCapital(thkSecRetiredInfos.getStatutoryCapital());
            stockThkInfo.setSsuingEquity(thkSecRetiredInfos.getSsuingEquity());
            stockThkInfo.setCurrency(thkSecRetiredInfos.getCurrency());
            stockThkInfo.setChairmanGroup(thkSecRetiredInfos.getChairmanGroup());
            stockThkInfo.setCompanySecretary(thkSecRetiredInfos.getCompanySecretary());
            stockThkInfo.setCompanyWebSite(thkSecRetiredInfos.getCompanyWebSite());
            stockThkInfo.setEmailAddress(thkSecRetiredInfos.getEmailAddress());
            stockThkInfo.setPhone(thkSecRetiredInfos.getPhone());
            stockThkInfo.setFax(thkSecRetiredInfos.getFax());
            stockThkInfo.setJunction(thkSecRetiredInfos.getJunction());
            stockThkInfo.setHsLndustry(thkSecRetiredInfos.getHsLndustry());
            stockThkInfo.setTerminationType(thkSecRetiredInfos.getTerminationType());

            /***
             *部分代码逻辑沿用BondDelIssStrategy
             * {@link com.deloitte.crm.strategy.impl.BondDelIssStrategy#doBondImport(BondDelIss, Date, CrmWindTask)}
             */
            //推迟或取消发行债券 均为二级发行
            CrmTypeInfo crmTypeInfo = crmTypeInfoService.getBaseMapper().selectOne(new LambdaQueryWrapper<CrmTypeInfo>().eq(CrmTypeInfo::getName, thkSecRetiredInfos.getBelWind()).eq(CrmTypeInfo::getIsDeleted, Boolean.FALSE).eq(CrmTypeInfo::getType, 1));
            if (crmTypeInfo != null) {
                if (StringUtils.isNotEmpty(crmTypeInfo.getParentCode())) {
                    Set<CrmTypeInfo> hashSetResult = crmTypeInfoService.findCodeByParent(crmTypeInfo, Integer.valueOf(crmTypeInfo.getType()));
                    if (CollectionUtil.isEmpty(hashSetResult)) {
                        thkSecRetiredInfos.setBelWind(crmTypeInfo.getName());
                        stockThkInfo.setBelWind(crmTypeInfo.getName());
                    } else {
                        String WindIndustryApend = hashSetResult.stream().sorted(Comparator.comparing(CrmTypeInfo::getLevel)).map(CrmTypeInfo::getName).collect(Collectors.joining("--"));
                        thkSecRetiredInfos.setBelWind(WindIndustryApend + "--" + crmTypeInfo.getName());
                        stockThkInfo.setBelWind(WindIndustryApend + "--" + crmTypeInfo.getName());
                    }
                } else {
                    thkSecRetiredInfos.setBelWind(crmTypeInfo.getName());
                    stockThkInfo.setBelWind(crmTypeInfo.getName());
                }
            }
//            thkSecRetiredInfos.setNumber(null);
            //这条ThkSecIssDetail是新增还是修改 1-新增 2-修改
            Integer changeType = null;
            //查询这条数据有没有
            ThkSecRetiredInfo thkSecRetiredInfoLast = thkSecRetiredInfoService.getBaseMapper().selectOne(
                    new LambdaQueryWrapper<ThkSecRetiredInfo>().eq(ThkSecRetiredInfo::getCode, thkSecRetiredInfos.getCode())
                            .orderBy(true, false, ThkSecRetiredInfo::getId)
                            .last("LIMIT 1"));
            if (thkSecRetiredInfoLast == null) {
                changeType = DataChangeType.INSERT.getId();
                stockThkInfo.setStockStatus(StockThkStatus.DELISTING.getId());
                stockThkInfo.setStatusDesc(StockThkStatus.DELISTING.getName());

            } else if (!Objects.equals(thkSecRetiredInfoLast, thkSecRetiredInfos)) {
                //如果他们两个不相同，代表有属性修改了
                changeType = DataChangeType.UPDATE.getId();
            }
            StockThkInfo dbStockThkInfo = stockThkInfoService.saveOrUpdateNew(stockThkInfo);
            //公司中文名称
            if (StringUtils.isNotEmpty(thkSecRetiredInfos.getCompanyCn())) {
                List<EntityInfo> entityInfos = entityInfoService.findByName(thkSecRetiredInfos.getCompanyCn());
                if (CollUtil.isNotEmpty(entityInfos)) {
                    //更新 主体基本信息表
                    entityInfos.stream().map(e -> e.setWindMaster(thkSecRetiredInfos.getBelWind())).forEach(e -> entityInfoService.getBaseMapper().updateById(e));
                    initEntityRel(entityInfos, stockThkInfo, thkSecRetiredInfos);
                } else {
                    log.warn("==> 根据企业名称：{} 查询主体信息 为空 不做任何绑定关系", thkSecRetiredInfos.getCompanyCn());
                }
            }
            thkSecRetiredInfos.setChangeType(changeType);
            thkSecRetiredInfoService.save(thkSecRetiredInfos);

            return new AsyncResult(new Object());
        } catch (Exception e) {
            log.error("导入出现异常>>>>>:{}", e);
            return new AsyncResult<>(e);
        }
    }

    @Override
    public boolean support(Integer windDictId) {
        return Objects.equals(windDictId, WindTaskEnum.THK_SEC_RETIRED_INFO.getId());
    }

    @Override
    public Object doTask(WindTaskContext windTaskContext) throws Exception {
        MultipartFile file = windTaskContext.getFile();
        CrmWindTask windTask = windTaskContext.getWindTask();
        ExcelUtil<ThkSecRetiredInfo> util = new ExcelUtil<ThkSecRetiredInfo>(ThkSecRetiredInfo.class);
        List<ThkSecRetiredInfo> thkSecRetiredInfo = util.importExcel(windTaskContext.getFileStream(), true);
        Collections.reverse(thkSecRetiredInfo);
        return thkSecRetiredInfoService.doTask(windTask, thkSecRetiredInfo);
    }

    @Override
    public List<String> getDetailHeader(CrmWindTask windTask) {
        ArrayList<String> arr = new ArrayList<>();
        arr.add("导入日期");
        arr.add("变化状态");

        //证券代码
        //证券简称
        //公司中文名称
        arr.add("证券代码");
        arr.add("证券简称");
        arr.add("公司中文名称");

        return arr;
    }

    @Override
    public List<Map<String, Object>> getDetail(CrmWindTask windTask) {
        return null;
    }

    private void initEntityRel(List<EntityInfo> entityInfos, StockThkInfo stockThkInfo, ThkSecRetiredInfo thkSecRetiredInfo) {
        for (EntityInfo info : entityInfos) {
            String entityCode = info.getEntityCode();
            String stockDqCode = stockThkInfo.getStockDqCode();
            //查询关联关系
            EntityBaseBusiInfoMapper entityBaseBusiInfoMapper = ApplicationContextHolder.get().getBean(EntityBaseBusiInfoMapper.class);
            EntityBaseBusiInfo entityBaseBusiInfo = entityBaseBusiInfoMapper
                    .selectOne(new LambdaQueryWrapper<EntityBaseBusiInfo>()
                            .eq(EntityBaseBusiInfo::getEntityCode, entityCode));
            if (entityBaseBusiInfo != null) {
                entityBaseBusiInfo.setRegAddr(thkSecRetiredInfo.getDetailedAddress());
                entityBaseBusiInfo.setBusRange(Optional.ofNullable(thkSecRetiredInfo.getMainBusiness()).orElse(null));
                entityBaseBusiInfoMapper.updateById(entityBaseBusiInfo);
            } else {
                EntityBaseBusiInfo info1 = new EntityBaseBusiInfo();
                info1.setRegAddr(Optional.ofNullable(thkSecRetiredInfo.getDetailedAddress()).orElse(null));
                info1.setBusRange(Optional.ofNullable(thkSecRetiredInfo.getMainBusiness()).orElse(null));
                info1.setEntityCode(entityCode);
                entityBaseBusiInfoMapper.insert(info1);
            }
            EntityStockThkRel entityStockThkRel = ApplicationContextHolder
                    .get()
                    .getBean(EntityStockThkRelMapper.class)
                    .selectOne(new LambdaQueryWrapper<EntityStockThkRel>()
                            .eq(EntityStockThkRel::getEntityCode, entityCode)
                            .eq(EntityStockThkRel::getStockDqCode, stockDqCode)
                            .eq(EntityStockThkRel::getStatus, 1));
            if (entityStockThkRel != null) {
                continue;
            }
            //新增关联关系
            EntityStockThkRel stockThkRel = new EntityStockThkRel();
            stockThkRel.setEntityCode(entityCode);
            stockThkRel.setStockDqCode(stockDqCode);
            ApplicationContextHolder.get().getBean(EntityStockThkRelMapper.class).insert(stockThkRel);
        }
    }


}
