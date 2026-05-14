package com.cursos.servicio_cursos.repositories;

import com.cursos.servicio_cursos.entities.ScheduleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

  @Query("SELECT s FROM ScheduleEntity s WHERE s.group.groupId = :groupId")
  List<ScheduleEntity> findByGrouId(@Param("groupId") Long groupId); // este método me permite buscar horarios por el

  @Modifying
  @Transactional
  @Query("""
          DELETE FROM ScheduleEntity s
          WHERE s.group.groupId = :groupId
          """)
  void deleteAllByGroupId(@Param("groupId") Long groupId);
}
