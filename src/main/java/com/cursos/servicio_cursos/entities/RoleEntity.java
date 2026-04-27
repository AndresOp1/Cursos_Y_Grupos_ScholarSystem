// esta es la clase que representa a los roles de los usuarios, en esta clase vamos a tener toda la informacion de los roles, que en este caso solo va a ser el nombre del rol,
//  pero se pueden agregar mas atributos si se desea, ademas de tener una relacion con la clase UserEntity para poder asignar los roles a los usuarios

package com.cursos.servicio_cursos.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles", schema = "courses_groups") // Tabla para almacenar los roles de los usuarios
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // generamos auromaticamente el id de cada rol autoincrementable
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Nombre del rol

    // Relación: un rol puede ser asignado a muchos usuarios entonces seria
    // OneToMany
    @OneToMany(mappedBy = "role")
    private List<UserEntity> users; // Los usuarios que tienen este rol

    // Constructor vacío
    public RoleEntity() {
    }

    // Constructor con campos básicos que serian el name
    public RoleEntity(String name) {
        this.name = name;
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

}
