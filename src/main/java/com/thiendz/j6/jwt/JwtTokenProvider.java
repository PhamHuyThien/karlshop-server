package com.thiendz.j6.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.thiendz.j6.service.impl.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

	private final String JWT_SECRET = "thiendeptraii";

	private final long JWT_EXPIRATION = 604800000L;

	public String generateToken(UserDetailsImpl userDetailsImpl) {
		//
		Date nowDate = new Date();
		Date expiredDate = new Date(nowDate.getTime() + JWT_EXPIRATION);
		//
		JwtBuilder jwtBuilder = Jwts.builder();
		jwtBuilder.setSubject(userDetailsImpl.getAccount().getId()+"");
		jwtBuilder.claim("author", "PhamHuyThien");
		jwtBuilder.claim("api_version", "1.0");
		jwtBuilder.setIssuedAt(nowDate);
		jwtBuilder.setExpiration(expiredDate);
		jwtBuilder.signWith(SignatureAlgorithm.HS512, JWT_SECRET);
		return jwtBuilder.compact();
	}

	public Integer getUserIdFromJWT(String token) {
		//
		JwtParser jwtParser = Jwts.parser();
		jwtParser.setSigningKey(JWT_SECRET);
		//
		Claims claims = jwtParser.parseClaimsJws(token).getBody();
		//
		return Integer.parseInt(claims.getSubject());
	}

	
	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException ex) {
			log.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			log.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			log.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty.");
		}
		return false;
	}
}
