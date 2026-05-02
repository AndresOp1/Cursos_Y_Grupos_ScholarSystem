package com.cursos.servicio_cursos.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;

@Configuration
public class WebConfiguration {
  @Value("${authentication.service.url}")
  private String authenticationServiceUrl;

  @Value("${spring.cloud.azure.storage.connection-string}")
  private String connectionString;
  @Value("${spring.cloud.azure.storage.queue.queue-name}")
  private String queueName;

  @Bean
  public WebClient authenticationWebClient() {
    return WebClient.builder()
        .baseUrl(authenticationServiceUrl)
        .build();
  }

  @Bean
  public QueueClient queueClient() {
    return new QueueClientBuilder()
        .connectionString(connectionString)
        .queueName(queueName)
        .buildClient();
  }
}
