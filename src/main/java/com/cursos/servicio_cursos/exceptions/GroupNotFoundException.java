package com.cursos.servicio_cursos.exceptions;

public class GroupNotFoundException extends RuntimeException {
  public GroupNotFoundException(Long id) {
    super(String.format("Grupo de id %d no encontrado", id));

  }

  public GroupNotFoundException() {
    super("Grupo no encontrado");
  }

}
