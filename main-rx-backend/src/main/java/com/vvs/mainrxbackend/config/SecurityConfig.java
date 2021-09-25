package com.vvs.mainrxbackend.config;

// import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.reactive.CorsConfigurationSource;
// import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    // private static final String FRONTEND_LOCALHOST = "http://localhost:4200";

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private SecurityContextRepository securityContextRepository;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return http
						.csrf().disable()
						.cors().disable()
						.exceptionHandling()
						.authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
						.accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
						.and()
						.formLogin().disable()
						.httpBasic().disable()
						.authenticationManager(authenticationManager)
						.securityContextRepository(securityContextRepository)
						.authorizeExchange().pathMatchers("/login", "/signup", "/favicon.icon").permitAll()
						.pathMatchers("/**").hasAnyRole("USER", "ADMIN")
						.anyExchange()
						.authenticated().and()
						.build();
	}

	// @Bean
	// CorsConfigurationSource corsConfigurationSource() {
	// 	CorsConfiguration config = new CorsConfiguration();
	// 	config.applyPermitDefaultValues();
	// 	config.addAllowedMethod("*");
	// 	config.setAllowCredentials(true);
	// 	config.setAllowedOrigins(Arrays.asList(FRONTEND_LOCALHOST));

	// 	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	// 	source.registerCorsConfiguration("/**", config);
	// 	return source;
	// }

}
