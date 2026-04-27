package com.cursos.servicio_cursos.mappers;

import org.springframework.stereotype.Component;

import com.cursos.servicio_cursos.dtos.ScheduleDto;
import com.cursos.servicio_cursos.entities.ScheduleEntity;
import com.cursos.servicio_cursos.enums.DayOfWeek;

@Component
public class ScheduleMapper {

  public ScheduleEntity toEntity(ScheduleDto req) throws IllegalArgumentException {
    return ScheduleEntity.builder()
        .day(DayOfWeek.valueOf(req.day()))
        .timeStar(req.startsTime())
        .timeEnd(req.endTime()).build();
  }

  public ScheduleDto toDto(ScheduleEntity entity) {
    return ScheduleDto.builder()
        .day(entity.getDay().toString())
        .startsTime(entity.getTimeStar())
        .endTime(entity.getTimeEnd())
        .build();
  }
}
