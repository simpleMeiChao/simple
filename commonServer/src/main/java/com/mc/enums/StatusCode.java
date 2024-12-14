package com.mc.enums;

public enum StatusCode {

    SUCCESS { // 成功状态码
        public int statusCode() {
            return 200;
        }
    },
    ERROR { // 业务错误状态码
        public int statusCode() {
            return 500;
        }
    },
    AUTHENTICATION_ERROR { // 认证错误
        public int statusCode() {
            return 5001001;
        }
    },
    ILLEGAL_ACCESS{ // 非法访问
        public int statusCode() {
            return 5001002;
        }
    },
    SYSTEM_ERROR { // 系统未知错误
        public int statusCode() {
            return 5001003;
        }
    },
    ILLEGAL_PARAM { // 非法入参
        public int statusCode() {
            return 5001004;
        }
    },
    BRANCH_CODE { // 分支码  if  else
        public int statusCode() {
            return 1;
        }
    };

    public abstract int statusCode();
}
