package org.framework.integration.security.core.utils;

import org.junit.jupiter.api.Test;

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
        String token = JwtUtil.createToken(claim, 10 * 60 * 1000);
        System.out.println(JwtUtil.getCustomClaims(token, User.class));

        var claimTypeMapping = new HashMap<String, Class>();
        claim.put(User.class.getSimpleName(), User.class);
        claim.put(Student.class.getSimpleName(), Student.class);
        System.out.println(JwtUtil.getCustomClaims(token, claimTypeMapping));

    }

}
