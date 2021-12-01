package com.lzl.sleuth.constants;


/**
 * @author eren.liao
 * @date 2021/12/1 15:50
 */
public class TraceConstants {

    public static final String TRACE_KEY_TRACE_ID = "traceId";
    public static final String TRACE_KEY_TRACE_URL = "traceUrl";
    public static final String DELIMITER = "/";
    public static final String APP_NAME_KEY = "appcode";
    public static final String APP_NAME_DEFAULT = "MIS_APP_NAME";
    private static String appName = null;


    public static String getLocalAppName() {
        if (appName != null) {
            return appName;
        }
        appName = APP_NAME_DEFAULT;
        return appName;
    }
}
