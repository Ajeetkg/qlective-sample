package com.ajeetkg.service.impl;

import com.ajeetkg.service.HelloService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by agupta2 on 3/8/16.
 */

@Service
public class HelloServiceImpl implements HelloService{

    @Override
    @Cacheable(value="messageCache", key = "illumina")
    public String getMessage(String name){
        System.out.println(
                "Executing the HelloServiceImpl.getMessage for : " + name
        );

        return "Hello "+ name;
    }


}
