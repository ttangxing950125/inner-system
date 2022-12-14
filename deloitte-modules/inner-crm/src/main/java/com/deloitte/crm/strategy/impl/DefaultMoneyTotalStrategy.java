package com.deloitte.crm.strategy.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.constants.DataChangeType;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.dto.DefaultFirstNumberCountDto;
import com.deloitte.crm.mapper.BondInfoMapper;
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
import java.util.stream.Collectors;

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
     * ??????????????????wind??????
     *
     * @param windDictId
     * @return boolean
     */
    @Override
    public boolean support(Integer windDictId) {
        return Objects.equals(WindTaskEnum.BREAK_CONTRACT_DEFAULT_MONEY_TOTAL.getId(), windDictId);
    }

    /**
     * ????????????
     * {@link com.deloitte.crm.strategy.iampl.DefaultFirstNumberCountStrategy#doBondImport(DefaultFirstNumberCount, Date, CrmWindTask)} }
     */
    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Future<Object> doBondImport(DefaultMoneyTotal moneyTotal, Date timeNow, CrmWindTask windTask) {
        moneyTotal.setTaskId(windTask.getId());

        String shortName = moneyTotal.getBondAbstract();

        //TODO ????????????????????????BondInfo ??????????????????  ?????????????????????????????? ?????? ??????????????????BondInfo ?????????????????????????????????
        BondInfoMapper bondInfoMapper = ApplicationContextHolder.get().getBean(BondInfoMapper.class);
        BondInfo bondInfo1 = bondInfoMapper.selectOne(new LambdaQueryWrapper<BondInfo>().eq(BondInfo::getBondCode, moneyTotal.getBondCode())
                .eq(BondInfo::getIsDeleted, Boolean.FALSE));
        BondInfo bondInfo = Optional.ofNullable(bondInfo1).orElseGet(() -> BondInfo.builder().bondShortName(shortName).build());

        if (moneyTotal.getLatestStatus().equals("????????????")) {
            bondInfo.setBondStatus(7);
            bondInfo.setBondState(1);
        } else if (moneyTotal.getLatestStatus().equals("??????")) {
            bondInfo.setBondStatus(8);
        }
        if (bondInfo.getId() != null) {
            int count = bondInfoService.updateBondInfo(bondInfo);
        } else {
            bondInfo = bondInfoService.saveOrUpdate(bondInfo);
        }
        List<EntityInfo> entityInfoLists = iEntityInfoService.findByName(moneyTotal.getPublisher());
        log.info(">>>>???????????????==>{}:????????????????????????:==>{}", moneyTotal.getPublisher(), JSON.toJSONString(entityInfoLists));
        if (CollUtil.isNotEmpty(entityInfoLists)) {
            for (EntityInfo entityInfoList : entityInfoLists) {
                EntityBondRel entityBondRel = entityBondRelMapper.findByEntityBondCode(entityInfoList.getEntityCode(), bondInfo.getBondCode());
                log.info(">>>>????????????code:>>{}?????????code:{}??????????????????????????????????????????:{}", entityInfoList.getEntityCode(), bondInfo.getBondCode(), JSON.toJSONString(entityBondRel));
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
        //??????????????????????????????????????? ?????? "????????????"
        final List<DefaultMoneyTotal> lastList = defaultMoneyTotalMapper.selectList(new LambdaQueryWrapper<DefaultMoneyTotal>()
                .eq(DefaultMoneyTotal::getBondCode, moneyTotal.getBondCode())
                .orderBy(true, false, DefaultMoneyTotal::getId)
                .last("LIMIT 1"));
        if (CollUtil.isEmpty(lastList)) {
            changeType = DataChangeType.INSERT.getId();
        } else {
            final DefaultMoneyTotal last = lastList.stream().findFirst().get();
            if (!ObjectUtil.equals(last, moneyTotal)) {
                changeType = DataChangeType.UPDATE.getId();
            }
        }
        moneyTotal.setChangeType(changeType);
        final int updateCount = entityAttrValueService.updateBondAttr(bondInfo.getBondCode(), moneyTotal);
        if (resStatus == null && updateCount > 0) {
            resStatus = 2;
        }
        this.defaultMoneyTotalMapper.insert(moneyTotal);
        return new AsyncResult<>(new Object());
    }

    @Override
    public Object doTask(WindTaskContext windTaskContext) throws Exception {
        MultipartFile file = windTaskContext.getFile();
        CrmWindTask windTask = windTaskContext.getWindTask();
        ExcelUtil<DefaultMoneyTotal> util = new ExcelUtil<DefaultMoneyTotal>(DefaultMoneyTotal.class);
        List<DefaultMoneyTotal> list = util.importExcel(file.getInputStream(), true);
        Collections.reverse(list);
        DefaultMoneyTotalService defaultMoneyTotalService = ApplicationContextHolder.get().getBean(DefaultMoneyTotalService.class);
        return defaultMoneyTotalService.doTask(windTask, list);

    }

    /**
     * ????????????????????????????????????????????????
     *
     * @param windTask
     * @return
     */
    @Override
    public List<String> getDetailHeader(CrmWindTask windTask) {
        ArrayList<String> arr = new ArrayList<>();
        //????????????
        //????????????
        //??????????????????
        arr.add("????????????");
        arr.add("????????????");

        arr.add("????????????");
        arr.add("????????????");
        return arr;
    }

    //TODO
    @Override
    public List<Map<String, Object>> getDetail(CrmWindTask windTask) {
        Integer taskId = windTask.getId();
        Wrapper<DefaultMoneyTotal> wrapper = Wrappers.<DefaultMoneyTotal>lambdaQuery()
                .eq(DefaultMoneyTotal::getTaskId, taskId)
                .in(DefaultMoneyTotal::getChangeType, 1, 2);

        return defaultMoneyTotalMapper.selectList(wrapper).stream().map(item -> {

            HashMap<String, Object> dataMap = new HashMap<>();

            dataMap.put("????????????", item.getImportTime());
            dataMap.put("ID", item.getId());
            dataMap.put("????????????", item.getChangeType());

            dataMap.put("????????????", item.getBondCode());
            dataMap.put("????????????", item.getBondAbstract());
            dataMap.put("?????????", item.getPublisher());
            dataMap.put("????????????(??????)", item.getOfferingSize());

            return dataMap;
        }).collect(Collectors.toList());
    }

}
