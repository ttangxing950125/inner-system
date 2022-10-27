package com.deloitte.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deloitte.common.core.domain.R;
import com.deloitte.crm.domain.CrmSupplyTask;
import com.deloitte.crm.domain.EntityFinancial;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.mapper.CrmSupplyTaskMapper;
import com.deloitte.crm.mapper.EntityFinancialMapper;
import com.deloitte.crm.service.EntityFinancialService;
import com.deloitte.crm.service.ICrmDailyTaskService;
import com.deloitte.crm.service.ICrmSupplyTaskService;
import com.deloitte.crm.service.IEntityInfoService;
import com.deloitte.crm.vo.EntitySupplyMsgBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * (EntityFinancial)表服务实现类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-10-11 17:11:57
 */
@Service("entityFinancialService")
public class EntityFinancialServiceImpl extends ServiceImpl<EntityFinancialMapper, EntityFinancial> implements EntityFinancialService {

    @Autowired
    private EntityFinancialMapper financialMapper;

    @Autowired
    private IEntityInfoService entityInfoService;

    @Autowired
    private ICrmSupplyTaskService crmSupplyTaskService;

    @Autowired
    private CrmSupplyTaskMapper crmSupplyTaskMapper;

    @Autowired
    private ICrmDailyTaskService crmDailyTaskService;
    /**
     * 金融机构根据entityCode补充录入副表信息
     *
     * @return R
     * @author 冉浩岑
     * @date 2022/10/12 9:10
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R addFinEntitySubtableMsg(EntitySupplyMsgBack entitySupplyMsgBack) {


        Integer taskId = entitySupplyMsgBack.getTaskId();
        CrmSupplyTask crmSupplyTask = crmSupplyTaskMapper.selectById(taskId);

        if (!ObjectUtils.isEmpty(crmSupplyTask)&&!ObjectUtils.isEmpty(crmSupplyTask.getId())&&crmSupplyTask.getId()==1){
            return R.fail("已完成的任务，不能重复提交");
        }

        crmSupplyTaskService.completeTaskById(taskId);
        //保存
        EntityFinancial entityFinancial = entitySupplyMsgBack.newEntityFinancial();
        QueryWrapper<EntityFinancial> financialQuery = new QueryWrapper<>();
        Long count = financialMapper.selectCount(financialQuery.lambda().eq(EntityFinancial::getEntityCode, entityFinancial.getEntityCode()));
        if (count>0){
            financialMapper.update(entityFinancial, financialQuery.lambda().eq(EntityFinancial::getEntityCode, entityFinancial.getEntityCode()));
        }else {
            financialMapper.insert(entityFinancial);
        }
        EntityInfo entityInfo = entitySupplyMsgBack.newEntityInfo();
        entityInfoService.updateEntityInfoByEntityCodeWithOutId(entityInfo);
        //检验是否更新每日任务表
        crmDailyTaskService.checkDailyTask(crmSupplyTask);
        //更新任务进度
        crmDailyTaskService.checkDailyTask(crmSupplyTask);
        return R.ok("修改成功");
    }

}
