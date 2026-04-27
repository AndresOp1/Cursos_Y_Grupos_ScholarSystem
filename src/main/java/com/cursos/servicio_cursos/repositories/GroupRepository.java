package com.cursos.servicio_cursos.repositories;

import com.cursos.servicio_cursos.entities.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    @Query("SELECT g FROM GroupEntity g WHERE g.teacher.id = :teacherId")
    List<GroupEntity> findByTeacherId(@Param("teacherId") Long teacherId); // este método me permite buscar grupos por
                                                                           // el ID del profesor.

    @Query("SELECT g FROM GroupEntity g WHERE g.course.code = :courseCode")
    List<GroupEntity> findGroupsByCourseCode(@Param("courseCode") Long courseCode);
}

// no se si este repositorio sea necesario