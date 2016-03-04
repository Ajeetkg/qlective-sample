package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class JspController {

    @RequestMapping("/home")
    public String greeting() {
        return "home";
    }

}
