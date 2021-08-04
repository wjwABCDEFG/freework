package com.wjw.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wjw
 * @date 2021/3/4 17:52
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.wjw"})
public class FreeworkUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreeworkUploadApplication.class, args);
    }
}
