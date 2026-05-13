// los servicio se encarga de la logica del negocio como tal donde voy a escribir las reglas del sistema, si hago una analogica con una biblioteca 
//digamos que el repositorio seria el bibliotecario que me trae un libro y el servicio  seria el que decide que libros trar y que hacer con ellos, tambien se encarga de hacer
//  las validaciones necesarias para que el sistema funcione correctamente, por ejemplo si quiero crear un nuevo rol en el sistema, el servicio se encargaria de validar que el 
// nombre del rol no este vacio o que no exista otro rol con el mismo nombre antes de guardarlo en la base de datos a traves del repositorio. jjaja mucho texto zorry brayan.

package com.cursos.servicio_cursos.services;

import com.cursos.servicio_cursos.dtos.PageResponse;
import com.cursos.servicio_cursos.dtos.RequestCourse;
import com.cursos.servicio_cursos.dtos.ResponseCourse;
import com.cursos.servicio_cursos.entities.CourseEntity;
import com.cursos.servicio_cursos.exceptions.CourseAlreadyExistsException;
import com.cursos.servicio_cursos.exceptions.CourseNotFoundException;
import com.cursos.servicio_cursos.repositories.CourseRepository;
import com.cursos.servicio_cursos.repositories.InscriptionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final InscriptionRepository inscriptionRepository;

    // crear un nuevo curso
    public CourseEntity createCourse(RequestCourse requestCourse) {
        log.info("Peticion de guardar curso: {}", requestCourse);

        if (requestCourse.credits() <= 0) {
            throw new IllegalArgumentException("El nuevo curso no puede tener creditos negativos");
        }

        // validate if course already exists to send a exception
        if (courseRepository.findById(requestCourse.code()).isPresent()) {
            throw new CourseAlreadyExistsException(
                    String.format("Curso con el codigo %d ya existe", requestCourse.code()));
        }

        // validate that there is not other course with the same name
        if (courseRepository.findByName(requestCourse.name()).isPresent()) {
            throw new CourseAlreadyExistsException(
                    String.format("Curso con el nombre %s ya existe", requestCourse.name()));
        }

        CourseEntity course = CourseEntity.builder()
                .code(requestCourse.code())
                .credits(requestCourse.credits())
                .name(requestCourse.name())
                .build();

        CourseEntity courseSaved = courseRepository.save(course);
        log.info("Curso guardado: {}", courseSaved);
        return courseSaved;
    }

    // aca listo todos los cursos con el nombre.
    public PageResponse<ResponseCourse> findAllCourses(String text, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        org.springframework.data.domain.Page<ResponseCourse> response = courseRepository.filterCourses(text, pageable);
        return PageResponse.<ResponseCourse>builder()
                .content(response.getContent())
                .currentPage(response.getNumber())
                .totalPages(response.getTotalPages())
                .pageSize(response.getSize())
                .totalElements(response.getTotalElements())
                .isLast(response.isLast())
                .isFirst(response.isFirst())
                .build();
    }

    public List<ResponseCourse> findCoursesByUserId(long userId) {
        List<CourseEntity> courses = inscriptionRepository.findCoursesByUserId(userId);
        return courses.stream().map(this::fromEntityToResponse).toList();
    }

    public ResponseCourse findCourseByCode(long code) {
        CourseEntity course = courseRepository.findByCode(code)
                .orElseThrow(CourseNotFoundException::new);
        return ResponseCourse.builder()
                .code(course.getCode())
                .name(course.getName())
                .credits(course.getCredits())
                .build();
    }

    private ResponseCourse fromEntityToResponse(CourseEntity c) {
        return ResponseCourse.builder()
                .name(c.getName())
                .code(c.getCode())
                .credits(c.getCredits())
                .build();
    }

}
