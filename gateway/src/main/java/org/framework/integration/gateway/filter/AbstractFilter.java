package org.framework.integration.gateway.filter;

import org.framework.integration.common.core.http.response.ResponseEntity;
import org.framework.integration.gateway.config.SecurityFilterProperties;
import org.framework.integration.utils.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Created  on 2022/8/2 14:14:17
 *
 * @author zl
 */
public abstract class AbstractFilter implements GlobalFilter, Ordered {

    @Autowired
    private SecurityFilterProperties securityFilterProperties;

    /**
     * @param exchange
     * @param chain
     * @return
     */
    protected boolean filterBefore(ServerWebExchange exchange, GatewayFilterChain chain) {

        return false;
    }

    /**
     * @param exchange
     * @param chain
     * @return
     */
    protected boolean filterAfter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return false;
    }

    protected static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, String contentType, HttpStatus status, String message, int code) {
        response.setStatusCode(status);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, contentType);
        ResponseEntity<Void> result = ResponseEntity.fail(code, message);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JsonUtil.toJSON(result).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }

    enum Ordered {
        AUTH, PERMISSION
    }

}
