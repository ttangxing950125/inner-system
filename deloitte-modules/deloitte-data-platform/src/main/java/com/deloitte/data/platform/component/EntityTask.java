package com.deloitte.data.platform.component;


import com.deloitte.data.platform.service.IFactorEvidenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

//@Component
public class EntityTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IFactorEvidenceService factorEvidenceService;

    @Autowired
    private RedisTemplate redisTemplate;

    private final static String ENTITY_TASK_LOCK_KEY = "ENTITY_TASK_KEY";

    //@Scheduled(cron = "0/30 * * * * ? ")
    public void invalidation() {
        //直接用UUID生成一个ID
        String requestId = UUID.randomUUID().toString();
        //这里加锁的原因是为了防止 分布式部署时 多个定时任务同时启动，这里只运行一个任务运行即可
        boolean isLocked = redisTemplate.opsForValue().setIfAbsent(ENTITY_TASK_LOCK_KEY, requestId, 10, TimeUnit.MINUTES);
        if (!isLocked) {
            //如果加锁失败
            return;
        }
        //查出所有主体 和公式

    }
}
