// esta es la clase que representa a los cursos, en esta clase vamos a tener toda la informacion de los cursos, como el nombre, la descripcion, el horario, el profesor, etc.
// guiandome por el diagrama de brayan esta clase debe tener una relacion con la  clase GroupEntity, ademas de tener un atributos de (id, name, credits) 
// 
package com.cursos.servicio_cursos.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "courses", schema = "courses_groups") // Tabla para almacenar los cursos
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // Código único del curso

    @Column(nullable = false, unique = true)
    private String name; // Nombre del curso

    @Column(nullable = false)
    private int credits; // Creditos del curso

    // Relación: un curso tiene muchos grupos entonces seria OneToMany
    @OneToMany(mappedBy = "course")
    private List<GroupEntity> groups; // Los grupos que pertenecen al curso

    // Constructor vacío
    public CourseEntity() {
    }

    // Constructor con campos básicos que serian el name y credits
    public CourseEntity(String name, int credits) {
        this.name = name;
        this.credits = credits;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
