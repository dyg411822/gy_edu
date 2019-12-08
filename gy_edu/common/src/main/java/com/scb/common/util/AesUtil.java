package com.scb.common.util;

import cn.hutool.crypto.SecureUtil;

/**
 * <AES 加密工具类>
 * @author Administrator
 *
 */
public class AesUtil {

    private final static String DEFAULT_AES_KEY = "AES%100vbnm@PMP#";

    /**
     * AES 加密内容
     * @param password 加密密码
     * @param content 加密内容
     * @return 加密之后的字符串
     */
    public static String encrypt2HexStr(String password, String content) {
        return SecureUtil.aes(password.getBytes()).encryptHex(content);
    }

    /**
     * AES 加密内容（使用默认密码）
     * @param content 加密内容
     * @return 加密之后的字符串
     */
    public static String encrypt2HexStr(String content) {
        return encrypt2HexStr(DEFAULT_AES_KEY, content);
    }

    /**
     * AES 加密内容
     * @param password 加密密码
     * @param content 加密内容
     * @return 加密之后的字符串
     */
    public static String encrypt2Base64(String password, String content) {
        return SecureUtil.aes(password.getBytes()).encryptBase64(content);
    }

    /**
     * AES 加密内容（使用默认密码）
     * @param content 加密内容
     * @return 加密之后的字符串
     */
    public static String encrypt2Base64(String content) {
        return encrypt2Base64(DEFAULT_AES_KEY, content);
    }

    /**
     * AES 解密内容
     * @param password 密码
     * @param content 需要解密的内容
     * @return 明文内容
     */
    public static String decrypt(String password, String content) {
        return SecureUtil.aes(password.getBytes()).decryptStr(content);
    }

    /**
     * AES 解密内容（使用默认密码）
     * @param content 需要解密的内容
     * @return 明文内容
     */
    public static String decrypt(String content) {
        return decrypt(DEFAULT_AES_KEY, content);
    }

}
