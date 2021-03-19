package dev.group1.utils;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.group1.entities.User;

public class JwtUtil {
    private static final String SECRET = System.getenv("JWT");
    private static final Algorithm ALGO = Algorithm.HMAC512(SECRET);
    private JwtUtil() {}
    public static String generate(User user) {
        return JWT.create().withClaim("userId",user.getUserId()).withClaim("username", user.getUsername()).withClaim("displayName", user.getDisplayName()).sign(ALGO);
    }

    public static DecodedJWT isValidJWT(String token) {
        try {
            return JWT.require(ALGO).withClaimPresence("userId").withClaimPresence("username").withClaimPresence("displayName").build().verify(token);
        } catch(JWTVerificationException e) { // throws if invalid
            // will add logging later, or in an aspect
            return null;
        }
    }
}
