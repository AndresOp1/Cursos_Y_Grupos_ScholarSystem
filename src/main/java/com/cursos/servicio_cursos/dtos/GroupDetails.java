package com.cursos.servicio_cursos.dtos;

import java.util.List;

import lombok.Builder;

@Builder
public record GroupDetails(
    Long id,
    String groupName,
    int capacity,
    UserResponse teacher,
    List<ScheduleDto> schedules,
    ResponseCourse course,
    List<UserResponse> students) {
}