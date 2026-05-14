package com.cursos.servicio_cursos.mappers;

import org.springframework.stereotype.Component;

import com.cursos.servicio_cursos.dtos.UserResponse;
import com.cursos.servicio_cursos.entities.UserEntity;

@Component
public class UserMapper {

  public UserResponse fromEntityToResponse(UserEntity entity) {
    return UserResponse.builder()
        .fullName(entity.getFullName())
        .email(entity.getEmail())
        .id(entity.getId())
        .role(entity.getRole().getName())
        .build();
  }
}
