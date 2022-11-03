package com.deloitte.crm.quartz;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.nacos.shaded.com.google.common.base.Objects;
import com.deloitte.common.core.utils.DateUtil;
import com.deloitte.common.core.utils.EmailUtil;
import com.deloitte.crm.domain.EntityAttrValue;
import com.deloitte.crm.quartz.service.QuarzRoleTaskService;
import com.deloitte.crm.service.EntityAttrValueRunBatchTask;
import com.deloitte.crm.service.IBondInfoService;
import com.deloitte.crm.service.ProductsCoverService;
import com.deloitte.crm.service.StockCnInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * @author PenTang
 * @date 2022/09/22 14:21
 */
@Slf4j
@Component
@EnableScheduling
public class QuartzTask implements ApplicationContextAware {
    @Autowired
    private QuarzRoleTaskService quarzRoleTaskService;

    private Collection<EntityAttrValueRunBatchTask> entityAttrValueRunBatchTasks;

    @Resource
    private ProductsCoverService productsCoverService;

    @Resource
    private IBondInfoService bondInfoService;

    @Resource
    private StockCnInfoService stockCnInfoService;
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
    public void startRuleTask() {
       //当前日期
       String date = DateUtil.getDate();
       //节假日 0=工作日, 1=假日, 2=节日
       try {
           HttpResponse response = HttpRequest.get("https://tool.bitefu.net/jiari/?d=".concat(date)).execute();
           if (Objects.equal("1",response.body()) || Objects.equal("2",response.body())) {
               return;
           }
       }catch (Exception e){
           ArrayList<String> EamilArrayList = new ArrayList<>();
           EamilArrayList.add("1033166542@qq.com");
           EamilArrayList.add("2471485070@qq.com");
           String errorInfoFromException = getErrorInfoFromException(e);
           for (String s : EamilArrayList) {
               EmailUtil.sendTemplateEmail("每日定时任务跑批异常", errorInfoFromException,s);
           }

            log.error("e");

        }
        log.info("同步任务开始 =============");
        quarzRoleTaskService.executeQuarzRoleTask();
        log.info("同步任务结束 =============");
    }

    /**
     * 每日中午 12 点开始同步entity_attr_value
     * 来自表 entity_master entity_financial entity_gov_rel entity_base_busi_info
     * {@link EntityAttrValue}
     */
    @Async
    @Scheduled(cron = "0 0 12 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void runBatchDataToAttrValue() {
        log.info("=>>  " + DateUtil.dateTimeNow() + " Attr数据导入开始  <<=");
        entityAttrValueRunBatchTasks.forEach(EntityAttrValueRunBatchTask::runBatchData);
        log.info("=>>  " + DateUtil.dateTimeNow() + " Attr数据导入完成  <<=");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, EntityAttrValueRunBatchTask> beans = applicationContext.getBeansOfType(EntityAttrValueRunBatchTask.class);
        entityAttrValueRunBatchTasks = beans.values();
    }


    /**
     * 覆盖规则
     *
     * @return void
     * @author penTang
     * @date 2022/10/28 11:08
    */
    @Scheduled(cron = "0 0 1 * * ?")
    public void  CoverRulePro(){
        log.info("=>> "+ DateUtil.dateTimeNow()+"覆盖跑批开始");
        productsCoverService.CoverRule();
        log.info("=>> "+ DateUtil.dateTimeNow()+"覆盖跑批结束");
    }
    /**
     * 债券退市检测跑批
     *
     * @return void
     * @author 冉浩岑
     * @date 2022/11/3 10:35
    */
//    @Scheduled(cron = "30 0 0 * * ?")
    @Scheduled(cron = "0 1 14 * * ?")
    public void checkBondStatus(){
        log.info("=>> "+ DateUtil.dateTimeNow()+"债券退市检测跑批开始");
        bondInfoService.checkBondStatus();
        log.info("=>> "+ DateUtil.dateTimeNow()+"债券退市检测跑批结束");
    }
    /**
     * 股票退市检测跑批
     *
     * @return void
     * @author 冉浩岑
     * @date 2022/11/3 10:35
     */
    @Scheduled(cron = "30 0 0 * * ?")
    public void checkStockStatus(){
        log.info("=>> "+ DateUtil.dateTimeNow()+"股票退市检测跑批开始");
        stockCnInfoService.checkStockStatus();
        log.info("=>> "+ DateUtil.dateTimeNow()+"股票退市检测跑批结束");
    }

    //转换方法
    /**
     * 异常信息转String
     * @param e
     * @return
     */
    public String getErrorInfoFromException(Exception e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            sw.close();
            pw.close();
            return "\r\n" + sw.toString() + "\r\n";
        } catch (Exception e2) {
            return "ErrorInfoFromException";
        }
    }



}
