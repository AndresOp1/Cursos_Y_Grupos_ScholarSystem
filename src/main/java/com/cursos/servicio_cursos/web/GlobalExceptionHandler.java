package com.cursos.servicio_cursos.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cursos.servicio_cursos.dtos.ProblemResponse;
import com.cursos.servicio_cursos.exceptions.CourseAlreadyExistsException;
import com.cursos.servicio_cursos.exceptions.CourseNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CourseAlreadyExistsException.class)
  public ResponseEntity<ProblemResponse> handleCourseAlreadyExists(CourseAlreadyExistsException exception) {
    return new ResponseEntity<>(
        ProblemResponse.builder()
            .type("http://courses/course-already-exists")
            .title(exception.getMessage())
            .status(HttpStatus.BAD_GATEWAY.value())
            .detail("Verifica que se cumplan los valores que deben de ser unicos")
            .instance("/courses")
            .build(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CourseNotFoundException.class)
  public ResponseEntity<ProblemResponse> handleCourseNotFound(CourseNotFoundException ex) {
    return new ResponseEntity<>(ProblemResponse.builder()
        .type("http:/courses/course-not-found")
        .title(ex.getMessage())
        .status(HttpStatus.BAD_REQUEST.value())
        .detail("Asegurate que el curso que buscas si este creado")
        .instance("/courses")
        .build(),
        HttpStatus.BAD_REQUEST);
  }
}
