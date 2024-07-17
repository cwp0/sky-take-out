package com.sky.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * @Program: sky-take-out
 * @Package: com.sky.task
 * @Class: MyTask
 * @Description: 自定义定时任务
 * @Author: cwp0
 * @CreatedTime: 2024/07/17 12:38
 * @Version: 1.0
 */
@Component
@Slf4j
public class MyTask {

    /**
     * @Description: 定时任务，每5秒执行一次
     * @Param:       {}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/17 12:59
     */
    // @Scheduled(cron = "0/5 * * * * ?")
    public void executeTask() {
        log.info("定时任务执行了：{}", new Date());
    }
}

