package com.vvs.mainrxbackend.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {
	
	private final JwtUtil jwtUtil;
	
	public AuthenticationManager(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		
		String token = authentication.getCredentials().toString();
		String username;
		
		try {
			username = jwtUtil.extractUsername(token);
		} catch (Exception e) {
			username = null;
			System.out.println(e);
		}
		
		if (username != null && jwtUtil.validateToken(token)) {
			Claims claims = jwtUtil.getClaimsFromToken(token);
			List<String> roles = claims.get("role", List.class);
			List<SimpleGrantedAuthority> authorities = roles.stream()
											.map(SimpleGrantedAuthority::new)
											.collect(Collectors.toList());
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
			return Mono.just(authenticationToken);
		} else {
			return Mono.empty();			
		}		
	}

}
