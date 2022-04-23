package com.human;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Li JieGui
 * @date 2020/11/1 17:35
 * 启动类
 * EnableScheduling ：开启定时任务
 */
@SpringBootApplication
@MapperScan(basePackages = "com.human.mapper")
@EnableScheduling
public class ServerHumanApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerHumanApplication.class, args);
    }
}