package org.framework.integration.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.framework.integration.security.core.constant.AuthConstants;
import org.framework.integration.security.core.constant.AuthHeaderConstants;
import org.framework.integration.security.core.entity.AuthInfo;
import org.framework.integration.security.core.utils.JwtUtil;
import org.framework.integration.utils.validator.AssertUtil;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created  on 2022/8/2 11:11:36
 *
 * @author zl
 */
@Slf4j
@Component
public class AuthFilter extends AbstractFilter {

    final AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 过滤白名单， 基于antMatch
     * 过滤option 请求
     * 获取token  验证是否携带token
     * 解析token
     * 基于tokenId redis 查询有效时间进行验证
     * 将token包含的信息 设置到Http header
     * 移除其他header
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
        final var authorizationInfo = request.getHeaders().getFirst(AuthConstants.AUTHORIZATION_HEADER);
        if (!StringUtils.hasText(authorizationInfo)) {
            return webFluxResponseWriter(response, MediaType.APPLICATION_JSON_VALUE, HttpStatus.UNAUTHORIZED, "令牌不能为空", 401);
        }
        if (!StringUtils.startsWithIgnoreCase(authorizationInfo, AuthConstants.Bearer)) {
            return webFluxResponseWriter(response, MediaType.APPLICATION_JSON_VALUE, HttpStatus.UNAUTHORIZED, "令牌格式不正确,仅支持Bearer token", 401);
        }

        // 解析token
        var token = authorizationInfo.split(" ")[1];
        //        final var authInfo = JwtUtil.getCustomClaims(token, AuthInfo.class);

        var customClaims = JwtUtil.getCustomClaims(token, Map.of(AuthInfo.class.getSimpleName(), AuthInfo.class));
        var authInfo = customClaims.get(AuthInfo.class.getSimpleName(), AuthInfo.class);

        // TODO @wmz 2022/11/17 验证token 是否过期

        // 黑名单token 过滤
        if (!blackTokenFilter(authInfo.getTokenId())) {
            return webFluxResponseWriter(response, MediaType.APPLICATION_JSON_VALUE, HttpStatus.UNAUTHORIZED, "令牌已失效,当前用户已退出登录,请重新登录", 401);
        }

        // 校验是否刷新token
        if (verifyIsNeedRefreshToken(customClaims.getExpiration())) {
            var newToken = JwtUtil.createToken(Map.of(AuthInfo.class.getSimpleName(), AuthInfo.class), securityFilterProperties.getTtl());
            response.getHeaders().add("new_token", newToken);
        }

        var mutate = request.mutate();
        addHeaders(mutate, extractAuthInfo(authInfo));

        // TODO 后期验证是否还需要对request 重新构建
        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

    private boolean verifyIsNeedRefreshToken(Date expireDate) {
        return expireDate.getTime() - new Date().getTime() <= securityFilterProperties.getRefreshOffset();
    }

    @Override
    public int getOrder() {
        return Ordered.AUTH.ordinal();
    }

    private Map<String, Object> extractAuthInfo(AuthInfo info) {
        AssertUtil.notNull(info, "认证信息不能为空！");
        var result = new HashMap<String, Object>(5);
        result.put(AuthHeaderConstants.ACCOUNT, info.getAccount());
        result.put(AuthHeaderConstants.ACCOUNT_ID, info.getAccountId());
        result.put(AuthHeaderConstants.DEPT_ID, info.getDeptId());
        result.put(AuthHeaderConstants.DEPT_NAME, info.getDeptName());
        result.put(AuthHeaderConstants.ACCOUNT_NAME, info.getAccountName());
        return result;
    }

    /**
     * token 黑名单过过滤
     *
     * @param tokenId 不能为空
     * @return true 通过,false 拦截
     */
    private boolean blackTokenFilter(String tokenId) {
        final var fullTokenKey = AuthConstants.TOKEN_BLACK_KEY_PREFIX + tokenId;
        // TODO redis hasKey
        return true;
    }

    private boolean whitesFilter(String url, List<String> patterns) {
        // 白名单为空则不放行
        if (patterns.isEmpty()) {
            return false;
        }

        long count = patterns.stream().filter(pattern -> antPathMatcher.match(pattern, url)).count();
        return count > 0;
    }
}
