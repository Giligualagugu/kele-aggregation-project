package com.kele.aggregation.filter;

import com.alibaba.fastjson.JSON;
import com.kele.aggregation.common.dto.KeleResult;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ProtectInnerUriFilter implements GlobalFilter, Ordered {

    private static PathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        final String uri = exchange.getRequest().getURI().getPath();

        // 保护内部 service调用的api 不对外暴露;
        if (antPathMatcher.match("/**/inner/**", uri)) {
            final ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            final KeleResult<Object> result = KeleResult.fail().setMessage("禁止访问");
            final DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONString(result).getBytes());
            return response.writeWith(Mono.just(dataBuffer));
        }


        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
