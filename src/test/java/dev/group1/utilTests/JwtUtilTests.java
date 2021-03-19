package dev.group1.utilTests;

import com.auth0.jwt.interfaces.DecodedJWT;
import dev.group1.entities.User;
import org.junit.jupiter.api.*;
import dev.group1.utils.JwtUtil;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JwtUtilTests {
    private static String jwt;
    private static User user;

    @Test
    @Order(1)
    void generate_jwt() {
        user = new User();
        user.setUserId(1);
        user.setDisplayName("Test User 1");
        user.setUsername("TEST_USER_1");
        jwt = JwtUtil.generate(user);
        Assertions.assertNotNull(jwt);
    }

    @Test
    @Order(2)
    void decoded_jwt() {
        DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);
        Assertions.assertEquals(user.getUsername(),decodedJWT.getClaim("username").asString());
        Assertions.assertEquals(user.getUserId(), decodedJWT.getClaim("userId").asInt());
        Assertions.assertEquals(user.getDisplayName(), decodedJWT.getClaim("displayName").asString());
    }
}