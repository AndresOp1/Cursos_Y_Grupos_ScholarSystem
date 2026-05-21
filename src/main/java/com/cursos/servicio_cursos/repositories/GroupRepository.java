package com.cursos.servicio_cursos.repositories;

import com.cursos.servicio_cursos.entities.GroupEntity;
import com.cursos.servicio_cursos.entities.UserEntity;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

  // el ID del profesor.

  @Query("SELECT g FROM GroupEntity g WHERE g.course.code = :courseCode")
  List<GroupEntity> findGroupsByCourseCode(@Param("courseCode") Long courseCode);

  @Modifying
  @Transactional
  @Query("UPDATE GroupEntity g SET g.teacher = :teacher WHERE g.groupId =:groupId")
  int asingTeacher(@Param("teacher") UserEntity teacher,
                   @Param("groupId") Long groupId);

  @Modifying
  @Transactional
  @Query("""
          UPDATE GroupEntity g
          SET g.name = :name, g.capacity = :capacity,
            g.teacher = :teacher
          WHERE g.groupId = :groupId
          """)
  void updateGroup(
          @Param("groupId") Long id,
          @Param("name") String name,
          @Param("teacher") UserEntity teacher,
          @Param("capacity") int capacity);

  Page<GroupEntity> findByTeacher(UserEntity teacher, Pageable pageable);

  @Query(value = """
          SELECT g FROM GroupEntity g JOIN g.inscriptions i
          WHERE i.user = :student
          """)
  Page<GroupEntity> findByStudent(@Param("student") UserEntity student,
                                  Pageable pageable);
}

// no se si este repositorio sea necesario