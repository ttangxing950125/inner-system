package com.deloitte.crm.config;

import com.deloitte.crm.domain.CrmWindTask;
import com.deloitte.crm.domain.ImportTaskErrorLog;
import com.deloitte.crm.service.ImportTaskErrorLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/11/7
 */
@Component
@Aspect
@Slf4j
public class StrategyImport {

    @Resource
    private ImportTaskErrorLogService taskErrorLogService;

    @Resource
    private ObjectMapper objectMapper;

    @Pointcut("execution(* com.deloitte.crm.strategy.impl..*.doBondImport(..))")
    public void pointCut() {
        //该方法仅用于扫描controller包下类中的方法，而不做任何特殊的处理。
    }

    @AfterThrowing(value = "pointCut()", throwing = "t")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable t) throws JsonProcessingException {
        //类名
        String simpleName = joinPoint.getSignature().getDeclaringType().getSimpleName();

        //导入的数据
        Object importItem = joinPoint.getArgs()[0];

        //taskId
        CrmWindTask windTask = (CrmWindTask) joinPoint.getArgs()[2];
        Integer taskId = windTask.getId();
        //分类名
        String taskCategory = windTask.getTaskCategory();

        //异常信息
        String message = t.getMessage();


        ImportTaskErrorLog errorLog = ImportTaskErrorLog
                .builder()
                .importItem(objectMapper.writeValueAsString(importItem))
                .simpleClassName(simpleName)
                .taskId(taskId)
                .taskCategory(taskCategory)
                .message(message)
                .build();

        //保存
        taskErrorLogService.save(errorLog);
    }

}
