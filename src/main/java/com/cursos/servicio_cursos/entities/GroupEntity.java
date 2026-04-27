// debo recordar que las entidades son clases que representan una tabla en la base de datos y cada objeto es una fila y cada atributo una columna
// esta es la clase en la que vamos a representar los grupos de los cursos, para poder asignar los cursos a los grupos y llevar un control de los grupos
//guiandome por el diagrama de brayan esta clase debe tener una relacion con la clase UserEntity y ScheduleEntity, ademas de tener un atributos
// de (id, code, name. creditos, curso, profesor, horarios, inscripciones) aunque horaros no se sabe.

package com.cursos.servicio_cursos.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "groups", schema = "courses_groups")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // Codigo unico del grupo

    @Column(nullable = false)
    private String name; // Nombre del grupo

    @Column(nullable = false)
    private int credits; // Creditos del grupo

    // la recion es que muchos grupos pertenecen a un curos entonces seria ManyToOne
    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course; // El curso al que pertenece el grupo

    // la relacion es que muchos grupos pertenecen a un profesor entonces seria
    // ManyToOne
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private UserEntity teacher; // El profesor que tendra el grupo

    // la relacion es que un grupo tiene muchos horarios entonces seria OneToMany
    @OneToMany(mappedBy = "group")
    private List<ScheduleEntity> schedules; // Los horarios del grupo

    // la relacion es que un grupo tiene muchas inscripciones entonces seria
    // OneToMany
    @OneToMany(mappedBy = "group")
    private List<InscriptionEntity> inscriptions; // Las inscripciones al grupo

    // Constructor vacío
    public GroupEntity() {
    }

    // Constructor con campos básicos que serian el code, name, curso
    public GroupEntity(String code, String name, CourseEntity course) {
        this.code = code;
        this.name = name;
        this.course = course;

    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public UserEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(UserEntity teacher) {
        this.teacher = teacher;
    }

    public List<ScheduleEntity> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleEntity> schedules) {
        this.schedules = schedules;
    }

    public List<InscriptionEntity> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(List<InscriptionEntity> inscriptions) {
        this.inscriptions = inscriptions;
    }

}
