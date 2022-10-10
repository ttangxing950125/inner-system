package com.deloitte.crm.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.security.utils.SecurityUtils;
import com.deloitte.crm.constants.BadInfo;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.constants.SuccessInfo;
import com.deloitte.crm.domain.*;
import com.deloitte.crm.dto.AttrValueMapDto;
import com.deloitte.crm.dto.MasDto;
import com.deloitte.crm.mapper.*;
import com.deloitte.crm.service.*;
import com.deloitte.crm.vo.ModelMasterInfoVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author deloitte
 * @date 2022-09-21
 */
@Service
@AllArgsConstructor
public class ModelMasterServiceImpl implements IModelMasterService 
{
    private ModelMasterMapper modelMasterMapper;

    private ICrmMasTaskService iCrmMasTaskService;

    private ICrmDailyTaskService iCrmDailyTaskService;

    private IEntityInfoService iEntityInfoService;

    private IEntityAttrValueService iEntityAttrValueService;

    private EntityAttrIntypeService entityAttrIntypeService;

    private EntityMasterMapper entityMasterMapper;

    private EntityGovRelMapper entityGovRelMapper;

    private CrmSupplyTaskMapper crmSupplyTaskMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public ModelMaster selectModelMasterById(Long id)
    {
        return modelMasterMapper.selectModelMasterById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param modelMaster 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ModelMaster> selectModelMasterList(ModelMaster modelMaster)
    {
        return modelMasterMapper.selectModelMasterList(modelMaster);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param modelMaster 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertModelMaster(ModelMaster modelMaster)
    {
        return modelMasterMapper.insertModelMaster(modelMaster);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param modelMaster 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateModelMaster(ModelMaster modelMaster)
    {
        return modelMasterMapper.updateModelMaster(modelMaster);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteModelMasterByIds(Long[] ids)
    {
        return modelMasterMapper.deleteModelMasterByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteModelMasterById(Long id)
    {
        return modelMasterMapper.deleteModelMasterById(id);
    }


    /**
     * 敞口划分 选中单行开始工作 传入id后返回窗口 by正杰
     * @param id
     * @return
     */
    @Override
    public R<ModelMasterInfoVo> getTable(Integer id) {
        ModelMasterInfoVo modelMasterInfoVo = new ModelMasterInfoVo();
        Map<String, AttrValueMapDto> attrs = new HashMap<>();
        CrmMasTask crmMasTask = iCrmMasTaskService.getBaseMapper().selectOne(new QueryWrapper<CrmMasTask>().lambda().eq(CrmMasTask::getId, id));
        Assert.notNull(crmMasTask, BadInfo.VALID_EMPTY_TARGET.getInfo());
        String entityCode = crmMasTask.getEntityCode();
        EntityInfo entityInfo = iEntityInfoService.getBaseMapper().selectOne(new QueryWrapper<EntityInfo>().lambda().eq(EntityInfo::getEntityCode, entityCode));
        Assert.notNull(entityInfo, BadInfo.VALID_EMPTY_TARGET.getInfo());
        //数据组装
        modelMasterInfoVo.setEntityName(entityInfo.getEntityName()).setCreditCode(entityInfo.getCreditCode()).setSource(crmMasTask.getSourceName());
        //wind行业划分
        attrs.putAll(iEntityAttrValueService.findAttrValue(entityCode,652));
        //申万行业划分
        attrs.putAll(iEntityAttrValueService.findAttrValue(entityCode,650));
        return R.ok(modelMasterInfoVo.setAttrs(attrs));
    }

    /**
     * 获取金融细分领域多选框
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

    private final Integer TASK_ROLE_TWO = 4;

    private final String YES = "Y";

    /**
     * 敞口划分 保存并提交
     * @param masDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R insert(MasDto masDto) {
        String entityCode = masDto.getEntityCode();
        BaseMapper<EntityAttrValue> valueBaseMapper = iEntityAttrValueService.getBaseMapper();

        //修改 wind 行业 id 652
        Boolean wind = this.saveAttrValues(entityCode,652,masDto.getWind(),false);
        if(wind){return R.fail(BadInfo.VALID_EMPTY_TARGET.getInfo());}

        //修改 申万 id 650
        Boolean shenWan = this.saveAttrValues(entityCode,650,masDto.getShenWan(),false);
        if(shenWan){return R.fail(BadInfo.VALID_EMPTY_TARGET.getInfo());}

        //新增 是否为金融机构 id 640
        Boolean finace = this.saveAttrValues(entityCode, 640, masDto.getIsFinance(), true);
        if(finace){return R.fail(BadInfo.ERROR_SYSTEM_BUSY.getInfo());}

        // 新增 金融细分领域 id 656
        Boolean financeSegmentation = this.saveAttrValues(entityCode, 656, masDto.getFinanceSegmentation(), true);
        if(financeSegmentation){return R.fail(BadInfo.ERROR_SYSTEM_BUSY.getInfo());}

        //新增  YY-是否为城投机构 id 644
        Boolean city = this.saveAttrValues(entityCode, 644, masDto.getCity(), true);
        if(city){return R.fail(BadInfo.ERROR_SYSTEM_BUSY.getInfo());}

        //新增  中诚信-是否为城投机构 id 645
        Boolean cityZhong = this.saveAttrValues(entityCode, 644, masDto.getCityZhong(), true);
        if(cityZhong){return R.fail(BadInfo.ERROR_SYSTEM_BUSY.getInfo());}

        //新增  IB-是否为城投机构 id 642
        Boolean cityIb = this.saveAttrValues(entityCode, 644, masDto.getCityIb(), true);
        if(cityIb){return R.fail(BadInfo.ERROR_SYSTEM_BUSY.getInfo());}

        //新增 德勤政府code
        EntityGovRel entityGovRel = new EntityGovRel();
        entityGovRel.setEntityCode(entityCode).setDqGovCode(masDto.getDqGovCode());
        entityGovRelMapper.insertEntityGovRel(entityGovRel);

        //新增 敞口的code
        EntityMaster entityMaster = new EntityMaster();
        entityMaster.setEntityCode(entityCode).setMasterCode(masDto.getMasterCode()).setUpdate(new Date());
        entityMasterMapper.insertEntityMaster(entityMaster);

        //更改当条信息任务状态
        BaseMapper<CrmMasTask> mapper = iCrmMasTaskService.getBaseMapper();
        CrmMasTask crmMasTask = mapper.selectOne(new QueryWrapper<CrmMasTask>()
                .lambda().eq(CrmMasTask::getId,masDto.getId()));
        if(crmMasTask==null){ return R.fail(BadInfo.VALID_EMPTY_TARGET.getInfo());}
        if(UN_FINISH_STATE.equals(crmMasTask.getState())){return R.fail(BadInfo.EXITS_TASK_FINISH.getInfo());}
        //添加修改人
        String username = SecurityUtils.getUsername();
        crmMasTask.setHandleUser(username);

        // 修改状态
        crmMasTask.setState(FINISH_STATE);
        mapper.updateById(crmMasTask);

        // 查看当日任务情况
        List<CrmMasTask> crmMasTasks = mapper.selectList(new QueryWrapper<CrmMasTask>()
                .lambda().eq(CrmMasTask::getTaskDate, crmMasTask.getTaskDate())
                .eq(CrmMasTask::getState, UN_FINISH_STATE)
        );
        // 查询到所有任务已经完成 修改当日单表
        if(crmMasTasks.size()==0){
            CrmDailyTask crmDailyTask = iCrmDailyTaskService.getBaseMapper()
                    .selectOne(new QueryWrapper<CrmDailyTask>()
                            .lambda().eq(CrmDailyTask::getTaskDate, crmMasTask.getTaskDate())
                            .eq(CrmDailyTask::getTaskRoleType,TASK_ROLE_TWO));
            if(crmDailyTask==null){ return R.fail(BadInfo.EMPTY_TASK_TABLE.getInfo()); }
            //当日任务完成状态为 3
            crmDailyTask.setTaskStatus(FINISH_DAILY_STATE);
            iCrmDailyTaskService.getBaseMapper().updateById(crmDailyTask);
        }

        //完成当条任务后 向 crm_supply 添加任务
        CrmSupplyTask crmSupplyTask = new CrmSupplyTask();
        crmSupplyTask.setEntityCode(entityCode);
        crmSupplyTask.setTaskDate(crmMasTask.getTaskDate());
        crmSupplyTask.setFrom(masDto.getSourceName());
        crmSupplyTask.setRemark(masDto.getRemarks());

        //如果 是金融机构 那么 role_id 为6 如果 是城投政府 那么 为 7 如果都是否 那么为 5  => Y 为是
        if(YES.equals(masDto.getIsFinance())){
            crmSupplyTask.setRoleId(6L);
        }else if(YES.equals(masDto.getCityIb())){
            crmSupplyTask.setRoleId(7L);
        }else {
            crmSupplyTask.setRoleId(5L);
        }
        //新增任务
        crmSupplyTaskMapper.insert(crmSupplyTask);
        return R.ok();
    }

    /**
     * 获取敞口信息 by正杰
     * @return
     */
    @Override
    public R<List<ModelMaster>> getModelMaster() {
        List<ModelMaster> modelMasters = modelMasterMapper.selectList(new QueryWrapper<ModelMaster>());
        return R.ok(modelMasters);
    }

    /**
     * 存value公用方法
     * @param entityCode
     * @param attrId
     * @param value
     */
    public Boolean saveAttrValues(String entityCode,Integer attrId,String value,Boolean insert){
        BaseMapper<EntityAttrValue> valueBaseMapper = iEntityAttrValueService.getBaseMapper();
        EntityAttrValue entityAttrValue = new EntityAttrValue();
        entityAttrValue.setEntityCode(entityCode).setAttrId(attrId.longValue());
        if(insert){
            valueBaseMapper.insert(entityAttrValue.setValue(value));
            return false;
        }else{
            entityAttrValue = valueBaseMapper.selectOne(new QueryWrapper<EntityAttrValue>().lambda().eq(EntityAttrValue::getEntityCode, entityCode).eq(EntityAttrValue::getAttrId, attrId));
            if(entityAttrValue==null){return true;}
            entityAttrValue.setValue(value);
            valueBaseMapper.updateById(entityAttrValue);
            return false;
        }
    }

}
