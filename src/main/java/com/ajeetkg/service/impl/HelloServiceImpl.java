package com.ajeetkg.service.impl;

import com.ajeetkg.service.HelloService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by agupta2 on 3/8/16.
 */

@Service
public class HelloServiceImpl implements HelloService{


    private static final Logger logger =Logger.getLogger(HelloServiceImpl.class);

    @Autowired
    private CacheManager cacheManager;


    @Autowired
    RedisTemplate<String, String> redisTemplate;


    @Override
    @Cacheable(cacheNames = "messageCache")
    public String getMessage(String name) {
        delay();
        storePsToken("Illumina");
        logger.debug("cachemangagerName:" + cacheManager.getCacheNames());
        System.out.println("cacheManager.getCacheNames():   " + cacheManager.getCacheNames());
        System.out.println("Executing HelloServiceImpl" + ".getHelloMessage(\"" + name + "\")");
        return "Hello " + name + "!";
    }

    private void delay(){
        try{
             Thread.sleep(2000l);
        }catch (Exception e){

        }
    }

    public void storePsToken(String psToken){
       redisTemplate.boundValueOps(psToken).set("IllumianIsGreatCompany");
       redisTemplate.boundValueOps(psToken).expire(10l, TimeUnit.SECONDS);
    }


}
