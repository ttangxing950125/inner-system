package com.deloitte.crm.strategy.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deloitte.crm.constants.DataChangeType;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.dto.BondInfoDto;
import com.deloitte.crm.dto.DefaultFirstNumberCountDto;
import com.deloitte.crm.mapper.DefaultFirstNumberCountMapper;
import com.deloitte.crm.mapper.EntityBondRelMapper;
import com.deloitte.crm.service.*;
import com.deloitte.crm.strategy.WindTaskContext;
import com.deloitte.crm.strategy.WindTaskStrategy;
import com.deloitte.crm.strategy.enums.WindTaskEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Future;

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
    private DefaultFirstNumberCountService defaultFirstNumberCountService;
    @Resource
    private IEntityInfoService iEntityInfoService;

    @Resource
    private IEntityBondRelService iEntityBondRelService;

    @Resource
    private EntityBondRelMapper entityBondRelMapper;


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
        return null;
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

    @Override
    public List<Map<String, Object>> getDetail(CrmWindTask windTask) {
        return null;
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
    public Future<BondInfoDto> doBondImport(DefaultFirstNumberCount defaultFirstNumberCount, Date timeNow, CrmWindTask windTask) {
        /**
         * 查询债券是否存在 根据 “债券简称” 查询 如果不存在创建对象 并修改 状态为违约 7
         * @see com.deloitte.crm.domain.BondInfo#getBondStatus()
         */
        String shortName = defaultFirstNumberCount.getDefaultBondsDesc();
        BondInfo bondInfo = Optional.ofNullable(bondInfoService.findByShortName(shortName)).orElseGet(() -> BondInfo.builder().bondShortName(shortName).build());
        bondInfo.setBondStatus(7);
        if (bondInfo.getId() != null) {
            int count = bondInfoService.updateBondInfo(bondInfo);
        } else {
            bondInfo = bondInfoService.saveOrUpdate(bondInfo);
        }
        List<EntityInfo> EntityInfoLists = iEntityInfoService.findByName(defaultFirstNumberCount.getPublisher());
        log.info(">>>>根据发行人==>{}:查询企业主体信息:==>{}", defaultFirstNumberCount.getPublisher(), JSONUtil.toJsonStr(EntityInfoLists));
        if (CollUtil.isNotEmpty(EntityInfoLists)) {
            for (EntityInfo entityInfoList : EntityInfoLists) {
                EntityBondRel entityBondRel = entityBondRelMapper.findByEntityBondCode(entityInfoList.getEntityCode(), bondInfo.getBondCode());
                log.info(">>>>根据债券code:>>{}和企业code:{}查询中间关联关系返回结果集合:{}", entityInfoList.getEntityCode(), bondInfo.getBondCode(), JSONUtil.toJsonStr(entityBondRel));
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
        final DefaultFirstNumberCount DBDefaultFirstNumberCount = defaultFirstNumberCountMapper.selectList(new QueryWrapper<DefaultFirstNumberCount>().lambda().eq(DefaultFirstNumberCount::getDefaultBondsCode, bondInfo.getBondCode())).stream().findFirst().get();
        if (DBDefaultFirstNumberCount == null) {
            changeType = DataChangeType.INSERT.getId();
        } else if (!Objects.equals(DBDefaultFirstNumberCount, defaultFirstNumberCount)) {
            ////如果他们两个不相同，代表有属性修改了
            changeType = DataChangeType.UPDATE.getId();
        }
        defaultFirstNumberCount.setChangeType(changeType);

        final int updateCount = entityAttrValueService.updateBondAttr(bondInfo.getBondCode(), defaultFirstNumberCount);
        if (resStatus == null && updateCount > 0) {
            resStatus = 2;
        }

        DefaultFirstNumberCountDto defaultFirstNumberCountDto = new DefaultFirstNumberCountDto();
        defaultFirstNumberCountDto.setInfo(defaultFirstNumberCount);
        defaultFirstNumberCountDto.setResStatus(resStatus);

        return new AsyncResult(defaultFirstNumberCountDto);
    }
}
