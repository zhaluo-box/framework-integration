package org.framework.integration.security.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * JWT TOKEN 工具类
 * Created  on 2022/7/28 16:16:28
 *
 * @author zl
 * @see org.framework.integration.security.core.utils #JwtUtilTest
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("unused")
public final class JwtUtil {

    /**
     * TODO 项目上线请手动修改 secret
     */
    static final String secret = "bT0oPwm/QeUZ2KmDQqxCQXFDQUs6GPxIt00FdoqASqI=";

    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static final Key KEY = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    /**
     * 创建token
     * TODO  实现时间换算 TimeUnit
     * <h3> 基于HS512算法</h3> <br/>
     * <h3>如果使用自定义Claim 约定使用 XXX.class.getSimpleName()</h3>
     * <h3>如果使用jwt 约定的属性，则使用 </h3> @see io.jsonwebtoken.Claims 中的常量
     *
     * @param claims claim
     * @param ttl    过期时间
     * @return token
     */
    public static String createToken(Map<String, Object> claims, long ttl) {
        return getJwtBuilder().setClaims(claims).setExpiration(getExpireTime(ttl)).setIssuedAt(new Date()).compact();
    }

    /**
     * 返回默认claims
     * 只支持基本类型
     *
     * @param token token
     * @return claims
     */
    public static Claims getClaims(String token) {
        return getJwtParser().parseClaimsJws(token).getBody();
    }

    /**
     * 返回自定义claim 类型的body
     * 推荐使用  getClaims(String token)
     *
     * @param claimType claimName = claimType.getSimpleName();
     */
    public static <T> T getCustomClaims(String token, Class<T> claimType) {
        final var claimTypeName = claimType.getSimpleName();
        return getJwtParser(Map.of(claimTypeName, claimType)).parseClaimsJws(token).getBody().get(claimTypeName, claimType);
    }

    /**
     * 返回自定义claim 类型的body
     *
     * @param claimName 自定义claim 名称
     */
    @Deprecated
    public static <T> T getCustomClaims(String token, String claimName, Class<T> claimType) {
        return getJwtParser(Map.of(claimName, claimType)).parseClaimsJws(token).getBody().get(claimName, claimType);
    }

    /**
     * 返回自定义claim 类型的body
     *
     * @param claimTypeMapping <claimName,claimType>  claim 映射关系，可以存储多种类型，但是不建议太多
     * @return 如果想要获取所有的claim 直接 claimTypeMapping 传递 new HashMap<String,Class>();
     */
    @SuppressWarnings("all")
    public static Claims getCustomClaims(String token, Map<String, Class> claimTypeMapping) {
        return getJwtParser(claimTypeMapping).parseClaimsJws(token).getBody();
    }

    /**
     * 获取过期时间
     *
     * @param ttl 过期时间，单位毫秒
     * @return 过期时间
     */
    private static Date getExpireTime(long ttl) {
        return new Date(System.currentTimeMillis() + ttl);
    }

    /**
     * 获取默认的 JwtParser
     */
    public static JwtParser getJwtParser() {
        return Jwts.parserBuilder().setSigningKey(KEY).build();
    }

    /**
     * 获取解析器
     * <p>参数样例 Map.of("user",User.class) </p>
     *
     * @param claimTypeMap 自定义claim 类型映射
     */
    @SuppressWarnings("all")
    public static JwtParser getJwtParser(Map<String, Class> claimTypeMap) {
        return Jwts.parserBuilder().deserializeJsonWith(new JacksonDeserializer<>(claimTypeMap)).setSigningKey(KEY).build();
    }

    /**
     * 获取指定claim 类型的parse
     *
     * @param claimType claim类型
     */
    private static JwtParser getJwtParser(Class<?> claimType) {
        return Jwts.parserBuilder().deserializeJsonWith(new JacksonDeserializer<>(Map.of(claimType.getSimpleName(), claimType))).setSigningKey(KEY).build();
    }

    /**
     * 获取 JWT Builder
     */
    private static JwtBuilder getJwtBuilder() {
        return Jwts.builder().serializeToJsonWith(new JacksonSerializer<>(OBJECT_MAPPER)).signWith(KEY);
    }

    /**
     * 生成secretKey
     */
    private static String generateKey() {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String secretString = Encoders.BASE64.encode(key.getEncoded());
        System.out.println(secretString);
        return secretString;
    }

}
