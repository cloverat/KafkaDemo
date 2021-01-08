package com.cloverat.kafka;

import javax.annotation.PostConstruct;

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
public class GoldenConsumer {

    @Value("${kafka.consumer.topic.user-visit-record}")
    private String userVisitRecordTopic;
    @Autowired
    private KafkaConsumerCentre kafkaConsumerCentre;

    @PostConstruct
    public void run() {
        KafkaConsumer<String, String> consumer = kafkaConsumerCentre.initConsumer(userVisitRecordTopic);
        log.info("---------开始kafka消费---------");

        while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(100);
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