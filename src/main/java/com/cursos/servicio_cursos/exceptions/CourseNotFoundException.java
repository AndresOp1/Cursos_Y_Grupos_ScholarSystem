package com.cursos.servicio_cursos.exceptions;

public class CourseNotFoundException extends RuntimeException {
  public CourseNotFoundException() {
    super("Curso no encontrado");
  }

  public CourseNotFoundException(Long code) {
    super(String.format("Curso de codigo %d no encontrado", code));
  }
}
