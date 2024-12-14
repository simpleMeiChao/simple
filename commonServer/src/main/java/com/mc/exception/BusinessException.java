package com.mc.exception;

import com.mc.enums.StatusCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通用业务异常类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException{

    protected int code;
    protected String message;

    public BusinessException(String message) {
        super(message);
        this.code = StatusCode.ERROR.statusCode();
        this.message = message;
    }

    public BusinessException(int code, String message) {
        super("[" + code + "]" + message);
        this.code = code;
        this.message = message;
    }
}
