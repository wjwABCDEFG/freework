package com.wjw.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author wjw
 * @date 2021/5/21 23:47
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
public class FreeworkGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreeworkGatewayApplication.class, args);
    }
}
