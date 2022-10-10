package com.deloitte.crm.strategy.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.constants.DataChangeType;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.dto.DefaultFirstNumberCountDto;
import com.deloitte.crm.mapper.DefaultMoneyTotalMapper;
import com.deloitte.crm.mapper.EntityBondRelMapper;
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

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/10/09/15:21
 * @Description:
 */
@Slf4j
@Component
public class DefaultMoneyTotalStrategy implements WindTaskStrategy {
    @Resource
    private IBondInfoService bondInfoService;

    @Resource
    private IEntityInfoService iEntityInfoService;
    @Resource
    private EntityBondRelMapper entityBondRelMapper;
    @Resource
    private IEntityBondRelService iEntityBondRelService;
    @Resource
    private DefaultMoneyTotalMapper defaultMoneyTotalMapper;
    @Resource
    private IEntityAttrValueService entityAttrValueService;

    /**
     * 是否支持当前wind任务
     *
     * @param windDictId
     * @return boolean
     */
    @Override
    public boolean support(Integer windDictId) {
        return Objects.equals(WindTaskEnum.BREAK_CONTRACT_DEFAULT_MONEY_TOTAL.getId(), windDictId);
    }

    /**
     * 参看逻辑
     * {@link com.deloitte.crm.strategy.impl.DefaultFirstNumberCountStrategy#doBondImport(DefaultFirstNumberCount, Date, CrmWindTask)} }
     */
    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Future<Object> doBondImport(DefaultMoneyTotal moneyTotal, Date timeNow, CrmWindTask windTask) {
        try {
            String shortName = moneyTotal.getBondAbstract();
            BondInfo bondInfo = Optional.ofNullable(bondInfoService.findByShortName(shortName)).orElseGet(() -> BondInfo.builder().bondShortName(shortName).build());
            if (moneyTotal.getLatestStatus().equals("实质违约")) {
                bondInfo.setBondStatus(7);
            } else if (moneyTotal.getLatestStatus().equals("展期")) {
                bondInfo.setBondStatus(8);
            }
            if (bondInfo.getId() != null) {
                int count = bondInfoService.updateBondInfo(bondInfo);
            } else {
                bondInfo = bondInfoService.saveOrUpdate(bondInfo);
            }
            List<EntityInfo> entityInfoLists = iEntityInfoService.findByName(moneyTotal.getPublisher());
            log.info(">>>>根据发行人==>{}:查询企业主体信息:==>{}", moneyTotal.getPublisher(), JSON.toJSONString(entityInfoLists));
            if (CollUtil.isNotEmpty(entityInfoLists)) {
                for (EntityInfo entityInfoList : entityInfoLists) {
                    EntityBondRel entityBondRel = entityBondRelMapper.findByEntityBondCode(entityInfoList.getEntityCode(), bondInfo.getBondCode());
                    log.info(">>>>根据债券code:>>{}和企业code:{}查询中间关联关系返回结果集合:{}", entityInfoList.getEntityCode(), bondInfo.getBondCode(), JSON.toJSONString(entityBondRel));
                    if (entityBondRel == null) {
                        EntityBondRel entityBondRelEntity = new EntityBondRel();
                        entityBondRelEntity.setBdCode(bondInfo.getBondCode());
                        entityBondRelEntity.setEntityCode(entityInfoList.getEntityCode());
                        entityBondRelEntity.setStatus(1);
                        iEntityBondRelService.insertEntityBondRel(entityBondRelEntity);
                    }
                }
            }
            Integer resStatus = null;
            Integer changeType = null;
            //看之前有没有导入过这个数据 根据 "债券代码"
            final List<DefaultMoneyTotal> defaultMoneyTotals = defaultMoneyTotalMapper.selectList(new QueryWrapper<DefaultMoneyTotal>().lambda().eq(DefaultMoneyTotal::getBondCode, moneyTotal.getBondCode()));
            if (CollUtil.isEmpty(defaultMoneyTotals)) {
                changeType = DataChangeType.INSERT.getId();
            } else if (!Objects.equals(defaultMoneyTotals, moneyTotal)) {
                //如果他们两个不相同，代表有属性修改了
                changeType = DataChangeType.UPDATE.getId();
            }

            moneyTotal.setChangeType(changeType);
            moneyTotal.setTaskId(windTask.getId());

            final int updateCount = entityAttrValueService.updateBondAttr(bondInfo.getBondCode(), moneyTotal);
            if (resStatus == null && updateCount > 0) {
                resStatus = 2;
            }
            this.defaultMoneyTotalMapper.insert(moneyTotal);
            return new AsyncResult<>(new Object());
        } catch (Exception e) {
            log.error("执行异常>>>>:{}", e);
            return new AsyncResult<>(e);
        }
    }

    @Override
    public Object doTask(WindTaskContext windTaskContext) throws Exception {
        MultipartFile file = windTaskContext.getFile();
        CrmWindTask windTask = windTaskContext.getWindTask();
        ExcelUtil<DefaultMoneyTotal> util = new ExcelUtil<DefaultMoneyTotal>(DefaultMoneyTotal.class);
        List<DefaultMoneyTotal> list = util.importExcel(file.getInputStream(), true);
        DefaultMoneyTotalService defaultMoneyTotalService = ApplicationContextHolder.get().getBean(DefaultMoneyTotalService.class);
        return defaultMoneyTotalService.doTask(windTask, list);

    }

    /**
     * 获得任务详情页，上传的数据的表头
     *
     * @param windTask
     * @return
     */
    @Override
    public List<String> getDetailHeader(CrmWindTask windTask) {
        ArrayList<String> arr = new ArrayList<>();
        //证券代码
        //证券简称
        //公司中文名称
        arr.add("导入日期");
        arr.add("变化状态");

        arr.add("代码");
        arr.add("公司名称");
        return arr;
    }

    //TODO
    @Override
    public List<Map<String, Object>> getDetail(CrmWindTask windTask) {
        return null;
    }

}
