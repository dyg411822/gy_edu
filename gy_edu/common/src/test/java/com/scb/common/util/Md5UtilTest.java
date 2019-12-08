package com.scb.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

@Slf4j
public class Md5UtilTest {

    @Test
    public void testGetHexStr() {
        String content = "这个是检测的内容";
        String destContent = "a10dfb99d61f289f08921c26c04778c2";

        String md5Str = Md5Util.getHexStr(content);
        assertEquals(destContent, md5Str);
    }

}