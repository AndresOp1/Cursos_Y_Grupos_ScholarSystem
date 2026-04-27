package com.cursos.servicio_cursos.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cursos.servicio_cursos.dtos.ResponseGroup;
import com.cursos.servicio_cursos.entities.GroupEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GroupMapper {
  private final ScheduleMapper scheduleMapper;
  private final CourseMapper courseMapper;

  public ResponseGroup fromEntityToResopnse(GroupEntity entity) {
    return ResponseGroup.builder()
        .groupName(entity.getName())
        .teacherId(entity.getTeacher() == null ? null : entity.getTeacher().getId())
        .schedules(entity.getSchedules() == null ? List.of()
            : entity.getSchedules().stream().map(scheduleMapper::toDto).toList())
        .course(courseMapper.fromEntityToCourseResponse(entity.getCourse()))
        .build();
  }
}
