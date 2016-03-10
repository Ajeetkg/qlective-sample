package com.ajeetkg.controller;

import com.ajeetkg.service.HelloService;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    RedisTemplate<String, String> redisTemplate;

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



    @RequestMapping(
            value = "/token",
            method = RequestMethod.GET,
            produces = APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            httpMethod = "GET",
            value = "Get the Token if it exists"
    )
    @ApiResponses(
            @ApiResponse(code = 200, message = "Success")
    )
    public String getTokenInfo(@ApiParam(value = "12345-abc", required = true)
                               @RequestParam(value = "token" , required = true) String psToken) {

        String result;

        logger.debug("Getting value for the psToken:    " + psToken);
        if (tokenExists(psToken)) {
            logger.debug("tokenExists(psToken) is true for psToken:    " + psToken);
            result = getRemainingExpiryTime(psToken) + "";
        } else {
            logger.debug("Token already expired :    " + psToken);
            result = "Token already expired";
        }

        return result;

    }


    @RequestMapping(
            value = "/token",
            method = RequestMethod.POST,
            produces = APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            httpMethod = "POST",
            value = "Post the token"
    )
    @ApiResponses(
            @ApiResponse(code = 200, message = "Success")
    )
    public String saveToken(@ApiParam(value = "12345-abc", required = true)
                            @RequestParam(value = "token") String psToken) {

        storePsToken(psToken);
        String result = getRemainingExpiryTime(psToken) + "";
        return result;

    }

    public void storePsToken(String psToken) {
        logger.debug("Store psToken: " + psToken+", for 30 seconds");
        redisTemplate.boundValueOps(psToken).set("IllumianIsGreatCompany");
        redisTemplate.boundValueOps(psToken).expire(30l, TimeUnit.SECONDS);
    }

    public boolean tokenExists(String psToken) {
        logger.debug("Store psToken: " + psToken+", for 30 seconds");
        return redisTemplate.hasKey(psToken);
    }

    public long getRemainingExpiryTime(String psToken) {
        logger.debug("SgetRemainingExpiryTime() for psToken: "+psToken);
        return redisTemplate.getExpire(psToken);
    }

}
