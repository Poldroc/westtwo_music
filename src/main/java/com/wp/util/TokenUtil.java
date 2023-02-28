package com.wp.util;

import io.jsonwebtoken.*;

import java.util.Date;

public class TokenUtil {
    private static final String secret = "secret";
    public static final String tokenHeard = "Authorization";
    private static final Long expTime = 60 * 60 * 1000L;

    public static String getToken(String name,Integer id) {
        JwtBuilder builder = Jwts.builder();
        builder.signWith(SignatureAlgorithm.HS256,secret);
        builder.setId(id.toString()).setSubject(name);
        builder.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + expTime));
        String token = builder.compact();
        return token;
    }

    public static Claims getTokenBody(String token) {
        JwtParser parser = Jwts.parser();
        Claims body = parser.setSigningKey(secret).parseClaimsJws(token).getBody();
        return body;
    }

    public static Integer getuId(String token) {
        Claims body = getTokenBody(token);
        String id = body.getId();
        return Integer.parseInt(id);
    }

}
