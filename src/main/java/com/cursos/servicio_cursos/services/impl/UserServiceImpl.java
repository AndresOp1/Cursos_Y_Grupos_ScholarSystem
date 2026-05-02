package com.cursos.servicio_cursos.services.impl;

import org.springframework.stereotype.Service;

import com.cursos.servicio_cursos.dtos.UserMessage;
import com.cursos.servicio_cursos.entities.RoleEntity;
import com.cursos.servicio_cursos.entities.UserEntity;
import com.cursos.servicio_cursos.exceptions.InvalidRoleException;
import com.cursos.servicio_cursos.repositories.RoleRepository;
import com.cursos.servicio_cursos.repositories.UserRepository;
import com.cursos.servicio_cursos.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepo;
  private final RoleRepository roleRepo;

  @Override
  public void upsertUser(UserMessage userMessage) {
    RoleEntity role = roleRepo.findByName(userMessage.role())
        .orElseThrow(() -> new InvalidRoleException(userMessage.role()));

    UserEntity userEntity = userRepo.findById(userMessage.id())
        .orElse(UserEntity.builder().id(userMessage.id()).build());

    userEntity.setEmail(userMessage.email());
    userEntity.setFullName(userMessage.fullName());
    userEntity.setRole(role);
    userRepo.save(userEntity);
  }
}
