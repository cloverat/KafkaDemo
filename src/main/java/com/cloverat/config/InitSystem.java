package com.cloverat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cloverat.kafka.GoldenConsumer;

import lombok.extern.slf4j.Slf4j;

/**
 * 代码描述
 * 
 * @author chenyujun 2021/1/14
 */
@Component
@Slf4j
public class InitSystem implements CommandLineRunner {

    @Autowired
    private GoldenConsumer goldenConsumer;

    @Override
    public void run(String... args) {
        // 启动黄金宝典的kafka消费者
        new Thread(goldenConsumer).start();
    }
}
