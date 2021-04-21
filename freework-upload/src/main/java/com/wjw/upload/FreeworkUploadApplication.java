package com.wjw.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author wjw
 * @date 2021/3/4 17:52
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class FreeworkUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreeworkUploadApplication.class, args);
    }
}
