package com.wjw.sms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wjw
 * @date 2021/5/19 3:14
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.wjw.sms.mapper")
@ComponentScan(basePackages = {"com.wjw"})
public class FreeworkSmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreeworkSmsApplication.class, args);
    }
}
