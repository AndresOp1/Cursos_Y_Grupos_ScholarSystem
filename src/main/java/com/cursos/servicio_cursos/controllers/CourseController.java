package com.cursos.servicio_cursos.controllers;

import com.cursos.servicio_cursos.services.CourseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.cursos.servicio_cursos.entities.CourseEntity;
import com.cursos.servicio_cursos.dtos.RequestCourse;
import com.cursos.servicio_cursos.dtos.ResponseCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;

@RestController // Anotación para indicar que esta clase es un controlador REST para manejar
                // solicitudes HTTP
@RequestMapping("/api/courses") // Anotación para definir la ruta base para todas las solicitudes relacionadas
                                // con cursos
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    private final CourseService courseService;

    @PostMapping // Anotación para manejar solicitudes HTTP POST para crear un nuevo curso
    public ResponseEntity<CourseEntity> createCourse(@RequestBody RequestCourse requestCourse) { // toma la peticion
                                                                                                 // http y lo convierte
                                                                                                 // en un objeto de tipo
                                                                                                 // RequestCourse, que
                                                                                                 // es un DTO que
                                                                                                 // contiene los datos
                                                                                                 // necesarios para
                                                                                                 // crear un curso
        log.info("CONTROLLER: request: {}", requestCourse);
        CourseEntity newCourse = courseService.createCourse(requestCourse);
        return new ResponseEntity<>(newCourse, HttpStatus.CREATED); // Devuelve el curso creado con el estado HTTP 201
                                                                    // (CREATED) aun no entiendo bien lo de los estados.
    }

    @GetMapping // Anotación para manejar solicitudes HTTP GET para obtener la lista de cursos
    public ResponseEntity<List<ResponseCourse>> getAllCourses() {
        List<ResponseCourse> courses = courseService.finACourseName();
        return ResponseEntity.ok(courses); // Devuelve la lista de cursos con el estado HTTP 200 (OK)
    }

}
