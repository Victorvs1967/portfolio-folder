package com.vvs.mainrxbackend.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

// @Configuration
// public class CorsWebFilterConfig {

//   @Bean
//   CorsWebFilter crosWebFilter() {
//     CorsConfiguration corsConfig = new CorsConfiguration();
//     corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//     corsConfig.setMaxAge(8000L);
//     corsConfig.addAllowedMethod("PUT");
//     corsConfig.addAllowedMethod("GET");
//     corsConfig.addAllowedHeader("Access-Control-Allow-Origin");
//     corsConfig.addAllowedHeader("Origin");

//     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
//     source.registerCorsConfiguration("/**", corsConfig);

//     return new CorsWebFilter(source);
//   }

//}