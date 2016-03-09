package com.ajeetkg.service.impl;

import com.ajeetkg.service.HelloService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by agupta2 on 3/8/16.
 */

@Service
public class HelloServiceImpl implements HelloService{


    private static final Logger logger =Logger.getLogger(HelloServiceImpl.class);

    @Autowired
    private CacheManager cacheManager;

    @Override
    @Cacheable(cacheNames = "messageCache")
    public String getMessage(String name) {


        logger.debug("cachemangagerName:" + cacheManager.getCacheNames());
        System.out.println("cacheManager.getCacheNames():   " + cacheManager.getCacheNames());
        System.out.println("Executing HelloServiceImpl" +
                ".getHelloMessage(\"" + name + "\")");

        return "Hello " + name + "!";
    }

    private void delay(){
        try{
                           Thread.sleep(2000l);
        }catch (Exception e){

        }
    }

    public void storePsToken(String psToken){

    }


}
