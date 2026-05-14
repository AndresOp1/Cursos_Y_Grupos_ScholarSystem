package com.cursos.servicio_cursos.exceptions;

public class CourseNotFoundException extends RuntimeException {
  public CourseNotFoundException() {
    super("Curso no encontrado");
  }
}
