package com.scb.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

@Slf4j
public class AesUtilTest {

    private static final String content = "这个是需要加密的内容";
    private static final String key = "1234567887654321";

    @Test
    public void testBase64() {
        String base64 = AesUtil.encrypt2Base64(key, content);
        String resContent = AesUtil.decrypt(key, base64);
        assertEquals(content, resContent);
    }

    @Test
    public void testHex() {
        String hex = AesUtil.encrypt2HexStr(key, content);
        String resContent = AesUtil.decrypt(key, hex);
        assertEquals(content, resContent);
    }
}