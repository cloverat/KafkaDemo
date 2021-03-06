package com.cloverat.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * kafka消费者中心
 *
 * @author cloverat 2021/1/4
 */
@Component
@Slf4j
public class KafkaConsumerCenter {

    @Autowired
    private KafkaConsumerConfig kafkaConsumerConfig;

    /**
     * 初始化消费者
     *
     * @param topic 主题
     * @return 初始化消费者
     */
    public KafkaConsumer<String, String> initConsumer(String topic) {
        // 初始化参数
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kafkaConsumerConfig.initConfig());
        // 订阅主题
        consumer.subscribe(Collections.singletonList(topic));
        return consumer;
    }
}
