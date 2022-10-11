package com.deloitte.crm.quartz;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.crm.quartz.service.QuarzRoleTaskService;
import com.google.common.base.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

}
