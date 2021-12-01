package com.lzl.sleuth.bean;

import lombok.Data;

/**
 * @author eren.liao
 * @date 2021/12/1 14:39
 */
@Data
public class BaseResult {
    /**
     * httpCode
     */
    private Integer code;

    /**
     * 业务code
     */
    private String errorCode;

    /**
     * 业务信息
     */
    private String message;

    /**
     * 链路id
     */
    private String traceId;

    public BaseResult() {
    }

    public BaseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResult(Integer code, String errorCode, String message) {
        this.code = code;
        this.errorCode = errorCode;
        this.message = message;
    }

    protected static final Integer CODE_SUCCESS = 200;

    protected static final Integer CODE_SYSTEM_ERROR = 500;

    protected static final Integer CODE_CLIENT_ERROR = 400;

    protected static final String MESSAGE_SUCCESS = "请求成功";

}

