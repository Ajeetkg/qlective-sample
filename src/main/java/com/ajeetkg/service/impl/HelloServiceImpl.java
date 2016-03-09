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
    @Cacheable(value="messageCache", condition="'illumina'.equals(#name)")
    public String getMessage(String name) {

        System.out.println("Executing HelloServiceImpl" +
                ".getHelloMessage(\"" + name + "\")");

        return "Hello " + name + "!";
    }


}
