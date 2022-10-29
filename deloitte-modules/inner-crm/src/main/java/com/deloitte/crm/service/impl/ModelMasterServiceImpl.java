package com.deloitte.crm.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.SuccessInfo;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.dto.MasDto;
import com.deloitte.crm.mapper.*;
import com.deloitte.crm.service.*;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Slf4j
@Service
@AllArgsConstructor
public class ModelMasterServiceImpl implements IModelMasterService {
    private ModelMasterMapper modelMasterMapper;

    private ICrmMasTaskService iCrmMasTaskService;

    private ICrmDailyTaskService iCrmDailyTaskService;

    private IEntityInfoService iEntityInfoService;

    private IEntityAttrValueService iEntityAttrValueService;

    private EntityAttrIntypeService entityAttrIntypeService;

    private EntityMasterMapper entityMasterMapper;

    private EntityGovRelMapper entityGovRelMapper;

    private CrmSupplyTaskMapper crmSupplyTaskMapper;

    private EntityFinancialService entityFinancialService;

    private SendEmailService sendEmailService;

    private GovInfoMapper govInfoMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public ModelMaster selectModelMasterById(Long id) {
        return modelMasterMapper.selectModelMasterById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param modelMaster 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ModelMaster> selectModelMasterList(ModelMaster modelMaster) {
        return modelMasterMapper.selectModelMasterList(modelMaster);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param modelMaster 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertModelMaster(ModelMaster modelMaster) {
        return modelMasterMapper.insertModelMaster(modelMaster);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param modelMaster 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateModelMaster(ModelMaster modelMaster) {
        return modelMasterMapper.updateModelMaster(modelMaster);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteModelMasterByIds(Long[] ids) {
        return modelMasterMapper.deleteModelMasterByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteModelMasterById(Long id) {
        return modelMasterMapper.deleteModelMasterById(id);
    }


    /**
     * 敞口划分 选中单行开始工作 传入id后返回窗口 by正杰
     *
     * @param id
     * @return
     */
    @Override
    public R<MasDto> getTable(Integer id) {
        MasDto masDto = new MasDto();
        CrmMasTask crmMasTask = Optional.ofNullable(iCrmMasTaskService.getBaseMapper().selectOne(new QueryWrapper<CrmMasTask>().lambda().eq(CrmMasTask::getId, id))).orElseThrow(() -> new ServiceException(BadInfo.VALID_EMPTY_TARGET.getInfo()));
        EntityInfo entityInfo = Optional.ofNullable(iEntityInfoService.getBaseMapper().selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityCode, crmMasTask.getEntityCode()).eq(EntityInfo::getStatus, 1))).orElseThrow(() -> new ServiceException(BadInfo.VALID_EMPTY_TARGET.getInfo()));
        String entityCode = entityInfo.getEntityCode();
        //基础信息展示
        masDto.setEntityName(entityInfo.getEntityName()).setCreditCode(entityInfo.getCreditCode()).setSource(crmMasTask.getSourceName()).setWind(entityInfo.getWindMaster()).setShenWan(entityInfo.getShenWanMaster());
        //当企业为金融机构的时候 去查entity_financial 中的数据
        if(!ObjectUtils.isEmpty(entityInfo.getFinance())||entityInfo.getFinance()==1){
            EntityFinancial entityFinancial = entityFinancialService.getBaseMapper().selectOne(new QueryWrapper<EntityFinancial>().lambda().eq(EntityFinancial::getEntityCode, entityCode));
            masDto.setFinanceSegmentation(entityFinancial.getMince());
        }

        //查询是否是城投机构
        EntityGovRel entityGovRel = entityGovRelMapper.selectOne(new QueryWrapper<EntityGovRel>().lambda().eq(EntityGovRel::getEntityCode, entityCode));
        if(!ObjectUtils.isEmpty(entityGovRel)){
            String dqGovCode = entityGovRel.getDqGovCode();
            masDto.setDqGovCode(dqGovCode);
            GovInfo govInfo = govInfoMapper.selectOne(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getDqGovCode, dqGovCode));
            if(!ObjectUtils.isEmpty(govInfo)){
                GovNode tree = this.getTree(new GovNode().setGovName(govInfo.getGovName()), govInfo);
                masDto.setGovNode(tree);
            }
        }
        //查询敞口信息
        EntityMaster entityMaster = entityMasterMapper.selectOne(new QueryWrapper<EntityMaster>().lambda().eq(EntityMaster::getEntityCode, entityCode));
        masDto.setMasterCode(entityMaster.getMasterCode());
        return R.ok(masDto,SuccessInfo.SUCCESS.getInfo());
    }

    public GovNode getTree(GovNode govNode,GovInfo govInfo){
        GovInfo parent = govInfoMapper.selectOne(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getDqGovCode, govInfo.getPreGovCode()));
        if(!ObjectUtils.isEmpty(govInfo.getPreGovCode())){
            govNode.setChildren(govNode);
            govNode.setGovName(parent.getGovName());
            this.getTree(govNode,parent);
        }
        return govNode;
    }

    /**
     * 获取金融细分领域多选框
     *
     * @return
     */
    @Override
    public R<List<String>> getFinances() {
        List<EntityAttrIntype> entityAttrIntypes = entityAttrIntypeService.getBaseMapper().selectList(new QueryWrapper<EntityAttrIntype>().lambda().eq(EntityAttrIntype::getAttrId, 656));
        List<String> collect = entityAttrIntypes.stream().map(EntityAttrIntype::getValue).collect(Collectors.toList());
        return R.ok(collect);
    }

    private final Integer FINISH_STATE = 1;

    private final Integer UN_FINISH_STATE = 0;

    private final Integer FINISH_DAILY_STATE = 3;
    private final String YES = "Y";

    private final Integer ROLE_2_ID = 4;

    private final Integer ROLE_3_ID = 5;

    private final Integer ROLE_4_ID = 6;

    private final Integer ROLE_5_ID = 7;

    /**
     * 敞口划分 保存并提交
     *
     * @param masDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R insert(MasDto masDto) {
        log.info("==> 敞口划分 保存并提交开始请求参数:{}", JSON.toJSONString(masDto));
        String entityCode = masDto.getEntityCode();
        EntityInfo entityInfo = Optional.ofNullable(iEntityInfoService.getBaseMapper().selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityCode, entityCode))).orElseThrow(() -> new ServiceException(BadInfo.VALID_EMPTY_TARGET.getInfo()));
        //判断是否为 金融机构  是的话 => 新增到 entity_financial
        if (YES.equals(masDto.getIsFinance())) {
            updateEntityFinancial(masDto);
            entityInfo.setFinance(1);
            log.info("  =>> 修改一条数据至 entity_financial <<=  ");
        } else {
            entityInfo.setFinance(0);
        }

        //修改 wind行业 、 申万行业
        entityInfo.setWindMaster(masDto.getWind()).setShenWanMaster(masDto.getShenWan());
        iEntityInfoService.updateById(entityInfo);
        log.info("  =>> 修改一条数据至 entity_info <<=  ");

        updateEntityMaster(masDto);
        log.info("  =>> 修改一条数据至 entity_master <<=  ");

        updateEntityGovRel(masDto);
        log.info("  =>> 修改一条数据至 entity_gov_info <<=  ");

        //更改当条信息任务状态
        Date currentDate = iCrmMasTaskService.finishTask(masDto.getId(), SecurityUtils.getUsername());
        log.info("  =>> 完成任务 taskId = " + masDto.getId() + " <<=  ");

        // 查看当日任务情况 未处理的 UN_FINISH_STATE 0-未处理
        List<CrmMasTask> crmMasTasks = iCrmMasTaskService.getBaseMapper().selectList(new QueryWrapper<CrmMasTask>()
                .lambda().eq(CrmMasTask::getTaskDate, currentDate)
                .eq(CrmMasTask::getState, UN_FINISH_STATE));

        //完成当条任务后 向 crm_supply 添加任务
        CrmSupplyTask crmSupplyTask = new CrmSupplyTask();
        crmSupplyTask.setEntityCode(entityCode);
        //设置为 未处理
        crmSupplyTask.setState(0);
        crmSupplyTask.setTaskDate(currentDate);
        crmSupplyTask.setFrom(masDto.getSourceName());
        crmSupplyTask.setRemark(masDto.getRemarks());

        //如果 是金融机构 那么 role_id 为6 如果 是城投政府 那么 为 7 如果都是否 那么为 5  => Y 为是
        if (YES.equals(masDto.getIsFinance())) {
            crmSupplyTask.setRoleId(5L);
        } else if (YES.equals(masDto.getCityIb())) {
            crmSupplyTask.setRoleId(6L);
        } else {
            crmSupplyTask.setRoleId(7L);
        }
        //新增任务
        crmSupplyTaskMapper.insert(crmSupplyTask);

        // 查询到所有任务已经完成 修改当日单表
        if (crmMasTasks.size() == 0) {
            iCrmDailyTaskService.saveTask(ROLE_2_ID, FINISH_DAILY_STATE, DateUtil.format(currentDate, "yyyy-MM-dd"));

            // 发送邮件
            String dateNow = DateUtil.format(currentDate, "yyyy-MM-dd");
            CrmDailyTask dailyTask = new CrmDailyTask();
            dailyTask.setTaskDate(currentDate);

            List<CrmSupplyTask> crmSupplyTasks = crmSupplyTaskMapper.selectList(new LambdaQueryWrapper<CrmSupplyTask>().eq(CrmSupplyTask::getTaskDate, dateNow)
                    .between(CrmSupplyTask::getRoleId, 5L, 7L));
            if (CollUtil.isEmpty(crmSupplyTasks)) {
                log.warn("==> 查询角色 3 4 5任务表【crm_supply_task】数据为空!!!!");
                return R.ok(SuccessInfo.SUCCESS.getInfo());
            }
            HashMap<Long, Long> groupingByMap = Maps.newHashMap();
            crmSupplyTasks.stream().collect(Collectors.groupingBy(CrmSupplyTask::getRoleId)).forEach((k, v) -> {
                groupingByMap.put(k, v.stream().count());
            });
            Long role3 = groupingByMap.computeIfPresent(5L, (k, v) -> v != 0 ? v : 0L);
            Long role4 = groupingByMap.computeIfPresent(6L, (k, v) -> v != 0 ? v : 0L);
            Long role5 = groupingByMap.computeIfPresent(7L, (k, v) -> v != 0 ? v : 0L);
            //角色3 角色id为 5L
            if (role3 != 0 && role3 != null) {
                sendEmailService.email(ROLE_3_ID, role3.intValue());
                //无任务为 1 有任务未完成 2
                this.createTask(ROLE_3_ID, 2);
            } else {
                this.createTask(ROLE_3_ID, 1);
            }
            //角色4 角色id为 6L
            if (role4 != 0 && role4 != null) {
                sendEmailService.email(ROLE_4_ID, role4.intValue());
                this.createTask(ROLE_4_ID, 2);
            } else {
                this.createTask(ROLE_4_ID, 1);
            }
            //角色5 角色id为 7L
            if (role5 != 0 && role5 != null) {
                sendEmailService.email(ROLE_5_ID, role5.intValue());
                this.createTask(ROLE_5_ID, 2);
            } else {
                this.createTask(ROLE_5_ID, 1);
            }
        }
        return R.ok(SuccessInfo.SUCCESS.getInfo());
    }

    /**
     * 获取敞口信息 by正杰
     *
     * @return
     */
    @Override
    public R<List<ModelMaster>> getModelMaster() {
        List<ModelMaster> modelMasters = modelMasterMapper.selectList(new QueryWrapper<ModelMaster>());
        return R.ok(modelMasters);
    }

    /**
     * 存value公用方法
     *
     * @param entityCode
     * @param attrId
     * @param value
     */
    public Boolean saveAttrValues(String entityCode, Integer attrId, String value, Boolean insert) {
        BaseMapper<EntityAttrValue> valueBaseMapper = iEntityAttrValueService.getBaseMapper();
        EntityAttrValue entityAttrValue = new EntityAttrValue();
        entityAttrValue.setEntityCode(entityCode).setAttrId(attrId.longValue());
        if (insert) {
            valueBaseMapper.insert(entityAttrValue.setValue(value));
            return false;
        } else {
            entityAttrValue = valueBaseMapper.selectOne(new QueryWrapper<EntityAttrValue>().lambda().eq(EntityAttrValue::getEntityCode, entityCode).eq(EntityAttrValue::getAttrId, attrId));
            if (entityAttrValue == null) {
                return true;
            }
            entityAttrValue.setValue(value);
            valueBaseMapper.updateById(entityAttrValue);
            return false;
        }
    }

    /**
     * 角色2 生成任务
     */
    public void createTask(Integer roleId, Integer taskState) {
        BaseMapper<CrmDailyTask> baseMapper = iCrmDailyTaskService.getBaseMapper();
        CrmDailyTask crmDailyTask = baseMapper.selectOne(new QueryWrapper<CrmDailyTask>().lambda().eq(CrmDailyTask::getTaskDate, DateUtil.format(new Date(), "yyyy-MM-dd")).eq(CrmDailyTask::getTaskRoleType, roleId));
        if(ObjectUtils.isEmpty(crmDailyTask)){
            baseMapper.insert(new CrmDailyTask().setTaskDate(new Date()).setTaskStatus(taskState).setTaskRoleType(roleId.toString()));
        }else{
            crmDailyTask.setTaskStatus(2);
            baseMapper.updateById(crmDailyTask);
        }
    }

    /**
     * 新增一条数据至  entity_financial
     *
     * @param masDto
     */
    @Transactional(rollbackFor = Exception.class)
    void updateEntityFinancial(MasDto masDto) {
        EntityFinancial entityFinancial = entityFinancialService.getBaseMapper().selectOne(new QueryWrapper<EntityFinancial>().lambda().eq(EntityFinancial::getEntityCode, masDto.getEntityCode()));
        if (ObjectUtils.isEmpty(entityFinancial)) {
            entityFinancial = new EntityFinancial();
        }
        entityFinancial.setMince(masDto.getFinanceSegmentation()).setEntityCode(masDto.getEntityCode());
        if (ObjectUtils.isEmpty(entityFinancial.getId())) {
            entityFinancialService.getBaseMapper().insert(entityFinancial);
        } else {
            entityFinancialService.updateById(entityFinancial);
        }
    }

    /**
     * 新增数据至 entity_master
     *
     * @param masDto
     */
    @Transactional(rollbackFor = Exception.class)
    void updateEntityMaster(MasDto masDto) {
        String entityCode = masDto.getEntityCode();
        EntityMaster entityMaster = entityMasterMapper.selectOne(new QueryWrapper<EntityMaster>().lambda().eq(EntityMaster::getEntityCode, entityCode));
        if (ObjectUtils.isEmpty(entityMaster)) {
            entityMaster = new EntityMaster();
        }
        //新增  YY-是否为城投机构
        entityMaster.setEntityCode(entityCode);
        entityMaster.setYyUrban(YES.equals(masDto.getCity()) ? "1" : "0");
        //新增  中诚信-是否为城投机构
        entityMaster.setZhongxinUrban(YES.equals(masDto.getCityZhong()) ? "1" : "0");
        //新增  IB-是否为城投机构
        entityMaster.setIbUrban(YES.equals(masDto.getCityIb()) ? "1" : "0");
        //新增 敞口的code
        entityMaster.setMasterCode(masDto.getMasterCode());
        if (ObjectUtils.isEmpty(entityMaster.getId())) {
            entityMasterMapper.insert(entityMaster);
        } else {
            entityMasterMapper.updateById(entityMaster);
        }
    }

    /**
     * 新增数据至 entity_gov_rel
     *
     * @param masDto
     */
    @Transactional(rollbackFor = Exception.class)
    void updateEntityGovRel(MasDto masDto) {
        String entityCode = masDto.getEntityCode();
        EntityGovRel entityGovRel = entityGovRelMapper.selectOne(new QueryWrapper<EntityGovRel>().lambda().eq(EntityGovRel::getEntityCode, entityCode));
        if (ObjectUtils.isEmpty(entityGovRel)) {
            entityGovRel = new EntityGovRel();
        }
        entityGovRel.setEntityCode(entityCode);
        entityGovRel.setDqGovCode(masDto.getDqGovCode());
        if (ObjectUtils.isEmpty(entityGovRel.getId())) {
            entityGovRelMapper.insert(entityGovRel);
        } else {
            entityGovRelMapper.updateById(entityGovRel);
        }
    }

}
