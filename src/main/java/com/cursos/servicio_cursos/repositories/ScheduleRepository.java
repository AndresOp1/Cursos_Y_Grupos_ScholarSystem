package com.cursos.servicio_cursos.repositories;

import com.cursos.servicio_cursos.entities.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    @Query("SELECT s FROM ScheduleEntity s WHERE s.group.groupId = :groupId")
    List<ScheduleEntity> findByGrouId(@Param("groupId") Long groupId); // este método me permite buscar horarios por el
                                                                       // ID del grupo al que pertenecen.

    // List<ScheduleEntity> findByCourseId(long courseId); // este método me permite
    // buscar horarios por el ID del curso al que pertenecen.
}
// la verdad no se como van bien lo de los horarios asi que solo cree estos 2
// metodos
// tengo un problema con el metodo findByCourseId ya que como un horario puede
// pertenecer a muchos cursos y un curso puede tener muchos horarios, entonces
// es una
// relacion ManyToMany, entonces no se como hacer la busqueda por el ID del
// curso, entonces lo deje comentado por ahora.