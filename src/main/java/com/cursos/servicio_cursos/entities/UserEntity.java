package com.cursos.servicio_cursos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users", schema = "courses_groups")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class UserEntity {

  @Id
  @Column(name = "user_id")
  private Long id;

  @Column(nullable = false)
  private String fullName;

  @Column(nullable = false, unique = true)
  private String email;

  // Relación: Muchos usuarios tienen UN rol
  @ManyToOne
  @JoinColumn(name = "role")
  private RoleEntity role;

  // Relación: Un usuario tiene muchas inscripciones a través de las cuales se
  // relaciona con los grupos
  @OneToMany(mappedBy = "user")
  private List<InscriptionEntity> inscriptions;

}