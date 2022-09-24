package com.deloitte.crm.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.crm.constants.BondStatus;
import com.deloitte.crm.domain.BondInfo;
import com.deloitte.crm.domain.BondNewIss;
import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.dto.BondInfoDto;
import com.deloitte.crm.mapper.BondNewIssMapper;
import com.deloitte.crm.service.*;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/24
 */
@Component
public class BondNewIssAsyncService {

    @Resource
    private BondNewIssMapper bondNewIssMapper;

    @Resource
    private IBondInfoService bondInfoService;

    @Resource
    private IEntityAttrValueService entityAttrValueService;


    @Resource
    private IEntityBondRelService bondRelService;

    @Resource
    private ICrmMasTaskService crmMasTaskService;

    @Resource
    private IEntityInfoService entityInfoService;

    /**
     * 根据导入的BondNewIss信息，处理bondinfo表和entityattrvalue
     * @param newIss
     * @param timeNow
     * @return 如果这条 newIss 是新增的，返回1
     *         如果这条 newIss 是原有基础上有修改，返回2
     */
    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Future<BondInfoDto> doBondImport(BondNewIss newIss, Date timeNow, CrmWindTask windTask) {
        try {
            //设置任务相关信息进临时表
//            newIss.setImportTime(new Date());
            newIss.setTaskId(windTask.getId());

            Integer resStatus = null;

            //查询债券是否存在
            String shortName = newIss.getBondShortName();

            //查询有没有这个债券
            BondInfo bondInfo = bondInfoService.findByShortName(shortName);
            if (bondInfo==null){
                bondInfo = new BondInfo();
                bondInfo.setBondShortName(shortName);
                resStatus = 1;
            }


            Integer newStatus = judgeBondStatus(bondInfo.getBondStatus(), newIss.getIssStartDate(), newIss.getIssEndDate(), newIss.getIpoDate(), timeNow);
            if (newStatus!=null){
                bondInfo.setBondStatus(newStatus);
            }

            //保存当前债券
            BondInfo newDbBond = bondInfoService.saveOrUpdate(bondInfo);

            //更新当前债券属性
            int updateCount = entityAttrValueService.updateBondAttr(newDbBond.getBondCode(), newIss);
            if (resStatus==null && updateCount>0){
                resStatus = 2;
            }

            BondInfoDto bondInfoDto = new BondInfoDto();
            bondInfoDto.setBondInfo(bondInfo);
            bondInfoDto.setResStatus(resStatus);

            //设置newIss的状态
            newIss.setChangeType(resStatus);

            bondNewIssMapper.insert(newIss);

            //绑定债券和主体关系
            String entityName = newIss.getIssorName();
            bondRelService.bindRel(entityName, bondInfo, newIss, windTask);


            //如果债券状态变为成功上市，创建敞口划分任务
            List<EntityInfo> entityInfos = entityInfoService.findByName(entityName);
            Integer bondStatus = bondInfo.getBondStatus();
            if (Objects.equals(bondStatus, BondStatus.LISTED.getId()) && CollUtil.isNotEmpty(entityInfos)){
                //新敞口划分任务
                crmMasTaskService.createTasks(entityInfos, windTask.getTaskCategory(), windTask.getTaskDate());
            }

            return new AsyncResult(bondInfoDto);
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResult(e);
        }
    }



    /**
     * 判断债券状态
     * @param bondStatus 当前状态
     * @param startDateStr 发行起始日
     * @param endDateStr 发行截止日
     * @param ipoDateStr 上市日期
     * @param timeNow 今天
     * @return
     */
    private Integer judgeBondStatus(Integer bondStatus,String startDateStr, String endDateStr, String ipoDateStr, Date timeNow){
        //发行起始日
        Date startDate = DateUtil.parseDate(startDateStr);
        //发行截止日
        Date endDate = DateUtil.parseDate(endDateStr);
        //上市日期
        Date ipoDate = DateUtil.parseDate(ipoDateStr);

        int compare = DateUtil.compare(startDate, timeNow);
        if (compare>0){
            //当债券的【发行起始日】晚于今天，则记为“等待发行”
            return BondStatus.WAIT.getId();
        }else if (compare==0 && Objects.equals(bondStatus, BondStatus.WAIT.getId())){
            //当债券状态已经是“等待发行”的情况下，在【发行起始日】= 今天 时，改为“正在发行”
            return BondStatus.ISSUE.getId();
        }

        //当债券状态已经是“正在发行”的情况下，在【发行截止日】= 今天 时，改为“已发行待上市”
        if (Objects.equals(bondStatus, BondStatus.ISSUE.getId())
                &&
                DateUtil.compare(endDate, timeNow)==0
        ){
            return BondStatus.WAIT_LIST.getId();
        }

        //当债券状态已经是“已发行待上市”的情况下，在【上市日期】= 今天 时，改为“成功上市”
        if (Objects.equals(bondStatus,BondStatus.WAIT_LIST.getId())
                &&
                DateUtil.compare(ipoDate, timeNow)==0
        ){
            return BondStatus.LISTED.getId();
        }

        return null;
    }

}
