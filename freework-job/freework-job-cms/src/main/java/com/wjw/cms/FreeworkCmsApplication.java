package com.wjw.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wjw
 * @date 2021/5/16 0:57
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.wjw.cms.mapper")
@ComponentScan(basePackages = {"com.wjw"})
public class FreeworkCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreeworkCmsApplication.class, args);
    }
}
