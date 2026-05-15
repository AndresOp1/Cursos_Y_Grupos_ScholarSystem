package com.cursos.servicio_cursos.services.impl;

import com.cursos.servicio_cursos.dtos.ScheduleDto;
import com.cursos.servicio_cursos.entities.ScheduleEntity;
import com.cursos.servicio_cursos.mappers.ScheduleMapper;
import com.cursos.servicio_cursos.repositories.ScheduleRepository;
import com.cursos.servicio_cursos.services.GroupService;
import com.cursos.servicio_cursos.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {
  private final ScheduleRepository scheduleRepo;
  private final GroupService groupService;
  private final ScheduleMapper scheduleMapper;

  @Override
  public void deleteAllByGroupId(Long groupId) {
    var group = groupService.findById(groupId);
    var schedules = scheduleRepo.findAllByGroup(group);
    scheduleRepo.deleteAll(schedules);
  }

  @Override
  public void saveAll(List<ScheduleDto> schedules, Long groupId) {
    var group = groupService.findById(groupId);
    List<ScheduleEntity> scheduleEntities = schedules.stream()
            .map(scheduleMapper::toEntity).toList();
    scheduleEntities.forEach(e -> e.setGroup(group));
    scheduleRepo.saveAll(scheduleEntities);
  }
}
