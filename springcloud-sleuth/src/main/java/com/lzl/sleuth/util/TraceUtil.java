package com.lzl.sleuth.util;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContext;

/**
 * @author eren.liao
 * @date 2021/12/1 14:43
 */
public class TraceUtil {

    public static String getTraceId(){
        Tracer tracer=(Tracer)SpringContextUtil.getBean("tracer");
        if(tracer!=null){
            Span span=tracer.currentSpan();
            TraceContext traceContext=span.context();
            return traceContext.traceIdString();
        }
        return null;
    }
}

