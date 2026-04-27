package com.cursos.servicio_cursos.mappers;

import org.springframework.stereotype.Component;

import com.cursos.servicio_cursos.dtos.ScheduleRequest;
import com.cursos.servicio_cursos.entities.ScheduleEntity;
import com.cursos.servicio_cursos.enums.DayOfWeek;

@Component
public class ScheduleMapper {

  public ScheduleEntity toEntity(ScheduleRequest req) throws IllegalArgumentException {
    return ScheduleEntity.builder()
        .day(DayOfWeek.valueOf(req.day()))
        .timeStar(req.startsTime())
        .timeEnd(req.endTime()).build();
  }
}
