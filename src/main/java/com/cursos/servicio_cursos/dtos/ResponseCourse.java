package com.cursos.servicio_cursos.dtos;

import lombok.Builder;

@Builder
public record ResponseCourse(
                Long code,
                String name,
                int credits) {
}
