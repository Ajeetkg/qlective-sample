package com.ajeetkg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JspController {

    @RequestMapping("/home")
    public String greeting() {
        return "home";
    }

}
