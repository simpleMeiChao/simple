package com.mc.entity;

import com.mc.enums.StatusCode;

public class CommonResponse {

    /**
     * 无返回值状态返回
     *
     * @return CommonEntity
     */
    public static CommonEntity successInstance() {
        return new CommonEntity<>(StatusCode.SUCCESS.statusCode(), ".success", null);
    }

    /**
     * 返回数据体
     *
     * @param data 数据
     * @return CommonEntity
     */
    public static CommonEntity successInstance(Object data) {
        return new CommonEntity<>(StatusCode.SUCCESS.statusCode(), ".success", data);
    }

    /**
     * 错误返回体
     *
     * @param code 状态码
     * @param message 消息
     * @return CommonEntity
     */
    public static CommonEntity errorInstance(int code, String message) {
        return new CommonEntity<>(code, message, null);
    }
}
