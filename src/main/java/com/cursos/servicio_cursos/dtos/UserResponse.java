package com.cursos.servicio_cursos.dtos;

import lombok.Builder;

@Builder
public record UserResponse(
    Long id,
    String fullName,
    String role,
    String email) {

}
