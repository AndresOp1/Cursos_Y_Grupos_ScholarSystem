package com.cursos.servicio_cursos.dtos;

import lombok.Builder;

import java.util.List;

@Builder
public record UpdateGroupRequest(
        String groupName,
        int capacity,
        Long teacherId,
        List<ScheduleDto> schedules,
        List<Long> studentsIds) {
}
