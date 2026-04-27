//en este controllador voy a nesecitar 2 POST uno para crear un nuevo grupo y el otro para asignar estudiantes y tambien nesecito un PUT 

package com.cursos.servicio_cursos.controllers;

import com.cursos.servicio_cursos.services.GroupService;

import lombok.RequiredArgsConstructor;

import com.cursos.servicio_cursos.entities.GroupEntity;
import com.cursos.servicio_cursos.dtos.RequestGroup;
import com.cursos.servicio_cursos.dtos.ResponseGroup;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController // Anotación para indicar que esta clase es un controlador REST para manejar
                // solicitudes HTTP
@RequestMapping("/api/groups") // Anotación para definir la ruta base para todas las solicitudes relacionadas
                               // con grupos
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    // POST para crear un nuevo grupo
    @PostMapping
    public ResponseEntity<ResponseGroup> createGroup(@RequestBody RequestGroup requestGroup) { // toma la peticion http
                                                                                               // y lo convierte en un
                                                                                               // objeto de tipo
                                                                                               // RequestGroup, que es
        // un DTO que contiene los datos necesarios para crear un grupo
        ResponseGroup newGroup = groupService.createGroup(requestGroup);
        return new ResponseEntity<>(newGroup, HttpStatus.CREATED); // Devuelve el grupo creado con el estado HTTP 201
                                                                   // (CREATED)
    }

    @GetMapping("/{courseCode}")
    public ResponseEntity<List<ResponseGroup>> findGoupsByCourseId(@PathVariable Long courseCode) {
        return ResponseEntity.ok(groupService.findGroupsByCourseCode(courseCode));
    }

    @PutMapping("{groupId}/teacher/{teacherId}") // Anotación para manejar solicitudes HTTP PUT para asignar un profesor
                                                 // a un grupo, se recibe el ID del
    // grupo y el ID del profesor en la URL|
    public ResponseEntity<GroupEntity> assignTeacher(@PathVariable Long groupId, @PathVariable Long teacherId) { // toma
                                                                                                                 // los
                                                                                                                 // ID
                                                                                                                 // del
                                                                                                                 // grupo
                                                                                                                 // y
                                                                                                                 // del
                                                                                                                 // profesor
                                                                                                                 // de
                                                                                                                 // la
                                                                                                                 // URL
        GroupEntity updatedGroup = groupService.assignTeacher(groupId, teacherId);
        return ResponseEntity.ok(updatedGroup); // Devuelve el grupo actualizado con el estado HTTP 200 (OK)
    }
}
