package com.qlective.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/api/v1/hello", produces = APPLICATION_JSON_VALUE)
@Api(value = "Search", description = "Test APIs")
public class DemoController {

    private final Logger logger = LoggerFactory.getLogger(DemoController.class);


    @RequestMapping(
            value="/me",
            method = GET,
            produces = "application/json")
    @ApiOperation(
            httpMethod = "GET",
            value = "Hello world",
            notes = "Notes goes here")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success")

    })
    public ResponseEntity getAccessLogs() {
        System.out.println("Hello from the controller!");
        Map<String, String> data = new HashMap<>();
        data.put("message", "Hello World!");
        return ok(data);
    }
}
