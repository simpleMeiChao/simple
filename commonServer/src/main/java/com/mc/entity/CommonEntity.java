package com.mc.entity;

import java.io.Serializable;

/**
 * 实体类 数据结构
 *
 * @param <T> t
 */
public class CommonEntity<T> implements Serializable {

    private static final long SERIAL_VERSION_ID = 1L;

    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public CommonEntity() {
        super();
    }

    public CommonEntity(int code, String message, T data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
