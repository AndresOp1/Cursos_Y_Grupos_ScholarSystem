package com.cursos.servicio_cursos.dtos;

import java.util.List;

public record RequestStudentsInscriptions(
    List<String> studentsEmails,
    Long groupId) {
}
