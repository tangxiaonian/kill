package com.tang.kill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname RootConfig
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/3/21 13:54
 * @Created by ASUS
 */
@Configuration
public class RootConfig {

    @Bean
    public SnowflakeDistributeId snowflakeDistributeId() {
        return new SnowflakeDistributeId(1, 1);
    }

}