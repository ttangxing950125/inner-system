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
            BondInfo bondInfo = bondInfoService.findByShortName(shortName,Boolean.FALSE);
            if (bondInfo==null){
                bondInfo = new BondInfo();
                bondInfo.setBondShortName(shortName);
                bondInfo.setOriCode(newIss.getTradeCode());
            }

            //看之前有没有导入过这个数据
            List<BondNewIss> bondNewIsses = bondNewIssMapper.findByShortName(shortName);
            if (CollUtil.isEmpty(bondNewIsses)){
                resStatus = 1;
            }


}
