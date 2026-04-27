package com.cursos.servicio_cursos.mappers;

import org.springframework.stereotype.Component;

import com.cursos.servicio_cursos.dtos.ResponseCourse;
import com.cursos.servicio_cursos.entities.CourseEntity;

@Component
public class CourseMapper {
  public ResponseCourse fromEntityToCourseResponse(CourseEntity entity) {
    return ResponseCourse.builder()
        .code(entity.getCode())
        .credits(entity.getCredits())
        .name(entity.getName())
        .build();
  }
}
