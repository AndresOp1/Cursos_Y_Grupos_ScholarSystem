package com.cursos.servicio_cursos.repositories;

import com.cursos.servicio_cursos.entities.InscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InscriptionRepository extends JpaRepository<InscriptionEntity, Long> {
    
    List<InscriptionEntity> findByUserId(long userId); // este método me permite buscar inscripciones por el ID del usuario al que pertenecen.

    List<InscriptionEntity> findByGroupId(long groupId); // este método me permite buscar inscripciones por el ID del grupo al que pertenecen.

    boolean existsByUserIdAndGroupId(long userId, long groupId); // este método me permite verificar si existe una inscripción para un usuario y un grupo
}


