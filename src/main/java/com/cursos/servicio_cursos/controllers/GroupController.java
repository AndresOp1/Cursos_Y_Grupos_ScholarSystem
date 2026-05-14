//en este controllador voy a nesecitar 2 POST uno para crear un nuevo grupo y el otro para asignar estudiantes y tambien nesecito un PUT 

package com.cursos.servicio_cursos.controllers;

import com.cursos.servicio_cursos.dtos.GroupDetails;
import com.cursos.servicio_cursos.dtos.RequestGroup;
import com.cursos.servicio_cursos.dtos.ResponseGroup;
import com.cursos.servicio_cursos.dtos.UpdateGroupRequest;
import com.cursos.servicio_cursos.services.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
@Slf4j
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

  @GetMapping("/{groupId}")
  public ResponseEntity<GroupDetails> getGroupDetails(@PathVariable Long groupId) {
    log.info("Peticion de detalles del grupo con id: {}", groupId);
    GroupDetails groupDetails = groupService.getGroupDetails(groupId);
    return ResponseEntity.ok(groupDetails);
  }

  @PutMapping("/{groupId}")
  public ResponseEntity<Void> updateGroup(@PathVariable Long groupId,
                                          @RequestBody UpdateGroupRequest requestUpdate) {
    log.info("update group request, group id {}, request {}", groupId, requestUpdate);
    groupService.updateGroup(groupId, requestUpdate);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("{groupId}/teacher/{teacherId}")
  public ResponseEntity<ResponseGroup> assignTeacher(@PathVariable Long groupId, @PathVariable Long teacherId) {
    return ResponseEntity.ok(groupService.assignTeacher(teacherId, groupId));
  }
}
