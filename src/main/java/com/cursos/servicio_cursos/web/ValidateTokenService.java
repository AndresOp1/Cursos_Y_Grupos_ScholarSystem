package com.cursos.servicio_cursos.web;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cursos.servicio_cursos.dtos.ValidateTokenResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateTokenService implements ValidateTokenPort {

  private final WebClient authenticationWebClient;

  @Override
  public boolean validateToken(String tokenHash) {
    return authenticationWebClient.get()
        .uri("/auth/token/{tokenHash}", tokenHash)
        .retrieve()
        .bodyToMono(ValidateTokenResponse.class)
        .map(t -> t.valid())
        .onErrorReturn(false)
        .block();
  }
}
