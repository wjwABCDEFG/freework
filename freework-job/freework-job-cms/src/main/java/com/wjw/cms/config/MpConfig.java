package com.wjw.cms.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Date;

/**
 * @author wjw
 * @date 2021/1/21 2:28
 * mybatis-plus配置类
 */
@Configuration
public class MpConfig implements MetaObjectHandler {

    //自动填充时间
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("modifiedTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("modifiedTime", new Date(), metaObject);
    }

    //逻辑删除
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    // sql性能分析，设置 dev test 环境开启，线上环境不推荐开启
//    @Bean
//    @Profile({"dev","test"})
//    public PerformanceInterceptor performanceInterceptor() {
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//        performanceInterceptor.setMaxTime(1000);//ms，超过此处设置的ms则sql不执行
//        performanceInterceptor.setFormat(true);
//        return performanceInterceptor;
//    }

    //分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
