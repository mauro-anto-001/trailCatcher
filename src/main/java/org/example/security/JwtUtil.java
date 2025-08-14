package org.example.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static final String SECRET = "change-this-to-a-very-long-256-bit-secret-change-this-now!";
    private static final long EXPIRATION_MS = 1000 * 60 * 60 * 24; //24hours

    private static Key key() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public static String generateToken(String subject, Map<String, Object> claims){
        Date now = new Date();
        Date exp = new Date(now.getTime() + EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(subject)
                .addClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
    public static Jws<Claims> validate(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token);
    }

    public static String getSubject(String token) {
        return validate(token).getBody().getSubject();
    }
}
