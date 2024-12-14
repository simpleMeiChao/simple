package com.mc.exception;

import com.mc.entity.CommonEntity;
import com.mc.entity.CommonResponse;
import com.mc.enums.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public CommonEntity defaultErrorHandler(Exception ex, ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        if (ex instanceof BusinessException) {
            log.error("defaultErrorHandler :" + ex.getMessage());
            return CommonResponse.errorInstance(((BusinessException) ex).getCode(), ((BusinessException) ex).message);
        } else {
            log.error("defaultErrorHandler :" + ex.getMessage());
            return CommonResponse.errorInstance(StatusCode.SYSTEM_ERROR.statusCode(), "系统出现未知错误，请联系开发人员支撑");
        }
    }
}
