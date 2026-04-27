// esta es una clase en la que vamos a representar los horarios de los cursos y la relacion con los cursos, para poder asignar los horarios a los cursos

package com.cursos.servicio_cursos.entities;

import com.cursos.servicio_cursos.enums.DayOfWeek; 
import java.time.LocalTime;
import jakarta.persistence.*;

@Entity
@Table(name = "schedules") // Tabla para almacenar los horarios de los cursos
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek day; // Día de la semana del horario

    @Column(nullable = false)
    private LocalTime  timeStar; // Hora de inicio del horario

    @Column(nullable = false)
    private LocalTime timeEnd; // Hora de fin del horario

    // Relación: muchos horarios pertenecen a un grupo
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group; // El grupo al que pertenece este horario

    //@ManyToMany 
    //private CourseEntity course; // Los cursos a los que pertenece este horario, me toco crearlo por que una busqueda en ScheduleRepository me lo
    //  pedia para buscar horarios por el ID del curso, entoces como un curso puede tener muchos horarios es oneToMany, me toco comentarlo para que puediera correr el codigo
    //toca que mirar como hacer la busqueda por el id del surso.

    // Constructor vacío
    public ScheduleEntity() {}

    // Constructor con campos básicos que serian el dia, inicio, final y grupo
    public ScheduleEntity(DayOfWeek day, LocalTime timeStar, LocalTime timeEnd, GroupEntity group) {
        this.day = day;
        this.timeStar = timeStar;
        this.timeEnd = timeEnd;
        this.group = group;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public LocalTime getTimeStar() {
        return timeStar;
    }

    public void setTimeStar(LocalTime timeStar) {
        this.timeStar = timeStar;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

}
