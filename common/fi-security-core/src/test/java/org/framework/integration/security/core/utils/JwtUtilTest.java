package org.framework.integration.security.core.utils;

import org.framework.integration.security.core.entity.AuthInfo;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;

/**
 * Created  on 2022/8/2 10:10:14
 *
 * @author zl
 */
class JwtUtilTest {

    @Test
    void jwtTest() {
        var zhang_san = new User().setName("zhang san").setAge(12);
        var student = new Student().setName("student").setAge(19);
        var claim = new HashMap<String, Object>();
        claim.put("user", zhang_san);
        claim.put("student", student);
        System.out.println(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
        String token = JwtUtil.createToken(claim, 10 * 60 * 1000);
        System.out.println(JwtUtil.getCustomClaims(token, User.class));

        var claimTypeMapping = new HashMap<String, Class>();
        claim.put(User.class.getSimpleName(), User.class);
        claim.put(Student.class.getSimpleName(), Student.class);
        System.out.println(JwtUtil.getCustomClaims(token, claimTypeMapping));

        var expiration = JwtUtil.getJwtParser().parseClaimsJws(token).getBody().getExpiration();
        var issuedAt = JwtUtil.getJwtParser().parseClaimsJws(token).getBody().getIssuedAt();

        System.out.println(expiration.getTime());
        System.out.println(issuedAt.getTime());

    }

    @Test
    public void testToken() {

        var token = "eyJhbGciOiJIUzI1NiJ9.eyJBdXRoSW5mbyI6eyJhY2NvdW50SWQiOjEsImFjY291bnQiOiIxMjAxMTAiLCJhY2NvdW50TmFtZSI6IueLl-euoeeQhiIsImRlcHROYW1lIjoi57O757uf566h55CGIiwiZGVwdElkIjoxLCJ0b2tlbklkIjoiODYxNDY5NmMtZGRmZi00YTEzLWFhNDktZGE5MTcwODFjNTEwIiwicm9sZXMiOlsibWFuYWdlciIsImFkbWluIl19LCJleHAiOjE2NjA4OTc1NzEsImlhdCI6MTY2MDg5Njk3MX0.NvNLczJW_FqppGM14lOt7p8e7ZLh6K1_C6CRHDm7EhA";
        var claimTypeMapping = new HashMap<String, Class>();
        claimTypeMapping.put(AuthInfo.class.getSimpleName(), AuthInfo.class);
        var body = JwtUtil.getJwtParser(claimTypeMapping).parseClaimsJws(token).getBody();
        var customClaims = JwtUtil.getCustomClaims(token, claimTypeMapping);

        System.out.println(body.get(AuthInfo.class.getSimpleName(), AuthInfo.class));
        System.out.println(customClaims.get(AuthInfo.class.getSimpleName(), AuthInfo.class));
        //        var issuedAt = body.getIssuedAt();
        //        var expiration = body.getExpiration();
        //        var customClaims = JwtUtil.getCustomClaims(token, claimTypeMapping);
        //        var ccs = JwtUtil.getCustomClaims(token);
        //        var o = ccs.get(AuthInfo.class.getSimpleName(), AuthInfo.class);
        //        System.out.println(authInfo);
    }

}
