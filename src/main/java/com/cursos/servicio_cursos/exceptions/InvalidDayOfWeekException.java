package com.cursos.servicio_cursos.exceptions;

public class InvalidDayOfWeekException extends RuntimeException {
  public InvalidDayOfWeekException() {
    super("Dia de la semana invalido");
  }
}
