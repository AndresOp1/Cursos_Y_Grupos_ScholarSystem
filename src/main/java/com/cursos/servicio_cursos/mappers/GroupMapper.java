package com.cursos.servicio_cursos.mappers;

import com.cursos.servicio_cursos.dtos.ResponseGroup;
import com.cursos.servicio_cursos.entities.GroupEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupMapper {
  private final ScheduleMapper scheduleMapper;
  private final CourseMapper courseMapper;
  private final UserMapper userMapper;

  public ResponseGroup fromEntityToResponse(GroupEntity entity) {
    return ResponseGroup.builder()
            .id(entity.getGroupId())
            .groupName(entity.getName())
            .teacher(entity.getTeacher() == null ? null : userMapper.fromEntityToResponse(entity.getTeacher()))
            .schedules(entity.getSchedules() == null ? List.of()
                    : entity.getSchedules().stream().map(scheduleMapper::toDto).toList())
            .course(courseMapper.fromEntityToCourseResponse(entity.getCourse()))
            .build();
  }
}
