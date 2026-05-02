package com.cursos.servicio_cursos.exceptions;

public class InvalidRoleException extends RuntimeException {
  public InvalidRoleException(String role) {
    super(String.format("Rol %s no es valido", role));
  }

}
