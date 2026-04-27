package com.cursos.servicio_cursos.repositories;

import  com.cursos.servicio_cursos.entities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;   
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    
    

    CourseEntity findByCode(String id); // este método me permite buscar un curso por su ID.
    
    List<CourseEntity> findByName(String name); // este método me permite buscar cursos por su nombre, pero devuelve una lista de cursos que coincidan con ese nombre.
    //preguntarle a abrayan si este metodo es necesario o si con el findByName es suficiente, ya que el findByName devuelve un solo curso y el findByCourseEntities devuelve una
    //  lista de cursos, entonces depende de como se quiera manejar la busqueda de cursos por nombre.



}
