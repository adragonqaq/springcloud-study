package com.lzl.sleuth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author eren.liao
 * @date 2021/12/1 16:04
 */
@Slf4j
@Service
public class TestService {

    @Autowired
    private AsyncTestService asyncTestService;

    public String hello(){
        log.info("testService");

        asyncTestService.printAsync();
        return "hello";
    }




}
