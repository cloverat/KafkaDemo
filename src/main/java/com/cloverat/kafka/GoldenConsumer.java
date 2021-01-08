package com.cloverat.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * kafka消费者
 * 
 * @author cloverat 2020/12/25
 */
@Component
@Slf4j
public class GoldenConsumer implements Runnable {

    @Value("${kafka.consumer.topic.user-visit-record}")
    private String userVisitRecordTopic;
    @Value("${kafka.consumer.poll-timeout}")
    private Integer pollTimeout;

    @Autowired
    private KafkaConsumerCentre kafkaConsumerCenter;

    @Override
    public void run() {
        KafkaConsumer<String, String> consumer = kafkaConsumerCenter.initConsumer(userVisitRecordTopic);
        log.debug("开始kafka消费");

        while (true) {
            try {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(pollTimeout);
                if (null != consumerRecords) {
                    for (ConsumerRecord<String, String> consumerData : consumerRecords) {
                        log.debug("offset = {}, key = {}, value = {}", consumerData.offset(), consumerData.key(),
                            consumerData.value());
                    }
                } else {
                    log.debug("kafka无消费消息");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        log.error("kafka消费中断异常");
                        log.error(e.getMessage());
                    }
                }
            } catch (Exception e) {
                log.error("kafka消费出现异常");
                log.error(e.getMessage());
            }
        }
    }
}