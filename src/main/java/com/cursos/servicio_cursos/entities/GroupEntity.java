// debo recordar que las entidades son clases que representan una tabla en la base de datos y cada objeto es una fila y cada atributo una columna
// esta es la clase en la que vamos a representar los grupos de los cursos, para poder asignar los cursos a los grupos y llevar un control de los grupos
//guiandome por el diagrama de brayan esta clase debe tener una relacion con la clase UserEntity y ScheduleEntity, ademas de tener un atributos
// de (id, code, name. creditos, curso, profesor, horarios, inscripciones) aunque horaros no se sabe.

package com.cursos.servicio_cursos.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.util.List;

@Entity
@Table(name = "groups", schema = "courses_groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GroupEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, unique = true, name = "group_id")
  private long groupId; // Codigo unico del grupo

  @Column(nullable = false)
  private String name; // Nombre del grupo

  // la recion es que muchos grupos pertenecen a un curos entonces seria ManyToOne
  @ManyToOne
  @JoinColumn(name = "course_code")
  private CourseEntity course; // El curso al que pertenece el grupo

  // la relacion es que muchos grupos pertenecen a un profesor entonces seria
  // ManyToOne
  @ManyToOne
  @JoinColumn(name = "teacher_id")
  private UserEntity teacher; // El profesor que tendra el grupo

  // la relacion es que un grupo tiene muchos horarios entonces seria OneToMany
  @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
  private List<ScheduleEntity> schedules; // Los horarios del grupo

  // la relacion es que un grupo tiene muchas inscripciones entonces seria
  // OneToMany
  @OneToMany(mappedBy = "group")
  private List<InscriptionEntity> inscriptions; // Las inscripciones al grupo

  @Check(constraints = "capacity >= 10")
  @Column(nullable = true, name = "capacity", columnDefinition = "INT DEFAULT 10")
  private int capacity; // La capacidad del grupo

}
