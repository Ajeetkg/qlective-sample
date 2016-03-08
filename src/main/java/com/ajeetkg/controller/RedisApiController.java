package com.ajeetkg.controller;

import com.ajeetkg.service.HelloService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

        System.out.println("MESSAGE:        "+ helloService.getMessage("illumina"));
        System.out.println("MESSAGE:        "+ helloService.getMessage("illumina2"));
        System.out.println("MESSAGE:        "+ helloService.getMessage("illumina3"));
        System.out.println("MESSAGE:        "+ helloService.getMessage("illumina"));

        Map<String,String> data = new HashMap<>();
        data.put("message",  helloService.getMessage("illumina"));
        return   ok(data);
    }


}
