package com.scb.gateway.filter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import com.scb.common.constant.Constant;
import com.scb.common.util.JacksonUtil;
import com.scb.common.util.RedisUtil;
import com.scb.common.util.TokenUtil;

import reactor.core.publisher.Mono;

//@Configuration
public class GlobalLoginFilter implements GlobalFilter, Ordered {
	private static final Logger logger = LogManager.getLogger(GlobalLoginFilter.class);
	// 来源:后台管理系统
	private static final String SYS_ADMIN = "0";
	// 来源:微信小程序
	private static final String WECHAT_MINI = "1";
	// header携带字段
	private static final String HEADER_FLATFORM = "platform";
	@Autowired
	private TokenUtil tokenUtil;

	@Resource
	private RedisUtil redisUtil;
//	@Value("${accessfilter.adminurl}")
	String adminUrl="dafdjklasfjkd";
//	@Value("${accessfilter.memberurl}")
	String memberUrl="dafdjklasfjkd";
	public static List<String> adminUrls = new ArrayList<>();
	public static List<String> memberUrls = new ArrayList<>();

	@Override
	public int getOrder() {
		return 1;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//		ServerHttpRequest request = exchange.getRequest();
//		ServerHttpResponse response = exchange.getResponse();
//		// 设置路径过滤--登录相关
//		String requestUrl = request.getURI().toString();
//		if (requestUrl.contains("/login/login")) {
//			return chain.filter(exchange);
//		}
//		String platform = request.getHeaders().getFirst(HEADER_FLATFORM);
//		// 微信小程序访问
//		try {
//			if (WECHAT_MINI.equals(platform)) {
//				for (String url : memberUrls) {
//					// 微信如果路径需要登录，拦截
//					if (requestUrl.contains(url)) {
//						String token = TokenUtil.getMmberToken(request);
//						if (StringUtils.isEmpty(token) || StringUtils.isEmpty(tokenUtil.getMemberByToken(token))) {
//							logger.warn(TokenUtil.WX_TOKEN + " is empty");
//							Map<String,String> message=new HashMap<String, String>();
////							JSONObject message = new JSONObject();
//							message.put("statusCode", Constant.MSG_LOGIN_SESSION_EXPIRE);
//							message.put("msg", "请求类型错误!!!");
//							String messateString=JacksonUtil.bean2Json(message);
//							byte[] bits = messateString.getBytes(StandardCharsets.UTF_8);
//							DataBuffer buffer = response.bufferFactory().wrap(bits);
//							response.setStatusCode(HttpStatus.UNAUTHORIZED);
//							// 指定编码，否则在浏览器中会中文乱码
//							response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
//							return response.writeWith(Mono.just(buffer));
//						}
//					}
//				}
//			} else if (SYS_ADMIN.equals(platform)) { // 后台管理系统
//				for (String url : adminUrls) {
//					if (requestUrl.contains(url)) {
//						return null;
//					}
//				}
//				String token = request.getHeaders().getFirst("token");
//				if (token == null || !redisUtil.exists(token)) {
//					logger.warn("token is empty");
//					Map<String,String> message=new HashMap<String, String>();
//					message.put("statusCode", Constant.MSG_LOGIN_SESSION_EXPIRE);
//					message.put("msg", "请求类型错误!!!");
//					String messateString=JacksonUtil.bean2Json(message);
//					byte[] bits = messateString.getBytes(StandardCharsets.UTF_8);
//					DataBuffer buffer = response.bufferFactory().wrap(bits);
//					response.setStatusCode(HttpStatus.UNAUTHORIZED);
//					// 指定编码，否则在浏览器中会中文乱码
//					response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
//					return response.writeWith(Mono.just(buffer));
//				} else {
//					// 刷新登录时间
//					redisUtil.expire(token, Constant.SESSION_USER_TIME);
//				}
//			} else {
//				logger.warn("platform is empty");
//				Map<String,String> message=new HashMap<String, String>();
//				message.put("statusCode", Constant.ERR_REQ_PARAM);
//				message.put("msg", "请求类型错误!!!");
//				String messateString=JacksonUtil.bean2Json(message);
//				byte[] bits = messateString.getBytes(StandardCharsets.UTF_8);
//
//
//				DataBuffer buffer = response.bufferFactory().wrap(bits);
//				response.setStatusCode(HttpStatus.UNAUTHORIZED);
//				// 指定编码，否则在浏览器中会中文乱码
//				response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
//				return response.writeWith(Mono.just(buffer));
//			}
//		} catch (Exception ex) {
//			logger.error("AccessFilter run  Exception", ex);
//		}
		return chain.filter(exchange);
	}

}
