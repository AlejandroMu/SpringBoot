package com.example.demo.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

    // Replace this with a secure key in a real application, ideally fetched from environment variables
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    // Generate token with given user name
    public String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
		String userName = authentication.getName();
		List<String> roles = authentication.getAuthorities()
                .stream()
                .map(a -> {
                    if (a.getAuthority().startsWith("ROLE_")) {
                        return a.getAuthority().substring(5);
                    }
                    return a.getAuthority();
                })
                .toList();
		claims.put("roles", roles);
		claims.put("username", userName);
        return createToken(claims, userName);
    }

    // Create a JWT token with specified claims and subject (user name)
    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // Token valid for 30 minutes
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Get the signing key for JWT token
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Extract the username from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract the expiration date from the token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract a claim from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Check if the token is expired
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

	public UserDetails getUserDetails(String token) {
		Claims claims = extractAllClaims(token);
		String username = claims.getSubject();
		List<String> roles = claims.get("roles", List.class);
		return org.springframework.security.core.userdetails.User.builder()
				.username(username)
				.password("topken")
				.roles(roles.toArray(new String[0]))
				.build();
	}
}