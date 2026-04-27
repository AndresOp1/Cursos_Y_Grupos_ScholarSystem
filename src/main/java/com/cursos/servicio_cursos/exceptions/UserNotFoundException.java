package com.cursos.servicio_cursos.exceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super("Usuario no encontrado");
  }
}
