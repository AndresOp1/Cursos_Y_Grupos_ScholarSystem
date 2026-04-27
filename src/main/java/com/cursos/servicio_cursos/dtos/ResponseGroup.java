package com.cursos.servicio_cursos.dtos;

import java.util.List;

import lombok.Builder;

@Builder
public record ResponseGroup(
    String groupName,
    Long teacherId,
    List<ScheduleDto> schedules,
    ResponseCourse course) {
}