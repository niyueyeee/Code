package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wwy23
 */
@RestController
public class TestController {
    @RequestMapping("/ok")
    public String test(){
        return "ok";
    }
}
