package com.cursos.servicio_cursos.repositories;

import com.cursos.servicio_cursos.entities.RoleEntity;
import com.cursos.servicio_cursos.entities.UserEntity;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email); // este método me permite buscar un usuario por su correo
                                                    // electrónico.

    List<UserEntity> findByRole_Name(String roleName); // este método me permite buscar usuarios por el nombre de su
                                                       // rol.

    List<UserEntity> findByFullName(String fullName); // este método me permite buscar usuarios por su nombre completo.

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.fullName = :fullName, u.email = :email, u.role = :role WHERE id = :userId")
    int updateUser(@Param("userId") Long userId,
            @Param("fullName") String fullname,
            @Param("email") String email,
            @Param("role") RoleEntity role);
}
