package com.cursos.servicio_cursos.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.cursos.servicio_cursos.entities.UserEntity;
import com.cursos.servicio_cursos.exceptions.UserNotFoundException;
import com.cursos.servicio_cursos.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    UserEntity user = userRepository.findByEmail(username)
        .orElseThrow(UserNotFoundException::new);

    return User.builder()
        .username(user.getEmail())
        .roles(user.getRole().getName())
        .build();
  }

}
