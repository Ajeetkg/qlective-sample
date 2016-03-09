package com.ajeetkg.controller;

import com.ajeetkg.service.HelloService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by agupta2 on 3/8/16.
 */

@RestController
@RequestMapping(value = "api/v2/redis", produces = APPLICATION_JSON_VALUE)
@Api(value = "Redis", description = "Redis Cache Testing")
public class RedisApiController {

    private static final Logger  logger = Logger.getLogger(RedisApiController.class);

    @Autowired
    HelloService helloService;

    @RequestMapping(
            value = "/hello",
            method = RequestMethod.GET,
            produces = APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            httpMethod = "GET",
            value="Redis Cache Hello",
            notes="Does it work?"
    )
    @ApiResponses(
            value={@ApiResponse(code = 200, message = "Success")}
    )

    public ResponseEntity testHello(){

        //First method execution using key="Josh", not cached
        System.out.println("message: " + helloService.getMessage("Josh"));

        //Second method execution using key="Josh", still not cached
        System.out.println("message: " + helloService.getMessage("Josh"));

        //First method execution using key="Joshua", not cached
        System.out.println("message: " + helloService.getMessage("Joshua"));

        //Second method execution using key="Joshua", cached
        System.out.println("message: " + helloService.getMessage("Joshua"));

        System.out.println("Done.");

        Map<String,String> data = new HashMap<>();
        data.put("message",  "REDIS TESTING");
        return   ok(data);
    }


    @RequestMapping(
            value="/result",
            method = RequestMethod.GET,
            produces =  APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            httpMethod = "GET",
            value ="test the result"
    )
    @ApiResponses(
            value={
                    @ApiResponse(code=200, message = "Success")
            }
    )
    @Cacheable(cacheNames = "calculateResult")
    public String   calculateResult() throws Exception{
        logger.debug("Performing time intensive calculation");

        String result = getResult();

        Map<String, String> data = new HashMap<>();
        data.put("result", "isWorking");
        return result;
    }

    private String getResult() throws Exception{
        Thread.sleep(2000l);
        return null;
    }


}
