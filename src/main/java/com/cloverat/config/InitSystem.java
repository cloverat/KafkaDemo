package com.cloverat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cloverat.kafka.GoldenConsumer;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InitSystem implements CommandLineRunner {

    @Autowired
    private GoldenConsumer goldenConsumer;

    @Override
    public void run(String... args) {
        new Thread(goldenConsumer).start();
    }
}
