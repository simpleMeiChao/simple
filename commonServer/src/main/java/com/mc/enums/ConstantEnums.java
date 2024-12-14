package com.mc.enums;

public enum ConstantEnums {

    TOKEN_SIGNATURE {
        public Object getConstant() {
            return "simple19900727";
        }
    },
    TOKEN_KEY {
        public Object getConstant() {
            return "Authorization";
        }
    },
    TOKEN_VALIDITY_TIME { // token有效时长
        public Object getConstant() {
            return 1000 * 60 * 10;
        }
    },
    ENCODING {
        public Object getConstant() {
            return "UTF-8";
        }
    },
    AES_ALGORITHM {
        public Object getConstant() {
            return "AES";
        }
    },
    CIPHER_PADDING {
        public Object getConstant() {
            return "AES/CBC/PKCS5Padding";
        }
    },
    SYMBOLS {
        public Object getConstant() {
            return "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }
    };

    public abstract Object getConstant();
}
