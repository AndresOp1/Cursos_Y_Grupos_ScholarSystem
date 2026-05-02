package com.cursos.servicio_cursos.services;

import com.cursos.servicio_cursos.dtos.UserMessage;

public interface UserService {

  void upsertUser(UserMessage userMessage);

}
