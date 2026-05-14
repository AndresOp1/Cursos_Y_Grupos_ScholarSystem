package com.cursos.servicio_cursos.controllers;

import com.cursos.servicio_cursos.services.CourseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.cursos.servicio_cursos.entities.CourseEntity;
import com.cursos.servicio_cursos.dtos.PageResponse;
import com.cursos.servicio_cursos.dtos.RequestCourse;
import com.cursos.servicio_cursos.dtos.ResponseCourse;
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

    @PostMapping
    public ResponseEntity<CourseEntity> createCourse(@RequestBody RequestCourse requestCourse) {
        log.info("CONTROLLER: request: {}", requestCourse);
        CourseEntity newCourse = courseService.createCourse(requestCourse);
        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ResponseCourse> getCourseByCode(@PathVariable("code") Long code) {
        return ResponseEntity.ok(
                courseService.findCourseByCode(code));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ResponseCourse>> getAllCourses(@RequestParam(required = false) String text,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "6") int size) {
        return ResponseEntity.ok(courseService.findAllCourses(text, page, size));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ResponseCourse>> getCoursesByUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(courseService.findCoursesByUserId(userId));
    }

}
