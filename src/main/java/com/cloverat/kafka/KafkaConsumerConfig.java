package com.cloverat.kafka;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * kafka消费者配置
 *
 * @author cloverat 2020/12/25
 */
@Component
@PropertySource("classpath:kafkaConfig.properties")
public class KafkaConsumerConfig {

    @Value("${kafka.consumer.bootstrap-servers}")
    private String servers;
    @Value("${kafka.consumer.group-id}")
    private String groupId;
    @Value("${kafka.consumer.enable-auto-commit}")
    private String enableAutoCommit;
    @Value("${kafka.consumer.auto-commit-interval-ms}")
    private String autoCommitIntervalMs;
    @Value("${kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;
    @Value("${kafka.consumer.session-timeout-ms}")
    private String sessionTimeoutMs;
    @Value("${kafka.consumer.key.serializer}")
    private String keySerializer;
    @Value("${kafka.consumer.value.serializer}")
    private String valueSerializer;

    public Properties initConfig() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitIntervalMs);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keySerializer);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueSerializer);

        return props;
    }
}
