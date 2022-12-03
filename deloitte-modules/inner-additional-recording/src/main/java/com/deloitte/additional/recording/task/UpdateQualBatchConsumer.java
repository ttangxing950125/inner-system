package com.deloitte.additional.recording.task;

import com.deloitte.additional.recording.constants.Common;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

/**
 * 批量更新
 *
 * @author 吴鹏鹏ppp
 * @date 2022/11/10
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = Common.QUAL_CODE_FRONT, consumerGroup = Common.CONSUMER_GROUP, consumeMode = ConsumeMode.ORDERLY)
public class UpdateQualBatchConsumer implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {

    /**
     * 对某个指标进行了批量更新，可能是导入等。。。
     *
     * @param qualCode
     */
    @Override
    public void onMessage(String qualCode) {
        try {
            //模拟业务逻辑处理中...
            log.info("更新数据汇总qual情况");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * MQ在启动时，会自动创建消费者组去订阅对应的Topic,在同一个进程内,注册消费者组的时候会将消费者组作为key，消费者作为value存储到消费者缓存Map中
     * 因此在同一个客户端实例中使用相同的消费组会提示 The consumer group has been created before, specify another name please
     *
     * @param defaultMQPushConsumer
     * @see https://blog.csdn.net/uniquewonderq/article/details/103206574
     */
    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.setInstanceName(this.getClass().getSimpleName());
    }
}