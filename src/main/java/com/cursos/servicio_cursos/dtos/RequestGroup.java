package com.cursos.servicio_cursos.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestGroup {
  private Long groupId;
  private String name;
  private Long courseId;
  private Long teacherId;
  private int capacity;
  private List<ScheduleDto> schedules;
}