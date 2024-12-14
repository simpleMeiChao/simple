package com.mc.utils;

import com.mc.enums.ConstantEnums;
import com.mc.enums.StatusCode;
import com.mc.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Random;

/**
 * AES加解密
 *
 * @author simpleMei
 * @since 2023-05-13 19:05:11
 */
@Slf4j
public class AESUtils {

    private static final Random RANDOM = new Random();

    /**
     * 生成加解密的随机密钥
     *
     * @return 16位长度随机数  [0-9a-zA-Z]
     */
    public static String getRandomNumber() {
        char[] nonceChars = new char[16];
        for (int i = 0; i < nonceChars.length; i++) {
            String symbols = (String) ConstantEnums.SYMBOLS.getConstant();
            nonceChars[i] = symbols.charAt(RANDOM.nextInt(symbols.length()));
        }
        return new String(nonceChars);
    }

    /**
     * aes加密
     *
     * @param content 待加密内容
     * @param aesKey 密钥
     * @return result
     */
    public static String encrypt(String content, String aesKey) {
        if (StringUtils.isBlank(content)) {
            log.error("AES encrypt: the content is null!");
            return "";
        }
        if (StringUtils.isNotBlank(aesKey) && aesKey.length() == 16) {
            String encodingFormat = (String) ConstantEnums.ENCODING.getConstant();
            try {
                Cipher cipher = Cipher.getInstance((String) ConstantEnums.CIPHER_PADDING.getConstant());
                byte[] raw = aesKey.getBytes();
                SecretKeySpec skeySpec = new SecretKeySpec(raw, (String) ConstantEnums.AES_ALGORITHM.getConstant());
                IvParameterSpec iv = new IvParameterSpec(aesKey.getBytes());
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
                byte[] encrypted = cipher.doFinal(content.getBytes(encodingFormat));
                return Base64Utils.encodeToString(encrypted);
            } catch (Exception e) {
                log.error("AES encrypt exception: " + e.getMessage());
                throw new BusinessException(StatusCode.ERROR.statusCode(), e.getMessage() == null ? "加密出现未知空指针异常，请联系开发人员定位分析。" : e.getMessage());
            }
        } else {
            log.error("AES encrypt: the key is null or error!");
            return "";
        }
    }

    /**
     * 解密
     *
     * @param content 待解密内容
     * @param aesKey 密钥
     * @return result
     *
     */
    public static String decrypt(String content, String aesKey) {
        if (StringUtils.isBlank(content)) {
            log.error("AES decrypt: the content is null!");
            return "";
        }
        if (StringUtils.isNotBlank(aesKey) && aesKey.length() == 16) {
            String encodingFormat = (String) ConstantEnums.ENCODING.getConstant();
            try {
                byte[] raw = aesKey.getBytes("ASCII");
                SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                IvParameterSpec iv = new IvParameterSpec(aesKey.getBytes());
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

                byte[] encrypted1 = Base64Utils.decodeFromString(content);
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, encodingFormat);
                return originalString;
            } catch (Exception e) {
                log.error("AES decrypt exception: " + e.getMessage());
                throw new BusinessException(StatusCode.ERROR.statusCode(), e.getMessage() == null ? "解密出现未知空指针异常，请联系开发人员定位分析。" : e.getMessage());
            }
        } else {
            log.error("AES decrypt: the key is null or error!");
            return "";
        }
    }
}
