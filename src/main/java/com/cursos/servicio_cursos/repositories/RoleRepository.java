// Los repositorios son interfaces que me permitiran hablar con la base de datos sin nesecidad de escribir codigos SQL directamente. puedo usar metodos predefinidios como estos.
//roleRepository.save(role); - guardar un rol
//roleRepository.findById(1L); - buscar por ID
//roleRepository.findAll(); - traer todos los roles
//roleRepository.delete(role); - eliminar un rol
//roleRepository.count();  - contar cuántos roles hay

package com.cursos.servicio_cursos.repositories;

import com.cursos.servicio_cursos.entities.RoleEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // con esta etiqueta le decimos a Spring que esta clase es un repositorio y que
            // maneja la base de datos.
// extends Jpa....... con esta heredamos los metodos basicos que puse la
// principio.
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    // JpaRepository es una interfaz que me proporciona métodos predefinidos para
    // realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) en la base de
    // datos.
    // RoleEntity es la clase que representa la entidad de rol en la base de datos y
    // Long es el tipo de dato del ID de esa entidad.

    Optional<RoleEntity> findByName(String name); // este método me permite buscar un rol por su nombre.

}
