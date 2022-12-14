package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.constants.Common;
import com.deloitte.crm.domain.CrmSupplyTask;
import com.deloitte.crm.domain.EntityGovRel;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.mapper.CrmSupplyTaskMapper;
import com.deloitte.crm.mapper.EntityGovRelMapper;
import com.deloitte.crm.service.*;
import com.deloitte.crm.vo.EntitySupplyMsgBack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author deloitte
 * @date 2022-09-21
 */
@Service
@Slf4j
public class EntityGovRelServiceImpl implements IEntityGovRelService {
    @Autowired
    private EntityGovRelMapper entityGovRelMapper;
    @Autowired
    private ICrmSupplyTaskService crmSupplyTaskService;
    @Autowired
    private IEntityInfoService entityInfoService;
    @Autowired
    private CrmSupplyTaskMapper crmSupplyTaskMapper;
    @Autowired
    private ICrmDailyTaskService crmDailyTaskService;
    @Autowired
    private EntityCaptureSpeedService entityCaptureSpeedService;
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public EntityGovRel selectEntityGovRelById(Long id) {
        return entityGovRelMapper.selectEntityGovRelById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param entityGovRel 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<EntityGovRel> selectEntityGovRelList(EntityGovRel entityGovRel) {
        return entityGovRelMapper.selectEntityGovRelList(entityGovRel);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param entityGovRel 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertEntityGovRel(EntityGovRel entityGovRel) {
        return entityGovRelMapper.insertEntityGovRel(entityGovRel);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param entityGovRel 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateEntityGovRel(EntityGovRel entityGovRel) {
        return entityGovRelMapper.updateEntityGovRel(entityGovRel);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityGovRelByIds(Long[] ids) {
        return entityGovRelMapper.deleteEntityGovRelByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteEntityGovRelById(Long id) {
        return entityGovRelMapper.deleteEntityGovRelById(id);
    }

    @Override
    public Long getEntityGovCount(String dqCode) {
        log.info("  >>>> 根据 dqCode 查询城投主体数量,dqCode=[{}] <<<<  ",dqCode);
        QueryWrapper<EntityGovRel> govRelQuery = new QueryWrapper<>();
        return entityGovRelMapper.selectCount(govRelQuery.lambda().eq(EntityGovRel::getDqGovCode, dqCode));
    }

    /**
     * 城投机构根据entityCode补充录入副表信息
     *
     * @param entitySupplyMsgBack
     * @return R
     * @author 冉浩岑
     * @date 2022/10/12 9:56
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R addGovEntitySubtableMsg(EntitySupplyMsgBack entitySupplyMsgBack) {
        log.info("  >>>> 城投机构根据entityCode补充录入副表信息,dqCode=[{}] <<<<  ",entitySupplyMsgBack.getEntityCode());
        Integer taskId = entitySupplyMsgBack.getTaskId();
        CrmSupplyTask crmSupplyTask = crmSupplyTaskMapper.selectById(taskId);

         /*//已完成的任务，不允许重复提交
        if (!ObjectUtils.isEmpty(crmSupplyTask)&&!ObjectUtils.isEmpty(crmSupplyTask.getId())&&crmSupplyTask.getId()==1){
            return R.fail("已完成的任务，不能重复提交");
        }*/
        EntityGovRel entityGovRel = entitySupplyMsgBack.newEntityGovRel();
        QueryWrapper<EntityGovRel> govQuery = new QueryWrapper<>();
        long count = entityGovRelMapper.selectCount(govQuery.lambda().eq(EntityGovRel::getEntityCode, entityGovRel.getEntityCode()));
        if (count > 0) {
            entityGovRelMapper.update(entityGovRel, govQuery.lambda().eq(EntityGovRel::getEntityCode, entityGovRel.getEntityCode()));
        } else {
            entityGovRelMapper.insert(entityGovRel);
        }
        EntityInfo entityInfo = entitySupplyMsgBack.newEntityInfo();
        entityInfoService.updateEntityInfoByEntityCodeWithOutId(entityInfo);

        //检验是否更新每日任务表
        crmDailyTaskService.checkDailyTask(crmSupplyTask);

        //更新任务进度
        entityCaptureSpeedService.sendTFFSpeed(crmSupplyTask,entityInfo);
        return R.ok(Common.UPDATE_SUCCESS);
    }

}
