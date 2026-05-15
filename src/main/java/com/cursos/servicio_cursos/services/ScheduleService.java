package com.cursos.servicio_cursos.services;

import com.cursos.servicio_cursos.dtos.ScheduleDto;

import java.util.List;

public interface ScheduleService {
  void deleteAllByGroupId(Long groupId);

  void saveAll(List<ScheduleDto> schedules, Long groupId);
}
