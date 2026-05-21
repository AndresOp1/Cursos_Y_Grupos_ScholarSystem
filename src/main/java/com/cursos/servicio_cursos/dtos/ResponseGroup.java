package com.cursos.servicio_cursos.dtos;

import java.util.List;

import lombok.Builder;

@Builder
public record ResponseGroup(
    Long id,
    String groupName,
    UserResponse teacher,
    List<ScheduleDto> schedules,
    ResponseCourse course) {
}