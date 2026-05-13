// esta es una clase en la que vamos a representar los horarios de los cursos y la relacion con los cursos, para poder asignar los horarios a los cursos

package com.cursos.servicio_cursos.entities;

import com.cursos.servicio_cursos.enums.DayOfWeek;
import java.time.LocalTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "schedules", schema = "courses_groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // Tabla para almacenar los horarios de los cursos
@ToString
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek day; // Día de la semana del horario

    @Column(nullable = false, name = "start_time")
    private LocalTime timeStar; // Hora de inicio del horario

    @Column(nullable = false, name = "end_time")
    private LocalTime timeEnd; // Hora de fin del horario

    // Relación: muchos horarios pertenecen a un grupo
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group; // El grupo al que pertenece este horario

    // @ManyToMany
    // private CourseEntity course; // Los cursos a los que pertenece este horario,
    // me toco crearlo por que una busqueda en ScheduleRepository me lo
    // pedia para buscar horarios por el ID del curso, entoces como un curso puede
    // tener muchos horarios es oneToMany, me toco comentarlo para que puediera
    // correr el codigo
    // toca que mirar como hacer la busqueda por el id del surso.

}
