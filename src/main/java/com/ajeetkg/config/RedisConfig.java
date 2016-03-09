package com.ajeetkg.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by agupta2 on 3/8/16.
 */

@Configuration
@EnableCaching
@PropertySource("classpath:/redis.properties")
public class RedisConfig extends CachingConfigurerSupport{

    @Value("${redis.hostname}")
    String hostname;

    @Value("${redis.port}")
    int port;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }


    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(hostname);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setUsePool(true);
        return jedisConnectionFactory;

    }


    @Bean
    public RedisTemplate<Object, Object> redisTemplate(){
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(){
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
        cacheManager.setDefaultExpiration(300);
        return cacheManager;
    }

}
