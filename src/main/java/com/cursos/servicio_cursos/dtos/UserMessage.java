package com.cursos.servicio_cursos.dtos;

import lombok.Builder;

@Builder
public record UserMessage(
    Long id,
    String email,
    String fullName,
    String role) {

}
