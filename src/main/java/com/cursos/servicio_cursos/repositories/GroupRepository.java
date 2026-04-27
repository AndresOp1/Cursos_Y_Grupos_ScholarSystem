package com.cursos.servicio_cursos.repositories;

import com.cursos.servicio_cursos.entities.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
    
    GroupEntity findByCode(String code); // este método me permite buscar un grupo por su código.

    List<GroupEntity> findByTeacherId(long id); // este método me permite buscar grupos por el ID del profesor.
}

//no se si este repositorio sea necesario 