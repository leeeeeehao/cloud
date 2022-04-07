package com.example.es.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableAsync
public class TestTask {

    private int i;

    @Scheduled(cron = "*/1 * * * * ?")
    public void execute() {
        log.info("--------------------test");
    }
}
