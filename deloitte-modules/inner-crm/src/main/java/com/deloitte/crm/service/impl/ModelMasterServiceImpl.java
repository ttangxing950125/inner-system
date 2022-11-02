package com.deloitte.crm.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.util.DateUtils;
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
        log.info("  =>> 角色 2 开始查询 {} 任务的相关信息 <<=  ",id);
        MasDto masDto = new MasDto();
        CrmMasTask crmMasTask = Optional.ofNullable(iCrmMasTaskService.getBaseMapper().selectOne(new QueryWrapper<CrmMasTask>().lambda().eq(CrmMasTask::getId, id))).orElseThrow(() -> new ServiceException(BadInfo.VALID_EMPTY_TARGET.getInfo()));
        EntityInfo entityInfo = Optional.ofNullable(iEntityInfoService.getBaseMapper().selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityCode, crmMasTask.getEntityCode()).eq(EntityInfo::getStatus, 1))).orElseThrow(() -> new ServiceException(BadInfo.VALID_EMPTY_TARGET.getInfo()));
        String entityCode = entityInfo.getEntityCode();
        String entityName = entityInfo.getEntityName();
        //基础信息展示
        masDto.setEntityName(entityName).setCreditCode(entityInfo.getCreditCode()).setSource(crmMasTask.getSourceName()).setWind(entityInfo.getWindMaster()).setShenWan(entityInfo.getShenWanMaster());
        //当企业为金融机构的时候 去查entity_financial 中的数据
        if(!ObjectUtils.isEmpty(entityInfo.getFinance())&&entityInfo.getFinance()==1){
            log.info("  =>> 查询到主体信息 {} 鉴定为金融机构 <<=  ", entityName);
            EntityFinancial entityFinancial = entityFinancialService.getBaseMapper().selectOne(new QueryWrapper<EntityFinancial>().lambda().eq(EntityFinancial::getEntityCode, entityCode));
            if(!ObjectUtils.isEmpty(entityFinancial)){
                masDto.setIsFinance("Y").setFinanceSegmentation(entityFinancial.getMince());
            }else{ log.warn("  =>> 角色 2 发现主体数据 {} 的 金融机构关联数据表值为空 <<=  ", entityName);}
        }else{
            masDto.setIsFinance("N");
        }
        EntityMaster entityMaster = entityMasterMapper.selectOne(new QueryWrapper<EntityMaster>().lambda().eq(EntityMaster::getEntityCode, entityCode));
        if(!ObjectUtils.isEmpty(entityMaster)){
            log.info("  =>> 查询到主体信息 {} 已经被划过敞口，拥有敞口信息 <<=  ", entityName);
            //城投YY
            String yyUrban = entityMaster.getYyUrban();
            if(ObjectUtils.isEmpty(yyUrban)||"0".equals(yyUrban)){masDto.setCity("N");}else{masDto.setCity("Y");}
            //中诚信
            String zhongxinUrban = entityMaster.getZhongxinUrban();
            if(ObjectUtils.isEmpty(zhongxinUrban)||"0".equals(zhongxinUrban)){masDto.setCityZhong("N");}else{masDto.setCityZhong("Y");}
            //查询是否是城投机构
            String ibUrban = entityMaster.getIbUrban();
            if(!ObjectUtils.isEmpty(ibUrban)&&"1".equals(ibUrban)){
                log.info("  =>> 查询到主体信息 {} 鉴定为城投机构(IB) <<=  ", entityName);
                masDto.setCityIb("Y");
                EntityGovRel entityGovRel = entityGovRelMapper.selectOne(new QueryWrapper<EntityGovRel>().lambda().eq(EntityGovRel::getEntityCode, entityCode));
                if(!ObjectUtils.isEmpty(entityGovRel)){
                    String dqGovCode = entityGovRel.getDqGovCode();
                    masDto.setDqGovCode(dqGovCode);
                    GovInfo govInfo = govInfoMapper.selectOne(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getDqGovCode, dqGovCode));
                        if(!ObjectUtils.isEmpty(govInfo)){
                            GovNode tree = this.getTree(new GovNode().setGovName(govInfo.getGovName()), govInfo);
                            masDto.setGovNode(tree);
                        }
                }else{log.warn("  =>> 角色 2 发现主体数据 {} 的 敞口关联数据表值为空 <<=  ", entityName);}
            }
            //敞口划分信息
            String masterCode = entityMaster.getMasterCode();
            ModelMaster modelMaster = Optional.ofNullable(modelMasterMapper.selectOne(new QueryWrapper<ModelMaster>().lambda().eq(ModelMaster::getMasterCode, masterCode))).orElse(new ModelMaster());
            masDto.setMasterCode(modelMaster.getMasterName());
        }

        return R.ok(masDto,SuccessInfo.SUCCESS.getInfo());
    }

    /** 组装 树形结构菜单 */
    public GovNode getTree(GovNode govNode,GovInfo govInfo){
        if(!ObjectUtils.isEmpty(govInfo.getPreGovCode())){
            GovInfo parent = govInfoMapper.selectOne(new QueryWrapper<GovInfo>().lambda().eq(GovInfo::getDqGovCode, govInfo.getPreGovCode()));
            GovNode node = new GovNode().setGovName(govNode.getGovName());
            govNode.setChildren(node);
            govNode.setGovName(parent.getGovName());
            return this.getTree(govNode,parent);
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


    /** 前端展示中 Y 表示 1*/
    private final String YES = "Y";

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

        CrmMasTask crmMasTask = Optional.ofNullable(iCrmMasTaskService.getBaseMapper().selectOne(new QueryWrapper<CrmMasTask>().lambda().eq(CrmMasTask::getId, masDto.getId()))).orElseThrow(() -> new ServiceException(BadInfo.EMPTY_TASK_TABLE.getInfo()));
        updateEntityMaster(masDto.setRemarks(crmMasTask.getRemarks()));
        log.info("  =>> 修改一条数据至 entity_master <<=  ");

        if (YES.equals(masDto.getCityIb())){ updateEntityGovRel(masDto); log.info("  =>> 修改一条数据至 entity_gov_info <<=  ");}

        //更改当条信息任务状态
        Date currentDate = iCrmMasTaskService.finishTask(masDto.getId(), SecurityUtils.getUsername());
        log.info("  =>> 完成任务 taskId = " + masDto.getId() + " <<=  ");

        // 查看当日任务情况 未处理的 UN_FINISH_STATE 0-未处理
        List<CrmMasTask> crmMasTasks = iCrmMasTaskService.getBaseMapper().selectList(new QueryWrapper<CrmMasTask>()
                .lambda().eq(CrmMasTask::getTaskDate, DateUtils.format(currentDate,"yyyy-MM-dd"))
                .eq(CrmMasTask::getState, 0));

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
            //修改每日任务状态
            this.editeDailyTaskByRoleTwo(5);
        } else if (YES.equals(masDto.getCityIb())) {
            crmSupplyTask.setRoleId(6L);
            this.editeDailyTaskByRoleTwo(6);
        } else {
            crmSupplyTask.setRoleId(7L);
            this.editeDailyTaskByRoleTwo(7);
        }
        //新增任务
        crmSupplyTaskMapper.insert(crmSupplyTask);

        // 查询到所有任务已经完成 修改当日单表
        if (crmMasTasks.size() == 0) {
            // 任务完成状态为 3
            iCrmDailyTaskService.saveTask(4, 3, DateUtil.format(currentDate, "yyyy-MM-dd"));

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
            if (!ObjectUtils.isEmpty(role3)&&role3!=0) {
                sendEmailService.email(5, role3.intValue(),dateNow);
                //无任务为 1 有任务未完成 2
                this.createTask(5, 2);
            } else {
                this.createTask(5, 1);
            }
            //角色4 角色id为 6L
            if (!ObjectUtils.isEmpty(role4)&&role4!=0) {
                sendEmailService.email(6, role4.intValue(),dateNow);
                this.createTask(6, 2);
            } else {
                this.createTask(6, 1);
            }
            //角色5 角色id为 7L
            if (!ObjectUtils.isEmpty(role5)&&role5!=0) {
                sendEmailService.email(7, role5.intValue(),dateNow);
                this.createTask(7, 2);
            } else {
                this.createTask(7, 1);
            }
        }
        return R.ok(SuccessInfo.SUCCESS.getInfo());
    }
    /** 修改每日任务表 角色2 */
    public void editeDailyTaskByRoleTwo(Integer roleId){
        CrmDailyTask crmDailyTask = iCrmDailyTaskService.getBaseMapper().selectOne(new QueryWrapper<CrmDailyTask>().lambda().eq(CrmDailyTask::getTaskDate,DateUtil.format(new Date(), "yyyy-MM-dd")).eq(CrmDailyTask::getTaskRoleType, roleId));
        if(ObjectUtils.isEmpty(crmDailyTask)){iCrmDailyTaskService.getBaseMapper().insert(new CrmDailyTask().setTaskStatus(2).setTaskDate(new Date()).setTaskRoleType(roleId.toString()));}
        else{crmDailyTask.setTaskStatus(2);iCrmDailyTaskService.getBaseMapper().updateById(crmDailyTask);}
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
     * @param roleId 5-角色3 6-角色4 7-角色5
     * @param taskState 1- 没有任务  2-有任务未完成
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
        entityMaster.setRemarks(masDto.getRemarks());
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
