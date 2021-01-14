package com.cloverat.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * kafka消费者
 * 
 * @author cloverat 2020/12/25
 */
@Component
@Slf4j
@PropertySource("classpath:kafkaConfig.properties")
public class GoldenConsumer implements Runnable {

    @Value("${kafka.consumer.topic.xxx}")
    private String userVisitRecordTopic;
    @Value("${kafka.consumer.poll-timeout}")
    private Long pollTimeout;
    @Autowired
    private KafkaConsumerCenter kafkaConsumerCenter;

    @Override
    public void run() {
        KafkaConsumer<String, String> consumer = kafkaConsumerCenter.initConsumer(userVisitRecordTopic);
        log.info("---------开始kafka消费---------");

        while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(pollTimeout);
            if (null != consumerRecords) {
                for (ConsumerRecord<String, String> consumerData : consumerRecords) {
                    log.info("offset = {}, key = {}, value = {}", consumerData.offset(), consumerData.key(),
                        consumerData.value());
                }
            } else {
                log.debug("---------kafka无消费消息---------");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    log.error("---------kafka消费异常中断---------");
                    log.error(e.getMessage());
                }
            }
        }
    }
}