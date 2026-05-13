package com.cursos.servicio_cursos.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
public class ResponseCourse {
    private Long code;
    private String name;
    private int credits;
    private Long ngroups;

    public ResponseCourse(Long code, String name, int credits, Long ngroups) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.ngroups = ngroups;
    }
}
