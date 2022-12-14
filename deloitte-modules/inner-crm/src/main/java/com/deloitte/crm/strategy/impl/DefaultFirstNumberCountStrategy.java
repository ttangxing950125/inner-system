package com.deloitte.crm.strategy.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.deloitte.common.core.utils.poi.ExcelUtil;
import com.deloitte.crm.constants.DataChangeType;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.dto.BondInfoDto;
import com.deloitte.crm.dto.DefaultFirstNumberCountDto;
import com.deloitte.crm.mapper.BondInfoMapper;
import com.deloitte.crm.mapper.DefaultFirstNumberCountMapper;
import com.deloitte.crm.mapper.DefaultMoneyTotalMapper;
import com.deloitte.crm.mapper.EntityBondRelMapper;
import com.deloitte.crm.service.*;
import com.deloitte.crm.strategy.WindTaskContext;
import com.deloitte.crm.strategy.WindTaskStrategy;
import com.deloitte.crm.strategy.enums.WindTaskEnum;
import com.deloitte.crm.utils.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
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
 * @Date: 2022/10/09/11:11
 * @Description:
 */
@Slf4j
@Component
public class DefaultFirstNumberCountStrategy implements WindTaskStrategy {

    @Resource
    private IEntityAttrValueService entityAttrValueService;

    @Resource
    private DefaultFirstNumberCountMapper defaultFirstNumberCountMapper;
    @Resource
    private IBondInfoService bondInfoService;

    @Resource
    private IEntityInfoService iEntityInfoService;

    @Resource
    private IEntityBondRelService iEntityBondRelService;

    @Resource
    private EntityBondRelMapper entityBondRelMapper;

    @Resource
    private DefaultMoneyTotalMapper defaultMoneyTotalMapper;


    /**
     * 是否支持当前wind任务
     *
     * @param windDictId
     * @return boolean
     */
    @Override
    public boolean support(Integer windDictId) {
        return Objects.equals(WindTaskEnum.BREAK_CONTRACT_FIRST_NUMBER_COUNT.getId(), windDictId);
    }

    @Override
    public Object doTask(WindTaskContext windTaskContext) throws Exception {
        MultipartFile file = windTaskContext.getFile();
        CrmWindTask windTask = windTaskContext.getWindTask();
        ExcelUtil<DefaultFirstNumberCount> util = new ExcelUtil<DefaultFirstNumberCount>(DefaultFirstNumberCount.class);
        List<DefaultFirstNumberCount> list = util.importExcel(file.getInputStream(), true);
        Collections.reverse(list);
        DefaultFirstNumberCountService defaultFirstNumberCountService = ApplicationContextHolder.get().getBean(DefaultFirstNumberCountService.class);

        return defaultFirstNumberCountService.doTask(windTask, list);
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

        arr.add("证券代码");
        arr.add("证券简称");
        arr.add("摘要");
        arr.add("首次违约时债券余额(亿元)");
        return arr;
    }

    @Override
    public List<Map<String, Object>> getDetail(CrmWindTask windTask) {
        Integer taskId = windTask.getId();
        Wrapper<DefaultFirstNumberCount> wrapper = Wrappers.<DefaultFirstNumberCount>lambdaQuery()
                .eq(DefaultFirstNumberCount::getTaskId, taskId)
                .in(DefaultFirstNumberCount::getChangeType, 1, 2);
        return defaultFirstNumberCountMapper.selectList(wrapper).stream().map(item -> {
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("导入日期", item.getImportTime());
            dataMap.put("ID", item.getId());
            dataMap.put("变化状态", item.getChangeType());

            dataMap.put("证券代码", item.getDefaultBondsCode());
            dataMap.put("证券简称", item.getDefaultBondsDesc());
            dataMap.put("摘要", item.getAbstracts());
            dataMap.put("首次违约时债券余额(亿元)", item.getDefaultBondsBalance());
            return dataMap;
        }).collect(Collectors.toList());
    }

    /***
     * @desc:
     *  <br>企业首次违约报表导入逻辑</br>
     *     1、根据【债券简称】查询bond_info表，如果没有新增数据，
     *     2、如果【最新状态】为展期或实质违约，修改bond_info的状态status  默认就是违约
     *     3、根据【发行人】查询entity_info表中数据，如果能查询到，添加entity_bond_rel表中数据（如果关联关系已存在不添加）
     *     4、更新债券数据，调用entityAttrValueService.updateBondAttr()
     * @param defaultFirstNumberCount
     * @param timeNow
     * @param windTask
     * @return
     */
    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Future<DefaultFirstNumberCountDto> doBondImport(DefaultFirstNumberCount defaultFirstNumberCount, Date timeNow, CrmWindTask windTask) {
        /**
         * 查询债券是否存在 根据 “债券简称” 查询 如果不存在创建对象 并修改 状态为违约 7
         * @see com.deloitte.crm.domain.BondInfo#getBondStatus()
         */
        defaultFirstNumberCount.setTaskId(windTask.getId());

        String shortName = defaultFirstNumberCount.getDefaultBondsDesc();
        //TODO 通过债券代码查询BondInfo 查询一条数据  这个地方可能存在多个 目前 按照业务来讲BondInfo 是债券全程作为唯一索引
        BondInfoMapper bondInfoMapper = ApplicationContextHolder.get().getBean(BondInfoMapper.class);
        BondInfo bondInfo1 =bondInfoMapper.selectOne(new LambdaQueryWrapper<BondInfo>().eq(BondInfo::getBondCode,defaultFirstNumberCount.getDefaultBondsCode())
                .eq(BondInfo::getIsDeleted,Boolean.FALSE));
         BondInfo bondInfo = Optional.ofNullable(bondInfo1).orElseGet(() -> BondInfo.builder().bondShortName(shortName).build());

        bondInfo.setBondStatus(7);
        bondInfo.setBondState(1);

        if (bondInfo.getId() != null) {
            int count = bondInfoService.updateBondInfo(bondInfo);
        } else {
            bondInfo = bondInfoService.saveOrUpdate(bondInfo);
        }
        List<EntityInfo> entityInfos = iEntityInfoService.findByName(defaultFirstNumberCount.getPublisher());
        log.info("根据发行人==>{}:查询企业主体信息:==>{}", defaultFirstNumberCount.getPublisher(), JSON.toJSONString(entityInfos));
        if (CollUtil.isNotEmpty(entityInfos)) {
            for (EntityInfo entityInfoList : entityInfos) {
                EntityBondRel entityBondRel = entityBondRelMapper.findByEntityBondCode(entityInfoList.getEntityCode(), bondInfo.getBondCode());
                log.info(">>>>根据债券code:>>{}和企业code:{}查询中间关联关系返回结果集合:{}", entityInfoList.getEntityCode(), bondInfo.getBondCode(), com.alibaba.fastjson.JSON.toJSONString(entityBondRel));
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
        List<DefaultFirstNumberCount> DBDefaultFirstNumberCount = defaultFirstNumberCountMapper.selectList(new QueryWrapper<DefaultFirstNumberCount>()
                .lambda().eq(DefaultFirstNumberCount::getDefaultBondsCode, defaultFirstNumberCount.getDefaultBondsCode())
                .orderBy(true, false, DefaultFirstNumberCount::getId)
                .last("LIMIT 1"));
        if (CollUtil.isEmpty(DBDefaultFirstNumberCount)) {
            changeType = DataChangeType.INSERT.getId();
        } else {
            final DefaultFirstNumberCount last = DBDefaultFirstNumberCount.stream().findFirst().get();
            if (!Objects.equals(last, defaultFirstNumberCount)) {
                changeType = DataChangeType.UPDATE.getId();
            }
        }
        ////如果他们两个不相同，代表有属性修改了
        defaultFirstNumberCount.setChangeType(changeType);
        final int updateCount = entityAttrValueService.updateBondAttr(bondInfo.getBondCode(), defaultFirstNumberCount);
        if (resStatus == null && updateCount > 0) {
            resStatus = 2;
        }

        DefaultFirstNumberCountDto defaultFirstNumberCountDto = new DefaultFirstNumberCountDto();
        defaultFirstNumberCountDto.setInfo(defaultFirstNumberCount);
        defaultFirstNumberCountDto.setResStatus(resStatus);

        defaultFirstNumberCountMapper.insert(defaultFirstNumberCount);

        return new AsyncResult(defaultFirstNumberCountDto);
    }
}
