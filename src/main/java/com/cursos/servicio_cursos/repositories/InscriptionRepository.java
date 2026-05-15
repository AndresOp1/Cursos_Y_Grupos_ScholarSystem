package com.cursos.servicio_cursos.repositories;

import com.cursos.servicio_cursos.entities.CourseEntity;
import com.cursos.servicio_cursos.entities.InscriptionEntity;
import com.cursos.servicio_cursos.entities.InscriptionId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscriptionRepository extends JpaRepository<InscriptionEntity, InscriptionId> {


  @Query("SELECT DISTINCT i.group.course FROM InscriptionEntity i WHERE i.user.id = :userId")
  List<CourseEntity> findCoursesByUserId(@Param("userId") Long userId);

  List<InscriptionEntity> findAllByGroupId(Long groupId);
}
