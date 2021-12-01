//package com.lzl.sleuth.config;
//
//import brave.Span;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//
///**
// * @author eren.liao
// * @date 2021/12/1 15:54
// */
//@Slf4j
//public class Tracer implements ApplicationContextAware {
//
//
//    private static brave.Tracer sleuthTracer;
//
//
//
//
//    public static String getTraceId() {
//        Span span = sleuthTracer.currentSpan();
//        return span.context().traceIdString();
//    }
//
//
//    public static String getSpanId() {
//        return sleuthTracer.currentSpan().context().spanIdString();
//    }
//
//
//    public static void createBaggage(String key, String value) {
//        sleuthTracer.c(key, value);
//    }
//
//
//    public static String getBaggage(String key) {
//        return sleuthTracer.getBaggage(key).get();
//    }
//
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        try {
//            brave.Tracer bean = applicationContext.getBean(brave.Tracer.class);
//            this.sleuthTracer = bean;
//        } catch (BeansException e) {
//            log.error("初始化trace功能异常,请检查org.springframework.cloud.sleuth.Tracer注入情况", e);
//            throw e;
//        }
//    }
//}
