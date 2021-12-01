package com.lzl.producer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author eren.liao
 * @date 2021/12/1 16:27
 */
@FeignClient("sleuth-one")
public interface SleuthFeign {

    @GetMapping("/hello")
    String hello();
}
