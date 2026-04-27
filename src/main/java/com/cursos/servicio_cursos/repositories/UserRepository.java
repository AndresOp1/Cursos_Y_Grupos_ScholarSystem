package com.cursos.servicio_cursos.repositories;

import com.cursos.servicio_cursos.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email); // este método me permite buscar un usuario por su correo electrónico.

    List<UserEntity> findByRole_Name(String roleName); // este método me permite buscar usuarios por el nombre de su
                                                       // rol.

    List<UserEntity> findByFullName(String fullName); // este método me permite buscar usuarios por su nombre completo.
}
// tengo que hablar con brayan sobre estas busquedas ya que en el servicio de
// usurios deben estar.