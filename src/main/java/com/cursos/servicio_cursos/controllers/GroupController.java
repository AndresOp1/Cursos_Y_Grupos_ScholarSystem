//en este controllador voy a nesecitar 2 POST uno para crear un nuevo grupo y el otro para asignar estudiantes y tambien nesecito un PUT 

package com.cursos.servicio_cursos.controllers;

import com.cursos.servicio_cursos.services.GroupService;

import lombok.RequiredArgsConstructor;

import com.cursos.servicio_cursos.dtos.RequestGroup;
import com.cursos.servicio_cursos.dtos.RequestStudentsInscriptions;
import com.cursos.servicio_cursos.dtos.ResponseGroup;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<ResponseGroup> createGroup(@RequestBody RequestGroup requestGroup) {
        ResponseGroup newGroup = groupService.createGroup(requestGroup);
        return new ResponseEntity<>(newGroup, HttpStatus.CREATED);
    }

    @GetMapping("course/{courseCode}")
    public ResponseEntity<List<ResponseGroup>> findGoupsByCourseId(@PathVariable Long courseCode) {
        return ResponseEntity.ok(groupService.findGroupsByCourseCode(courseCode));
    }

    @PutMapping("{groupId}/teacher/{teacherId}")
    public ResponseEntity<ResponseGroup> assignTeacher(@PathVariable Long groupId, @PathVariable Long teacherId) {
        return ResponseEntity.ok(groupService.assignTeacher(teacherId, groupId));
    }

    @PostMapping("/inscriptions")
    public ResponseEntity<Void> inscribeStudents(@RequestBody RequestStudentsInscriptions requestBody) {
        groupService.assignStudents(requestBody);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
