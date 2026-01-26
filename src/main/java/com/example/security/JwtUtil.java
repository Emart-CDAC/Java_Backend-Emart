package com.example.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	public String extractEmail(String token) {
		return getClaims(token).getSubject();
	}

	public String extractRole(String token) {
		return getClaims(token).get("role", String.class);
	}

	public Date extractExpiration(String token) {
		return getClaims(token).getExpiration();
	}

	public boolean isValid(String token) {
		try {
			getClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(String email, String role, Integer userId) {

		if (!role.startsWith("ROLE_")) {
			role = "ROLE_" + role;
		}

		return Jwts.builder().setSubject(email)
				.claim("role", role)
				.claim("userId", userId)
				.setIssuer("EmartAuthService")
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1 * 60 * 60 * 1000))
				.signWith(getSigningKey())
				.compact();
	}

}
