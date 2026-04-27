// esta es la clase que representa a los cursos, en esta clase vamos a tener toda la informacion de los cursos, como el nombre, la descripcion, el horario, el profesor, etc.
// guiandome por el diagrama de brayan esta clase debe tener una relacion con la  clase GroupEntity, ademas de tener un atributos de (id, name, credits) 
// 
package com.cursos.servicio_cursos.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "courses", schema = "courses_groups") // Tabla para almacenar los cursos
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseEntity {
    @Id
    private Long code; // Código único del curso

    @Column(nullable = false, unique = true)
    private String name; // Nombre del curso

    @Column(nullable = false)
    private int credits; // Creditos del curso

    // Relación: un curso tiene muchos grupos entonces seria OneToMany
    @OneToMany(mappedBy = "course")
    private List<GroupEntity> groups; // Los grupos que pertenecen al curso
}
