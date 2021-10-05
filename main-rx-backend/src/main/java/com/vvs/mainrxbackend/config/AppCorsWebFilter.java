package com.vvs.mainrxbackend.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class AppCorsWebFilter {

  @Bean
  CorsWebFilter corsFilter() {

    CorsConfiguration config = new CorsConfiguration();

    // Possibly...
    // config.applyPermitDefaultValues();

    // config.setAllowedOrigins(Arrays.asList("http://185.161.208.235:4200", "http://localhost:4200", "http://localhost:80", "http://localhost:8080"));
    // config.setAllowedOrigins(Arrays.asList("http://185.161.208.235:4200", "http://185.161.208.235:80", "http://185.161.208.235:8080"));
    config.setAllowedOrigins(Arrays.asList("http://www.portfolio-dev.club:4200", "http://www.portfolio-dev.club:8080"));
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    config.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return new CorsWebFilter(source);
  }
}
