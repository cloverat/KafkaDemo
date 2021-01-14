package com.cloverat.kafka;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * kafka生产者中心
 * 
 * @author cloverat 2021/1/14
 */
@Slf4j
@Component
@PropertySource("classpath:kafkaConfig.properties")
public class KafkaProducerCenter {

    @Autowired
    private KafkaProducerConfig kafkaProducerConfig;

    @Value("${kafka.producer.topic.xxx}")
    private String topic;

    /**
     * 模拟发送10条消息
     */
    @PostConstruct
    public void run() {
        for (int i = 0; i < 10; i++) {
            sendMsg("" + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送生产者消息
     *
     * @param sendMsgReq 消息体
     */
    public void sendMsg(String sendMsgReq) {
        log.debug("开始给kafka发送消息");
        KafkaProducer<String, String> producer = new KafkaProducer<>(kafkaProducerConfig.initConfig());
        log.info("消息体为{}", sendMsgReq);
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, sendMsgReq);
        // 异步发送消息
        producer.send(producerRecord, (recordMetadata, e) -> {
            if (null != e) {
                log.error(e.getMessage(), e);
            } else {
                log.info("成功接收返回消息：{}", recordMetadata);
            }
        });
        // 异步发送消息时必须要flush，不然发送失败时不会执行回调函数
        producer.flush();
        producer.close();
        log.debug("kafka消息发送结束");
    }
}
