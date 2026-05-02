package com.cursos.servicio_cursos.dtos;

import java.util.List;

import com.cursos.servicio_cursos.entities.UserEntity;

import lombok.Builder;

@Builder
public record ResponseGroup(
        Long id,
        String groupName,
        UserEntity teacher,
        List<ScheduleDto> schedules,
        ResponseCourse course) {
}