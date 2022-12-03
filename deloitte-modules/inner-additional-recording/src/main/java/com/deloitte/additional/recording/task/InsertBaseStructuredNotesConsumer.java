package com.deloitte.additional.recording.task;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenjiang
 * @Date: 2022/11/26/16:59
 * @Description:
 */

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.deloitte.additional.recording.constants.Common;
import com.deloitte.additional.recording.domain.BaseFinDataRecording;
import com.deloitte.additional.recording.domain.BaseStructuredNotes;
import com.deloitte.additional.recording.mapper.BaseStructuredNotesMapper;
import com.deloitte.common.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
@RocketMQMessageListener(topic = Common.STRUCTURED_NOTES_TOPIC, consumerGroup = Common.CONSUMER_GROUP, consumeMode = ConsumeMode.ORDERLY, messageModel = MessageModel.CLUSTERING)
public class InsertBaseStructuredNotesConsumer implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {
    @Resource
    private BaseStructuredNotesMapper baseStructuredNotesMapper;
    @Override
    public void onMessage(String message) {
        long start = System.currentTimeMillis();
        log.info(">>>补录平台 处理 数据资产平台 推送基础数据开始>>>>>>:{}",message);
        try {
            if (StringUtils.isEmpty(message)) {
                log.warn(">>>>消息推送为空 topic={},consumerGroup={}<<<<<<", Common.STRUCTURED_NOTES_TOPIC, Common.CONSUMER_GROUP);
                return;
            }
            if (getJSONType(message)) {
                log.warn(">>>>数据非标准格式化JSON不做处理 topic={},consumerGroup={}<<<<<<", Common.STRUCTURED_NOTES_TOPIC, Common.CONSUMER_GROUP);
                return;
            }
            JSONArray jsonArray = JSONUtil.parseArray(message);
            List<BaseStructuredNotes> BaseStructuredNoteslists = jsonArray.toList(BaseStructuredNotes.class);
            for (BaseStructuredNotes baseStructuredNotes : BaseStructuredNoteslists) {
                baseStructuredNotesMapper.insert(baseStructuredNotes);
            }
            long end = System.currentTimeMillis();
            log.info(">>>补录平台 处理 数据资产平台 推送基础数据结束>>>耗时:>>:{} ms",(end-start));
        } catch (Exception e) {
            log.error(">>>>补录平台接受数据资产平台消费出现异常:err:{}", e);
            throw new ServiceException("消费异常topic:" + Common.STRUCTURED_NOTES_TOPIC);
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setInstanceName(this.getClass().getSimpleName());
    }
    /**
     * 判断消息类型
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
