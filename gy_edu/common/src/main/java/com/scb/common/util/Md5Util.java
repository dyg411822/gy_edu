package com.scb.common.util;

import cn.hutool.crypto.digest.DigestUtil;

import java.io.File;

/**
 * MD5 摘要工具。
 * @author R)
 */
public class Md5Util {

    /**
     * 获取内容的 MD5 摘要
     * @param content 内容
     * @return 摘要
     */
    public static String getHexStr(String content) {
        return DigestUtil.md5Hex(content);
    }

    /**
     * 获取内容的 MD5 摘要
     * @param file 文件
     * @return 摘要
     */
    public static String getHexStr(File file) {
        return DigestUtil.md5Hex(file);
    }

}
