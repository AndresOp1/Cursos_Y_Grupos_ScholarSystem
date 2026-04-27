package com.cursos.servicio_cursos.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestGroup {
    private String groupId;
    private String name;
    private int credits;
    private Long courseId;
    private Long teacherId;
    private List<ScheduleRequest> schedules;
}