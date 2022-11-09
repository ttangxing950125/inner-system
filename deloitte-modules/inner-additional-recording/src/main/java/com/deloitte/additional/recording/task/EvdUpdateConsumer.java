package com.deloitte.additional.recording.task;

import com.deloitte.additional.recording.domain.BasEvdData;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author 吴鹏鹏ppp
 * @date 2022/11/10
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "updateEvdTopic",//主题
        consumerGroup = "updateEvdGroup",//消费组
        consumeMode = ConsumeMode.ORDERLY
)
public class EvdUpdateConsumer implements RocketMQListener<BasEvdData> {


    @Override
    public void onMessage(BasEvdData evdData) {
        try {
            //模拟业务逻辑处理中...
            log.info("Receive message: {}  ThreadName: {}", evdData, Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}