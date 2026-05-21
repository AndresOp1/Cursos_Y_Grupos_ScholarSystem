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
  private final UserMapper userMapper;

  public ResponseGroup fromEntityToResopnse(GroupEntity entity) {
    return ResponseGroup.builder()
        .id(entity.getGroupId())
        .groupName(entity.getName())
        .teacher(userMapper.fromEntityToResponse(entity.getTeacher()))
        .schedules(entity.getSchedules() == null ? List.of()
            : entity.getSchedules().stream().map(scheduleMapper::toDto).toList())
        .course(courseMapper.fromEntityToCourseResponse(entity.getCourse()))
        .build();
  }
}
