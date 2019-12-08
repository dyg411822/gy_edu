package com.scb.gateway.filter;

import com.scb.common.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 管理后台登录验证。
 * @author R)
 */
@Slf4j
public class SysAdminLoginFilter extends AbstractGatewayFilterFactory<SysAdminLoginFilter.Config> {

    private static final String KEY = "withParams";

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(KEY);
    }

    public SysAdminLoginFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            long startTime = System.currentTimeMillis();

            // TODO: 根据 token 进行获取
            List<String> roles = new ArrayList<>();
            roles.add("R1");
            roles.add("RX");

            ServerHttpRequest newRequest = exchange.getRequest().mutate()
                // 用户编号
                .header(Constant.USER_ID, String.valueOf(10000))
                // 用户名称
                .header(Constant.USERNAME, "AAron")
                // 用户角色
                .header(Constant.USER_ROLE, roles.toArray(new String[]{})).build();

            return chain.filter(exchange.mutate().request(newRequest).build()).then(
                Mono.fromRunnable(() -> {
                    log.error(exchange.getRequest().getURI().getRawPath() + ": " + (System.currentTimeMillis() - startTime) + "ms");
                })
            );
        };
    }

    public static class Config {
        private boolean withParams;

        public boolean isWithParams() {
            return withParams;
        }

        public void setWithParams(boolean withParams) {
            this.withParams = withParams;
        }
    }
}
