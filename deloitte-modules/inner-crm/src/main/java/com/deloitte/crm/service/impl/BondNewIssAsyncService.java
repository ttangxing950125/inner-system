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



}
