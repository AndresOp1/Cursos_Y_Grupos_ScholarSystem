package com.cursos.servicio_cursos.dtos;

public record ValidateTokenResponse(
    String tokenHash,
    boolean valid) {

}
