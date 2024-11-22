package com.micro.bank.security;

import io.jsonwebtoken.*;

import com.micro.bank.entities.User;

import java.util.Date;
import java.util.HashMap;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class Jwt {

	    private static final String SECRET_KEY = "PHPbankapp";

	    public static String extractUsername(String token) {
	        return extractClaim(token, Claims::getSubject);
	    }

	    public static Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }

	    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);
	    }

	    @SuppressWarnings("deprecation")
		private static Claims extractAllClaims(String token) {
	        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

	    }

	    private static Boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());

	    }

	    public static String generateToken(User userDetails) {
	        Map<String, Object> claims = new HashMap<>();
	        claims.put("jti",UUID.randomUUID().toString()); // Generar un JTI único
	        return createToken(claims, userDetails.getName());
	    }

	    //@SuppressWarnings("deprecation")
		private static String createToken(Map<String, Object> claims, String id) {
	        return Jwts.builder()
	                .setClaims(claims)
	                .setSubject(id)
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
	                //.signWith(SignatureAlgorithm.HS512, Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
	                .compact();
	    }

	    public static Boolean validateToken(String token, User userDetails) {
	        final String username = extractUsername(token);
	        return (username.equals(userDetails.getName())&& !isTokenExpired(token));
	    }
}
