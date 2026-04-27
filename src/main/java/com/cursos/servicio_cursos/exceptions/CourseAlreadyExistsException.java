package com.cursos.servicio_cursos.exceptions;

public class CourseAlreadyExistsException extends RuntimeException {
  public CourseAlreadyExistsException(String message) {
    super(message);
  }
}
