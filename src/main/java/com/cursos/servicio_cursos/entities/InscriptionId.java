package com.cursos.servicio_cursos.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class InscriptionId implements Serializable {
  private Long userId;
  private Long groupId;
}
