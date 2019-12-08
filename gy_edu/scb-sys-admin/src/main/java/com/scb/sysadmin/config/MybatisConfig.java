package com.scb.sysadmin.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Mybatis 配置。
 * @author R)
 */
@Configuration
@MapperScan("com.scb.sysadmin.dao")
@EnableTransactionManagement
public class MybatisConfig {

    /**
     * 分页插件。
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
