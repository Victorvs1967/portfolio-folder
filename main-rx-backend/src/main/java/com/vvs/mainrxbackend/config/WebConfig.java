package com.vvs.mainrxbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins("http://localhost:4200", "http://localhost:80", "http://localhost:8080")
      // .allowedOrigins("http://www.portfolio-dev.club", "http://www.portfolio-dev.club:4200", "http://185.161.208.235:8080", "http://localhost:4200", "http://localhost:8080")
      .allowedMethods("*")
      .allowedHeaders("*")
      .allowCredentials(true)
      .maxAge(3600);
  }

}
