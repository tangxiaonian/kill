package com.tang.kill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Classname SpringKillApplication
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/3/20 19:40
 * @Created by ASUS
 */
//@EnableScheduling
@MapperScan(value = "com.tang.kill.mapper")
@SpringBootApplication
public class SpringKillApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringKillApplication.class, args);

    }

}