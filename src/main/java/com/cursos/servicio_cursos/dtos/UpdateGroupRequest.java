package com.cursos.servicio_cursos.dtos;

import java.util.List;
import lombok.Builder;

@Builder
public record UpdateGroupRequest(
        Long id,
        String groupName,
        int capacity,
        Long teacherId,
        List<ScheduleDto> schedules,
        Long courseId,
        List<Long> studentsIds) {

}
