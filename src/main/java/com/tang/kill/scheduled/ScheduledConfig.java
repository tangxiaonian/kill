package com.tang.kill.scheduled;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

/**
 * @Classname ScheduledConfig
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/3/21 22:07
 * @Created by ASUS
 */
@Component
public class ScheduledConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        // 配置多线程模式
        scheduledTaskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));

    }
}