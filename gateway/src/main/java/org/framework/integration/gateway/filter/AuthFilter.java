package org.framework.integration.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.framework.integration.gateway.config.SecurityFilterProperties;
import org.framework.integration.security.core.constants.AuthConstant;
import org.framework.integration.security.core.entity.AuthInfo;
import org.framework.integration.security.core.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Created  on 2022/8/2 11:11:36
 *
 * @author zl
 */
@Slf4j
@Component
public class AuthFilter extends AbstractFilter {

    @Autowired
    private SecurityFilterProperties securityFilterProperties;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 过滤白名单， 基于antMatch
     * 过滤option 请求
     * 获取token  验证是否携带token
     * 解析token
     * 基于tokenId redis 查询有效时间进行验证
     * 将token包含的信息 设置到Http header
     * 移除其他header
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 请求方式过过滤
        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
            return chain.filter(exchange);
        }
        // 白名单过滤
        String url = request.getURI().getPath();
        // 跳过不需要验证的路径
        if (whitesFilter(url, securityFilterProperties.getWhitePath())) {
            return chain.filter(exchange);
        }

        // 获取token
        final var authorizationInfo = request.getHeaders().getFirst(AuthConstant.AUTHORIZATION_HEADER);
        if (!StringUtils.hasText(authorizationInfo)) {
            return webFluxResponseWriter(response, MediaType.APPLICATION_JSON_VALUE, HttpStatus.UNAUTHORIZED, "令牌不能为空", 401);
        }
        if (!StringUtils.startsWithIgnoreCase(authorizationInfo, AuthConstant.Bearer)) {
            return webFluxResponseWriter(response, MediaType.APPLICATION_JSON_VALUE, HttpStatus.UNAUTHORIZED, "令牌格式不正确,仅支持Bearer token", 401);
        }

        // 解析token
        var token = authorizationInfo.split(" ")[1];
        final var authInfo = JwtUtil.getCustomClaims(token, AuthInfo.class);
        // 黑名单token 过滤
        if (!blackTokenFilter(authInfo.getTokenId())) {
            return webFluxResponseWriter(response, MediaType.APPLICATION_JSON_VALUE, HttpStatus.UNAUTHORIZED, "令牌已失效,当前用户已退出登录,请重新登录", 401);
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.AUTH.ordinal();
    }

    /**
     * token 黑名单过过滤
     *
     * @param tokenId 不能为空
     * @return true 通过,false 拦截
     */
    private boolean blackTokenFilter(String tokenId) {
        final var fullTokenKey = AuthConstant.TOKEN_BLACK_KEY_PREFIX + tokenId;
        // TODO redis hasKey
        return true;
    }

    private boolean whitesFilter(String url, List<String> patterns) {
        // 白名单为空则不放行
        if (patterns.isEmpty()) {
            return false;
        }

        //        var antPathMatcher = new AntPathMatcher();
        long count = patterns.stream().filter(pattern -> antPathMatcher.match(pattern, url)).count();
        return count > 0;
    }
}
