package com.cursos.servicio_cursos.dtos;

import java.time.LocalTime;

import lombok.Builder;

@Builder
public record ScheduleDto(
                String day,
                LocalTime startsTime,
                LocalTime endTime) {

}
