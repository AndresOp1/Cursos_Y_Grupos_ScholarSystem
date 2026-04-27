// los servicio se encarga de la logica del negocio como tal donde voy a escribir las reglas del sistema, si hago una analogica con una biblioteca 
//digamos que el repositorio seria el bibliotecario que me trae un libro y el servicio  seria el que decide que libros trar y que hacer con ellos, tambien se encarga de hacer
//  las validaciones necesarias para que el sistema funcione correctamente, por ejemplo si quiero crear un nuevo rol en el sistema, el servicio se encargaria de validar que el 
// nombre del rol no este vacio o que no exista otro rol con el mismo nombre antes de guardarlo en la base de datos a traves del repositorio. jjaja mucho texto zorry brayan.

package com.cursos.servicio_cursos.services;

import com.cursos.servicio_cursos.dtos.RequestCourse;
import com.cursos.servicio_cursos.dtos.ResponseCourse;
import com.cursos.servicio_cursos.entities.CourseEntity;
import com.cursos.servicio_cursos.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired; // esto es para intectar automaticamente el repositorio, creando el objeto por mi.
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    // crear un nuevo curso
    public CourseEntity createCourse(RequestCourse requestCourse) {

        CourseEntity course = CourseEntity.builder()
                .code(requestCourse.code())
                .credits(requestCourse.credits())
                .name(requestCourse.name())
                .build();

        return courseRepository.save(course);
    }

    // aca listo todos los cursos con el nombre.
    public List<ResponseCourse> finACourseName() {
        List<CourseEntity> courses = courseRepository.findAll();

        // aca hago la conversion de la lista de cursos a una lista de responseCourse
        // para que solo me muestre el nombre del curso.
        return courses.stream().map(course -> new ResponseCourse(course.getCode(), course.getName()))
                .collect(Collectors.toList());
    }

}
