package com.cursos.servicio_cursos.services;

import java.util.List;

import com.cursos.servicio_cursos.dtos.UserMessage;
import com.cursos.servicio_cursos.dtos.UserResponse;

public interface UserService {

  void upsertUser(UserMessage userMessage);

  List<UserResponse> fetchTeachers();

}
