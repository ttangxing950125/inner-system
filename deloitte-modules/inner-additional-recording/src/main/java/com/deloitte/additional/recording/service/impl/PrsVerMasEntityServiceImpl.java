package com.deloitte.additional.recording.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.additional.recording.constants.Common;
import com.deloitte.additional.recording.domain.*;
import com.deloitte.additional.recording.mapper.ModelTaskDetailsMapper;
import com.deloitte.additional.recording.mapper.PrsVerMasEntityMapper;
import com.deloitte.additional.recording.service.*;
import com.deloitte.additional.recording.task.CrmEntityPullRunnable;
import com.deloitte.additional.recording.util.ApplicationContextHolder;
import com.deloitte.additional.recording.util.MetaSecurity;
import com.deloitte.common.core.domain.R;
import com.deloitte.common.core.exception.ServiceException;
import com.deloitte.common.core.utils.JwtUtil;
import com.deloitte.common.core.utils.uuid.UUID;
import com.deloitte.common.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * (PrsVerMasEntity)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-07 02:00:40
 */
@Service("prsVerMasEntityService")
@Slf4j
public class PrsVerMasEntityServiceImpl extends ServiceImpl<PrsVerMasEntityMapper, PrsVerMasEntity> implements PrsVerMasEntityService {

    @Resource
    private PrsProjectVersionsService versionsService;

    @Resource
    private SynTableService synTableService;

    @Resource
    private PrsVerMasEntityMapper prsVerMasEntityMapper;

    @Resource
    private RedisService redisService;

    @Resource
    private ModelTaskService modelTaskService;

    @Resource
    private ThreadPoolTaskExecutor commonTaskExecutor;

    @Resource
    private PrsVersionMasterService versionMasterService;

    @Resource
    private PrsQualDataService qualDataService;

    @Resource
    private BasEvdDataService evdDataService;

    @Resource
    private ModelTaskDetailsMapper taskDetailsMapper;

    @Override
    public R updateIncrStatus(Integer id, Integer stauts) {
        LambdaUpdateWrapper<PrsVerMasEntity> updateWrapper = new LambdaUpdateWrapper();
        updateWrapper.set(PrsVerMasEntity::getIncrStatus,stauts);
        updateWrapper.eq(PrsVerMasEntity::getId,id);
        this.update(updateWrapper);
        String message = stauts==1?"启用成功":"禁用成功";
        return R.ok(message);
    }

    /**
     * 拉取crm全量主体
     *
     * @param versionId
     * @return
     */
    @Override
    public ModelTask pullCrmEntity(Integer versionId) {
        //查询当前版本，以及版本对应的syn_table
        PrsProjectVersions versions = versionsService.getById(versionId);
        if (versions==null){
            throw new ServiceException("找不到对应的版本:"+versionId);
        }
        //syn_table
        SynTable synTable = synTableService.getById(versions.getSynId());
        if (synTable==null){
            throw new ServiceException("没有配置推送相关配置");
        }

        //生成任务
        ModelTask task = ModelTask.builder()
                .taskNo(UUID.fastUUID().toString(true))
                .taskType(1)
                .created(new Date())
                .taskYear(Integer.valueOf(versions.getTimeValue()))
                .build();

        boolean hasCondition = synTable.judgeHasCondition();
        if (hasCondition){
            synTable.setHasCondition(hasCondition);
        }

        //查询crm主体
        List<PrsVerMasEntity> crmEntityRels = prsVerMasEntityMapper.findCrmEntityRel(
                versions.getName(),
                PrsVerMasEntityMapper.META_Id,
                versions.getId(),
                synTable
        );


        if (CollUtil.isEmpty(crmEntityRels)){
            throw new ServiceException("crm没有该客户的配置");
        }

        log.info(">>>>>从crm查询到{}个主体<<<<", crmEntityRels.size());

        HashMap<String, Integer> taskSpeedMap = new HashMap<>(4);
        taskSpeedMap.put("total", crmEntityRels.size());
        taskSpeedMap.put("success", 0);
        taskSpeedMap.put("completed", 0);
        taskSpeedMap.put("fail", 0);
        //创建redis
        String taskNo = Common.CRM_PULL_TASK+":"+task.getTaskNo();
        redisService.setCacheMap(taskNo, taskSpeedMap);
        redisService.expire(taskNo,24, TimeUnit.HOURS);

        //创建多线程任务
        List<List<PrsVerMasEntity>> splitRels = CollUtil.split(crmEntityRels, 20);

        task.setTaskTotal(crmEntityRels.size());
//                .setCreater(MetaSecurity.getLoginUser().getId().toString());

        for (List<PrsVerMasEntity> list : splitRels) {
            commonTaskExecutor.execute(new CrmEntityPullRunnable(list, task));
//            new CrmEntityPullRunnable(list, task).run();
        }


        modelTaskService.save(task);

        return task;
    }

    /**
     * 保存主体关联关系
     *
     * @param verMasEntities
     * @return
     */
    @Override
    public boolean saveVerMasEntities(List<PrsVerMasEntity> verMasEntities, ModelTask modelTask) {
        PrsVerMasEntityService service = ApplicationContextHolder.getBean(PrsVerMasEntityService.class);

        verMasEntities.forEach(item->{
            service.saveVerMasEntity(item, modelTask);
        });

        return true;
    }

    /**
     * 保存主体关联关系
     *
     * @param verMasEntity
     * @param task
     * @author wpp
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveVerMasEntity(PrsVerMasEntity verMasEntity, ModelTask task) {
        HashOperations<String, String, Integer> opsForHash = redisService.redisTemplate.opsForHash();
        ModelTaskDetails taskDetails = null;

        try {
            PrsVerMasEntityService service = ApplicationContextHolder.getBean(PrsVerMasEntityService.class);

            //这里面会保存关联关系，保存qual_data evd_data
            service.saveVerMasEntity(verMasEntity, task.getTaskYear());

            opsForHash.increment(Common.CRM_PULL_TASK+":"+task.getTaskNo(),"success",1);
        } catch (Exception e) {
            e.printStackTrace();
            opsForHash.increment(Common.CRM_PULL_TASK+":"+task.getTaskNo(),"fail",1);

            //创建失败任务
            taskDetails = ModelTaskDetails.builder()
                    .taskFlag(false)
                    .taskType(task.getTaskType())
                    .taskNo(task.getTaskNo())
                    .msg(e.getMessage())
                    .build();

        }finally {
            Long completed = opsForHash.increment(Common.CRM_PULL_TASK + ":" + task.getTaskNo(), "completed",1);
            Integer total = opsForHash.get(Common.CRM_PULL_TASK + ":" + task.getTaskNo(), "total");
            log.info(">>>>>>已完成{},总数量{}<<<<<",completed,total);

            //如果有异常，记录异常详情
            if (taskDetails!=null){
                taskDetails.setTaskNumber(completed.intValue());
                taskDetailsMapper.insert(taskDetails);
            }

            if (Objects.equals(completed.intValue(), total)){
                //全部任务完成
                Integer success = opsForHash.get(Common.CRM_PULL_TASK + ":" + task.getTaskNo(), "success");
                Integer fail = opsForHash.get(Common.CRM_PULL_TASK + ":" + task.getTaskNo(), "fail");
                //任务完成
                task.setFinishTime(new Date());
                task.setFailTotal(fail);
                task.setSuccessTotal(success);

                modelTaskService.updateById(task);
            }

        }

        return true;
    }

    /**
     * 保存主体关联关系
     * @param verMasEntity
     * @return
     * @author wpp
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveVerMasEntity(PrsVerMasEntity verMasEntity, Integer year) {
        //查询关联关系是否存在了
        Integer versionId = verMasEntity.getVersionId();
        String modelCode = verMasEntity.getModelCode();
        String entityCode = verMasEntity.getEntityCode();
        PrsVerMasEntity dbRel = baseMapper.findByVersionMasterEntity(versionId, modelCode, entityCode);

        //查询version_master_id
        PrsVersionMaster versionMaster = versionMasterService.findByVersionMaster(versionId, modelCode);

        //不存在就新增
        if (dbRel==null){

            verMasEntity.setVerMasId(versionMaster.getId());
            this.save(verMasEntity);
            log.info(">>>>>新增关联关系,{},{},{}<<<<<",versionId,modelCode,entityCode);
        }else if (!dbRel.getStatus()){
            //存在则修改状态
            dbRel.setStatus(true);
            this.updateById(dbRel);
        }

        //绑定指标
        qualDataService.bindQualData(versionMaster, entityCode, year);

        //绑定evd
        evdDataService.bindEvdData(versionMaster, entityCode, year);

        return true;
    }
}
