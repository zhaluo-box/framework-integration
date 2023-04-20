package org.framework.integration.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Created  on 2023/4/20 16:16:56
 *
 * @author zl
 */
@Component
public class LogTraceFilter extends AbstractFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var mutate = exchange.getRequest().mutate();
        addHeader(mutate, "LOG_TRACE_ID", UUID.randomUUID().toString());
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
