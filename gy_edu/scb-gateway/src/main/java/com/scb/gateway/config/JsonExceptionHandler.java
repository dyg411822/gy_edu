package com.scb.gateway.config;

import com.scb.common.constant.ErrorTypeEnum;
import com.scb.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * 全局错误处理，适配 WebFlux。
 * @author R)
 */
@Slf4j
public class JsonExceptionHandler implements ErrorWebExceptionHandler {

    /**
     * MessageReader
     */
    private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();

    /**
     * MessageWriter
     */
    private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();

    /**
     * ViewResolvers
     */
    private List<ViewResolver> viewResolvers = Collections.emptyList();

    /**
     * 参考AbstractErrorWebExceptionHandler
     * @param messageReaders
     */
    public void setMessageReaders(List<HttpMessageReader<?>> messageReaders) {
        Assert.notNull(messageReaders, "'messageReaders' 不能为 null");
        this.messageReaders = messageReaders;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     * @param viewResolvers
     */
    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     * @param messageWriters
     */
    public void setMessageWriters(List<HttpMessageWriter<?>> messageWriters) {
        Assert.notNull(messageWriters, "'messageWriters' 不能为 null");
        this.messageWriters = messageWriters;
    }

    /**
     * 处理异常
     */
    @NotNull
    @Override
    public Mono<Void> handle(@NotNull ServerWebExchange exchange, @NotNull Throwable ex) {
        // 记录异常信息
        ServerHttpRequest request = exchange.getRequest();
        log.error("[全局异常处理] 异常请求路径:{}, 记录异常信息:{}", request.getPath(), ex.getMessage());

        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }

        ServerRequest newRequest = ServerRequest.create(exchange, this.messageReaders);

        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse).route(newRequest)
            .switchIfEmpty(Mono.error(ex))
            .flatMap((handler) -> handler.handle(newRequest))
            .flatMap((response) -> write(exchange, response));
    }

    /**
     * 参考 DefaultErrorWebExceptionHandler
     * @param request
     * @return
     */
    @NotNull
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        return ServerResponse.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            // TODO: 现在固定为处理失败，需要修改为更加合适的提示。
            .body(BodyInserters.fromObject(Result.fail(ErrorTypeEnum.SQL_EXECUTE_FAIL)));
    }

    /**
     * 参考 AbstractErrorWebExceptionHandler
     * @param exchange
     * @param response
     * @return
     */
    private Mono<? extends Void> write(ServerWebExchange exchange, ServerResponse response) {
        exchange.getResponse().getHeaders()
            .setContentType(response.headers().getContentType());
        return response.writeTo(exchange, new ResponseContext());
    }

    /**
     * 参考 AbstractErrorWebExceptionHandler
     */
    private class ResponseContext implements ServerResponse.Context {

        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return JsonExceptionHandler.this.messageWriters;
        }

        @Override
        public List<ViewResolver> viewResolvers() {
            return JsonExceptionHandler.this.viewResolvers;
        }

    }

}
