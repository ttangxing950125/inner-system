package com.deloitte.crm.quartz;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.crm.quartz.service.QuarzRoleTaskService;
import com.deloitte.crm.service.IEntityAttrValueService;
import com.google.common.base.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author PenTang
 * @date 2022/09/22 14:21
 */
@Slf4j
@Component
@EnableScheduling
public class QuartzTask {
    @Autowired
    private QuarzRoleTaskService quarzRoleTaskService;

    @Resource
    private IEntityAttrValueService iEntityAttrValueService;

    /**
     * 1.每天创建全部角色的任务，默认状态 无任务（1）
     * 2.创建角色1的详细任务 crm_wind_task
     * 3.将角色1当天的 crm_daily_task 状态改成 2
     *
     * @return void
     * @author penTang
     * @date 2022/9/22 14:22
     */
   @Scheduled(cron = "0 0 0 * * ?" )
    public void StartRuleTask() {
       //当前日期
       String date = DateUtil.getDate();
       //节假日 0=工作日, 1=假日, 2=节日
       try {
           HttpResponse response = HttpRequest.get("https://tool.bitefu.net/jiari/?d=".concat(date)).execute();
           if (Objects.equal("1",response.body()) || Objects.equal("2",response.body())) {
               return;
           }
       }catch (Exception e){

        log.error("e");

       }
        log.info("同步任务开始 =============");
        quarzRoleTaskService.executeQuarzRoleTask();
        log.info("同步任务结束 =============");
    }

    /**
     * 每日凌晨 1 点开始同步entity_attr_value
     * 来自表 entity_master entity_financial entity_gov_rel entity_base_busi_info
     */
    @Async
//    @Scheduled(cron = "0 0 1 * * ?" )
    @Transactional(rollbackFor = Exception.class)
    public void runBatchDataToAttrValue(){
        log.info("=>>  "+ DateUtil.dateTimeNow() +" Attr数据导入开始  <<=");
//        iEntityAttrValueService.runBatchToAttrValue();
        log.info("=>>  "+ DateUtil.dateTimeNow() +" Attr数据导入完成  <<=");
    }

}
