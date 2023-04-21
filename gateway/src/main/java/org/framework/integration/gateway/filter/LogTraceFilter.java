package org.framework.integration.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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
@Slf4j
@Component
public class LogTraceFilter extends AbstractFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var mutate = exchange.getRequest().mutate();
        var trace_id = UUID.randomUUID().toString();
        addHeader(mutate, "LOG_TRACE_ID", trace_id);
        MDC.put("TRACE_ID", trace_id);
        log.info("trace filter == aaa");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
