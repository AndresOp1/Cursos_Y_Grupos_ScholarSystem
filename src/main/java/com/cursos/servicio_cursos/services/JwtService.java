package com.cursos.servicio_cursos.services;

public interface JwtService {
  String getEmail(String tokenHash);
}
