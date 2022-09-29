package com.deloitte.crm.quartz;

import com.deloitte.crm.domain.EntityAttr;
import com.deloitte.crm.service.IEntityAttrService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/9/24
 */
@Component
public class EntityAttrQuartz {

    @Resource
    private IEntityAttrService entityAttrService;

    @PostConstruct
    public void initCache(){
        entityAttrService.cacheAll();
    }

}
