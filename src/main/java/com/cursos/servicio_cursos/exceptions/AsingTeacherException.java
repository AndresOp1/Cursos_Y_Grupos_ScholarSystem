package com.cursos.servicio_cursos.exceptions;

public class AsingTeacherException extends RuntimeException {
  public AsingTeacherException() {
    super("Error asignando profesor a curso");
  }

}
