package com.cursos.servicio_cursos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inscriptions", schema = "courses_groups")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InscriptionEntity {

  @EmbeddedId
  private InscriptionId id;

  @ManyToOne
  @MapsId("userId")
  @JoinColumn(name = "student_id", nullable = false)
  private UserEntity user;

  @ManyToOne
  @MapsId("groupId")
  @JoinColumn(name = "group_id", nullable = false)
  private GroupEntity group;


  @Column(nullable = false, name = "inscription_date")
  private LocalDateTime inscriptionDate;

}
