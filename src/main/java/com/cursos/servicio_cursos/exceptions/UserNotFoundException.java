package com.cursos.servicio_cursos.exceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super("Usuario no encontrado");
  }

  public UserNotFoundException(String email) {
    super(String.format("Usuario con email %s no encontrado", email));
  }
}
