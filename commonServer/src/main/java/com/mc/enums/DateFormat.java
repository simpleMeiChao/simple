package com.mc.enums;

public enum DateFormat {

    DATE_TIME {
        public String getConstant() {
            return "yyyy-MM-dd HH:mm:ss";
        }
    },
    DATE {
        public String getConstant() {
            return "yyyy-MM-dd";
        }
    },
    DATE_TIME_MILLISECOND {
        public String getConstant() {
            return "yyyyMMddHHmmssSSS";
        }
    };

    public abstract String getConstant();
}
