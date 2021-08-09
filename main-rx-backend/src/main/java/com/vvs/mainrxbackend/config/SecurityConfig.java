package com.vvs.mainrxbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
	
	private final AuthenticationManager authenticationManager;
	private final SecurityContextRepository securityContextRepository;
	
	public SecurityConfig(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
		this.authenticationManager = authenticationManager;
		this.securityContextRepository = securityContextRepository;
	}
  
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return http
						.exceptionHandling()
						.authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
						.accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN))).and()
								.csrf().disable()
								.cors().disable()
								.formLogin().disable()
								.httpBasic().disable()
								.authenticationManager(authenticationManager)
								.securityContextRepository(securityContextRepository)
								.authorizeExchange()
								.pathMatchers("/**").permitAll()
								.anyExchange().authenticated().and()
								.build();
	}

	@Bean
	public MapReactiveUserDetailsService userDetailsService() {
		UserDetails user = User
									.withUsername("user")
									.password(passwordEncoder().encode("password"))
									.roles("USER")
									.build();
			
		return new MapReactiveUserDetailsService(user);
	}
}
