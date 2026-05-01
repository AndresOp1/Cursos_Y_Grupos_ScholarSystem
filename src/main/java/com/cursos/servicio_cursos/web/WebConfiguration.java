package com.cursos.servicio_cursos.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfiguration {
  @Value("${authentication.service.url}")
  private String authenticationServiceUrl;

  @Bean
  public WebClient authenticationWebClient() {
    return WebClient.builder()
        .baseUrl(authenticationServiceUrl)
        .build();
  }
}
