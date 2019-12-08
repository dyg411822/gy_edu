package com.scb.common.util;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取客户端真实 ip
 *
 * @author Administrator
 */
public class ClientIPUtil {

    // 需要检测的请求头
    private static final String[] headers = {
        "X-Real-IP",
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_CLIENT_IP",
        "HTTP_X_FORWARDED_FOR"
    };

    /**
     * 获取客户端真实 IP
     * @param request HTTP 请求体
     * @return 真实 IP 或者 null
     */
    public static String getClientIp(HttpServletRequest request) {

        String ip = null;

        for (String header : headers) {
            ip = request.getHeader(header);
            if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                continue;
            }
            break;
        }

        return ip;
    }
}
