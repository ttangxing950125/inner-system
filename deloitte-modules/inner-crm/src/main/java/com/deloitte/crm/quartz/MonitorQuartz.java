package com.deloitte.crm.quartz;

import cn.hutool.core.collection.CollUtil;
import com.deloitte.common.core.utils.EmailUtil;
import com.deloitte.crm.domain.EntityInfo;
import com.deloitte.crm.service.IEntityInfoService;
import com.deloitte.crm.service.SendEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 监控数据是否异常
 * @author 吴鹏鹏ppp
 * @date 2022/11/17
 */
@Component
@Slf4j
public class MonitorQuartz {

    @Resource
    private IEntityInfoService entityInfoService;


    /**
     * 0 0 0 * * ?
     * 主体名前后是否有空格的监听
     */
    @Scheduled(cron="0 0 0 * * ? ")
    public void entityNameBlank(){
        List<EntityInfo> entityInfos = entityInfoService.findEntityNameBlank();
        if (CollUtil.isNotEmpty(entityInfos)){
            String blankEntityName = entityInfos.stream().map(EntityInfo::getEntityName).collect(Collectors.joining(","));

            EmailUtil.sendEmail("主体名有空格！！",blankEntityName,"2471485070@qq.com");
        }
    }

}
