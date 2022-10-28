package com.deloitte.crm.quartz;

import com.deloitte.crm.service.CrmTypeInfoService;
import com.deloitte.crm.service.IEntityAttrService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/10/28/18:31
 * @Description: crm_type_info 数据缓存
 */
@Component
public class CrmTypeInfoQuartz {

    @Resource
    private CrmTypeInfoService crmTypeInfoService;

    @PostConstruct
    public void initCache() {
        crmTypeInfoService.cacheAll();
    }
}
