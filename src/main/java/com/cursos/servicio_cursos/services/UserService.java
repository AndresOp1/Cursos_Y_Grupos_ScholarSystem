package com.cursos.servicio_cursos.services;

import com.cursos.servicio_cursos.dtos.UserMessage;
import com.cursos.servicio_cursos.dtos.UserResponse;
import com.cursos.servicio_cursos.entities.UserEntity;

import java.util.List;

public interface UserService {

  void upsertUser(UserMessage userMessage);

  List<UserResponse> fetchTeachers();

  List<UserEntity> findAllByIds(List<Long> ids);

}
