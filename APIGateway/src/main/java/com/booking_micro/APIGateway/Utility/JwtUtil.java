package com.booking_micro.APIGateway.Utility;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private String key_string =  "ThisIsTheSecretKeyImUsingForThisTHanksToMyKnowledgeOfCOurseItsPeterGriffin";
    private final SecretKey key = Keys.hmacShaKeyFor(key_string.getBytes());

    public Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }
}
