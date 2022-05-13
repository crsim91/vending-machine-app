package com.example.vendingmachine.security;

import com.example.vendingmachine.properties.ApplicationProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

	public static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String AUTHORITIES_KEY = "auth";
	private final Logger log = LoggerFactory.getLogger(TokenProvider.class);
	private final ApplicationProperties applicationProperties;
	private String secretKey;
	private long tokenValidityInMilliseconds;
	private Key key;

	public TokenProvider(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	@PostConstruct
	public void init() {
		this.secretKey = applicationProperties.getSecret();

		this.tokenValidityInMilliseconds =
				1000 * applicationProperties.getTokenValidityInSeconds();

		key = Keys.hmacShaKeyFor(secretKey.getBytes());
	}

	public String createToken(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		Date validity = new Date((new Date()).getTime() + this.tokenValidityInMilliseconds);

		return Jwts.builder()
				.setSubject(authentication.getName())
				.claim(AUTHORITIES_KEY, authorities)
				.signWith(key, SignatureAlgorithm.HS256)
				.setExpiration(validity)
				.compact();
	}

	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();

		Collection<? extends GrantedAuthority> authorities =
				Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());

		User principal = new User(claims.getSubject(), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException e) {
			log.info("Invalid JWT token.");
			log.trace("Invalid JWT token trace: {0}", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT token.");
			log.trace("Expired JWT token trace: {0}", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT token.");
			log.trace("Unsupported JWT token trace: {0}", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT token compact of handler are invalid.");
			log.trace("JWT token compact of handler are invalid trace: {0}", e);
		} catch (Exception e) {
			log.info("Invalid JWT token.");
			log.trace("Invalid JWT token trace: {0}", e);
		}
		return false;
	}

}