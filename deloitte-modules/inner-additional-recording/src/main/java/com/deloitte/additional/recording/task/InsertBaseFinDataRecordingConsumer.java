package com.deloitte.additional.recording.task;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.deloitte.additional.recording.constants.Common;
import com.deloitte.additional.recording.domain.BaseFinDataRecording;
import com.deloitte.additional.recording.mapper.BaseFinDataRecordingMapper;
import com.deloitte.common.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/26/11:20
 * @Description: 定时任务生成三表一注补录任务。数据资产平台会 通过MQ的方式向补录平台发送消息
 * 补录平台接受消息 1.将数据标准格式化入库
 * 2.生成定时任务
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = Common.BASE_FIN_DATA_RECORDING_TOPIC, consumerGroup = Common.CONSUMER_GROUP, consumeMode = ConsumeMode.ORDERLY, messageModel = MessageModel.CLUSTERING)
public class InsertBaseFinDataRecordingConsumer implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {
    @Resource
    private BaseFinDataRecordingMapper baseFinDataRecordingMappper;

    /**
     * 接受数据资产平台推送消息
     *
     * @param msgData
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void onMessage(String msgData) {
        long start = System.currentTimeMillis();
        log.info(">>>补录平台 处理 数据资产平台 推送基础数据开始>>>>>>:{}",msgData);
        try {
            if (StringUtils.isEmpty(msgData)) {
                log.warn(">>>>消息推送为空 topic={},consumerGroup={}<<<<<<", Common.BASE_FIN_DATA_RECORDING_TOPIC, Common.CONSUMER_GROUP);
                return;
            }
            if (getJSONType(msgData)) {
                log.warn(">>>>数据非标准格式化JSON不做处理 topic={},consumerGroup={}<<<<<<", Common.BASE_FIN_DATA_RECORDING_TOPIC, Common.CONSUMER_GROUP);
                return;
            }
            JSONArray jsonArray = JSONUtil.parseArray(msgData);
            List<BaseFinDataRecording> baseFinDataRecordingLists = jsonArray.toList(BaseFinDataRecording.class);
            for (BaseFinDataRecording baseFinDataRecordingList : baseFinDataRecordingLists) {
                baseFinDataRecordingMappper.insert(baseFinDataRecordingList);
            }
            long end = System.currentTimeMillis();
            log.info(">>>补录平台 处理 数据资产平台 推送基础数据结束>>>耗时:>>:{} ms",(end-start));
        } catch (Exception e) {
            log.error(">>>>补录平台接受数据资产平台消费出现异常:err:{}", e);
            throw new ServiceException("消费异常topic:" + Common.BASE_FIN_DATA_RECORDING_TOPIC);
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.setInstanceName(this.getClass().getSimpleName());
    }

    /**
     * 判断消息类型
     *
     * @param str
     * @return
     */
    private boolean getJSONType(String str) {
        boolean result = false;
        if (StringUtils.isNotBlank(str)) {
            str = str.trim();
            if (str.startsWith("{") && str.endsWith("}")) {
                result = true;
            } else if (str.startsWith("[") && str.endsWith("]")) {
                result = true;
            }
        }
        return result;
    }
}
