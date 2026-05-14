package com.cursos.servicio_cursos.exceptions;

public class InvalidGroupCapacityException extends RuntimeException {
  public InvalidGroupCapacityException() {
    super("La capacidad minima para los grupos de 10 estudiantes");
  }
}
