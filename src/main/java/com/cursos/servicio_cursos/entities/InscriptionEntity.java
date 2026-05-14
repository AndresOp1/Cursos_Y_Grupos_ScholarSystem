//  aca vamos a representar las incriciones de los estudiantes a los cursos, para poder asignar los estudiantes a los cursos y llevar un control de las inscripciones
// segun el diagrama de nrayan esta endidad debe conectar un usuario y un grupo osea UserEntity y GroupEntity

package com.cursos.servicio_cursos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inscriptions", schema = "courses_groups") // Tabla para almacenar las inscripciones de los estudiantes a
// los cursos
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InscriptionEntity {

  @EmbeddedId
  private InscriptionId id;

  // relacion, muchas inscripciones pertenecen a un usuario entonces seria
  // ManyToOne
  @ManyToOne
  @MapsId("userId")
  @JoinColumn(name = "student_id", nullable = false)
  private UserEntity user; // El usuario que se inscribe al curso

  // relacion, muchas inscripciones pertenecen a un grupo entonces seria ManyToOne
  @ManyToOne
  @MapsId("groupId")
  @JoinColumn(name = "group_id", nullable = false)
  private GroupEntity group; // El grupo al que se inscribe el usuario

  // Fecha y hora de la inscripción aunque no lo vi en el diagrama, veamos que
  // dice brayan
  @Column(nullable = false, name = "inscription_date")
  private LocalDateTime inscriptionDate; // Fecha y hora de la inscripción

}
