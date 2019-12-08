package com.scb.third;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * <用于管理短信，MQ, fastDFS  等第三方工具 >
 *
 * @author
 */
@EnableTransactionManagement   //开启事务管理
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.scb")
//@EnableCaching                 //开启缓存管理
@EnableHystrix
@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableScheduling       //开启定时任务
public class ThirdApplication {
    //TEST GIT
    private static final Logger logger = LogManager.getLogger(ThirdApplication.class);

    public static void main(String[] args) {
        logger.info("-----------ThirdApplication():--------------");
        SpringApplication.run(ThirdApplication.class, args);
    }
}
