package com.cursos.servicio_cursos.exceptions;

public class InvalidTeacherException extends RuntimeException {
  public InvalidTeacherException() {
    super("Invalid course teacher");
  }

}
