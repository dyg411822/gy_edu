package com.scb.sysadmin.config;

import com.scb.common.util.AbstractRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Redis 配置。
 * @author R)
 */
@Configuration
@EnableCaching
public class RedisConfig extends AbstractRedisConfig {

}
