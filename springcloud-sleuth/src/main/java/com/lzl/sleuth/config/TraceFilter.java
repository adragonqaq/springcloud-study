//package com.lzl.sleuth.config;
//
//import com.lzl.sleuth.constants.TraceConstants;
//import org.slf4j.MDC;
//import org.springframework.util.StringUtils;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import java.io.IOException;
//
///**
// * @author eren.liao
// * @date 2021/12/1 15:48
// */
//public class TraceFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        ///拦截一下把自己的 APPName处理掉
//        String traceUrl = Tracer.getBaggage(TraceConstants.TRACE_KEY_TRACE_URL);z
//        String content;
//        if (!StringUtils.hasText(traceUrl)) {
//            content = TraceConstants.DELIMITER + TraceConstants.getLocalAppName();
//        } else {
//            content = traceUrl + TraceConstants.DELIMITER + TraceConstants.getLocalAppName();
//        }
//        Tracer.createBaggage(TraceConstants.TRACE_KEY_TRACE_URL, content);
//        MDC.put(TraceConstants.TRACE_KEY_TRACE_URL, content);
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//}
