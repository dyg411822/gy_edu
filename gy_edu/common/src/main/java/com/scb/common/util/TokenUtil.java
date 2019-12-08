/**
 *
 */
package com.scb.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.scb.common.constant.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <token：用于web请求访问识别 >
 * @author Administrator
 *
 */
@Component
public class TokenUtil {
    @Autowired
    RedisUtil redisUtil;
    //token秘钥  24 位
    private static final String TOKEN_SECRET = "EJHWQK78SI61UB3FJBF26BQE";
    //前端上传Token字段
    private static final String USER_TOKEN = "token";
    //小程序header记录token标识
    public static final String WX_TOKEN = "Authorization";
    //小程序前端会在拿到的TOKEN拼接这个前缀
    public static final String WX_TOKEN_PREFIX = "Bearer ";

    /**
     * 生成一个token
     */

    public static String getToken() {
        String uuid = PrimaryKeyUtil.getUuid();
        return uuid;
    }

    /***
     * 生成一个会员token
     * */
    public static String getMemberToken(String memberId) {
        String token = "";
        try {
            //过期时间 一个月秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String, Object> header = new HashMap<>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            //携带memberId信息，生成签名
            token = JWT.create()
                .withHeader(header)
                .withClaim("memberId", memberId)
                //.withExpiresAt(date)
                .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     *  通过memberId清除个人用户缓存
     * */
    public boolean removeMemberToken(String memberId) {
        boolean succ = true;
        String token = getMemberToken(memberId);
        try {
            if (StringUtils.isNotEmpty(token)) {
                succ = redisUtil.remove(token);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            succ = false;
        }
        return succ;
    }

    /**
     * @desc 验证token，通过返回true
     * @create
     * @params [token]需要校验的串
     **/
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取当前操作的后台用户信息
     * */
    public Map<String, Object> getUserByToken(String token) {
        Map<String, Object> userMap = new HashMap<>();
        try {
            if (StringUtils.isNotEmpty(token)) {
                Map<String, Object> map = (Map) redisUtil.get(token);
                String userStr = (String) map.get(Constant.SESSION_USER);
                userMap = JacksonUtil.json2Map(userStr);
            }
        } catch (NullPointerException ex) {
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userMap;
    }

    /**
     * 获取当前操作的后台用户信息
     * */
    public Map<String, Object> getUserByRequest(HttpServletRequest request) {
        String token = getUserToken(request);
        return this.getUserByToken(token);
    }

    /**
     * 获取当前管理系统操作人loginName
     * */
    public String getLoginName(HttpServletRequest request) {
        String loginName = "";
        Map<String, Object> userMap = this.getUserByRequest(request);
        if (null != userMap && userMap.containsKey("loginName")) {
            loginName = (String) userMap.get("loginName");
        }
        return loginName;
    }

    /**
     * 获取管理系统前台header传入的token字段
     * */
    public static String getUserToken(HttpServletRequest request) {
        String userToken = request.getHeader(USER_TOKEN);
        return userToken;
    }

    /**
     * 获取当前操作的小程序会员m_id
     * */
    public String getMemberByToken(String token) {
        String memberId = "";
        try {
            if (StringUtils.isNotEmpty(token)) {
                memberId = (String) redisUtil.get(token);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return memberId;
    }

    /**
     * 获取小程序header传入的token字段
     * */
    public static String getMmberToken(HttpServletRequest request) {
        String userToken;
        try {
            userToken = request.getHeader(WX_TOKEN);
            if (StringUtils.isNotEmpty(userToken) && userToken.startsWith(WX_TOKEN_PREFIX)) {
                userToken = userToken.replace(WX_TOKEN_PREFIX, "");
                return userToken;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * 获取小程序header传入的token字段
     * */
    public static String getMmberToken(ServerHttpRequest request) {
        String userToken;
        try {
            userToken = request.getHeaders().get(WX_TOKEN).get(0);
//            userToken = request.getHeader(WX_TOKEN);
            if (StringUtils.isNotEmpty(userToken) && userToken.startsWith(WX_TOKEN_PREFIX)) {
                userToken = userToken.replace(WX_TOKEN_PREFIX, "");
                return userToken;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

}
