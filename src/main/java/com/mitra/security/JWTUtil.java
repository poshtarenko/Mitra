package com.mitra.security;

import com.mitra.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtil {

    private static final String SECRET_KEY = "ltwgowg435342656nggnerngehttrjjn412ng121fpowfegwirehrewrl";

    public static String createToken(String username, Role role) {
        Claims claims = Jwts.claims();
        claims.put("user", username);
        claims.put("role", role.name());

        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String extractRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return (String) claims.get("role");
    }
}