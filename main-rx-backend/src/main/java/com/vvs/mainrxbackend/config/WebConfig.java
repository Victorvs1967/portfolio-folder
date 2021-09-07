package com.vvs.mainrxbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebConfig implements WebFluxConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins("http://www.portfolio-dev.club:4200", "http://185.161.208.235:4200", "http://v10549.dh.net.ua:4200") // http://localhost:4200
      // .allowedOrigins("http://v10549.dh.net.ua:4200") // http://localhost:4200
      .allowedMethods("*")
      .allowedHeaders("*")
      .allowCredentials(true)
      .maxAge(3600);
  }

}
