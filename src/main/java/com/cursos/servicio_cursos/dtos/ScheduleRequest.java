package com.cursos.servicio_cursos.dtos;

import java.time.LocalTime;

public record ScheduleRequest(
    String day,
    LocalTime startsTime,
    LocalTime endTime) {

}
