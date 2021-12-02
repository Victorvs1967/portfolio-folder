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
    // .allowedOrigins("http://localhost:4200", "http://localhost:80")
      .allowedOrigins("http://www.portfolio-dev.club", "http://www.portfolio-dev.club:4200", "http://portfolio-dev.club", "http://portfolio-dev.club:4200")
      .allowedMethods("*")
      .allowCredentials(true)
      .maxAge(3600);
  }

}
