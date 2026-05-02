package com.cursos.servicio_cursos.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cursos.servicio_cursos.dtos.UserMessage;
import com.cursos.servicio_cursos.entities.RoleEntity;
import com.cursos.servicio_cursos.entities.UserEntity;
import com.cursos.servicio_cursos.exceptions.InvalidRoleException;
import com.cursos.servicio_cursos.repositories.RoleRepository;
import com.cursos.servicio_cursos.repositories.UserRepository;
import com.cursos.servicio_cursos.services.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
  private final UserRepository userRepo;
  private final RoleRepository roleRepo;

  @Override
  @Transactional
  public void upsertUser(UserMessage userMessage) {
    log.info("procesando usuario de id: {}", userMessage.id());
    RoleEntity role = roleRepo.findByName(userMessage.role())
        .orElseThrow(() -> new InvalidRoleException(userMessage.role()));

    Optional<UserEntity> userEntity = userRepo.findById(userMessage.id());
    if (userEntity.isPresent()) {
      UserEntity user = userEntity.get();
      userRepo.updateUser(user.getId(), userMessage.fullName(), userMessage.email(), role);
      return;
    }
    UserEntity user = UserEntity.builder()
        .id(userMessage.id())
        .email(userMessage.email())
        .fullName(userMessage.fullName())
        .role(role)
        .build();
    userRepo.save(user);

  }
}
