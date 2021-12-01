package com.lzl.sleuth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author eren.liao
 * @date 2021/12/1 16:08
 */
@Slf4j
@Service
public class AsyncTestService {

    @Async
    public void printAsync(){
        log.info("到了异步方法");
        log.info("888888");
    }
}
