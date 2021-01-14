package com.cloverat.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * kafka生产者配置类
 *
 * @author cloverat 2021/1/14
 */
@Component
@PropertySource("classpath:kafkaConfig.properties")
public class KafkaProducerConfig {

    /**
     * kafka 消息体参数
     */
    @Value("${kafka.producer.bootstrap-servers}")
    private String servers;
    @Value("${kafka.producer.key.serializer}")
    private String keySerializer;
    @Value("${kafka.producer.value.serializer}")
    private String valueSerializer;

    public Properties initConfig() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);

        return props;
    }
}
