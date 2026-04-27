//  aca vamos a representar las incriciones de los estudiantes a los cursos, para poder asignar los estudiantes a los cursos y llevar un control de las inscripciones
// segun el diagrama de nrayan esta endidad debe conectar un usuario y un grupo osea UserEntity y GroupEntity

package com.cursos.servicio_cursos.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inscriptions", schema = "courses_groups") // Tabla para almacenar las inscripciones de los estudiantes a
                                                         // los cursos
public class InscriptionEntity {

    // relacion, muchas inscripciones pertenecen a un usuario entonces seria
    // ManyToOne
    @ManyToOne
    @Id
    @JoinColumn(name = "student_id")
    private UserEntity user; // El usuario que se inscribe al curso

    // relacion, muchas inscripciones pertenecen a un grupo entonces seria ManyToOne
    @ManyToOne
    @Id
    @JoinColumn(name = "group_id")
    private GroupEntity group; // El grupo al que se inscribe el usuario

    // Fecha y hora de la inscripción aunque no lo vi en el diagrama, veamos que
    // dice brayan
    @Column(nullable = false, name = "inscription_date")
    private LocalDateTime inscriptionDate; // Fecha y hora de la inscripción

    // Constructor vacío
    public InscriptionEntity() {
    }

    // Constructor con campos básicos que serian el usuario, grupo y fecha de
    // inscripcion
    public InscriptionEntity(UserEntity user, GroupEntity group) {
        this.user = user;
        this.group = group;
        this.inscriptionDate = LocalDateTime.now(); // Asignamos la fecha y hora actual al momento de crear la
                                                    // inscripción

    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public LocalDateTime getInscriptionDate() {
        return inscriptionDate;
    }

    public void setInscriptionDate(LocalDateTime inscriptionDate) {
        this.inscriptionDate = inscriptionDate;
    }

}
