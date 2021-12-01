package com.lzl.sleuth.controller;

import com.lzl.sleuth.service.TestService;
import com.lzl.sleuth.util.TraceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eren.liao
 * @date 2021/12/1 16:03
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/hello")
    public String hello(){
        log.info("hello");
        String traceId = TraceUtil.getTraceId();
        log.info("打印的traceId:{}",traceId);
        return testService.hello();
    }
}
