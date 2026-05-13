package com.cursos.servicio_cursos.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cursos.servicio_cursos.dtos.UserResponse;
import com.cursos.servicio_cursos.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
  private final UserService userService;

  @GetMapping("/teachers")
  public ResponseEntity<List<UserResponse>> getMethodName() {
    log.info("buscando a los profesores");
    return ResponseEntity.ok(userService.fetchTeachers());
  }

}
