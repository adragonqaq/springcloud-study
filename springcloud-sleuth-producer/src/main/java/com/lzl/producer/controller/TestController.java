package com.lzl.producer.controller;

import com.lzl.producer.feign.SleuthFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eren.liao
 * @date 2021/12/1 16:28
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    private SleuthFeign sleuthFeign;

    @GetMapping("/hello")
    public String hello(){
        log.info("hello");
        String hello = sleuthFeign.hello();
        log.info("打印的hello:{}",hello);
        return hello;
    }
}
