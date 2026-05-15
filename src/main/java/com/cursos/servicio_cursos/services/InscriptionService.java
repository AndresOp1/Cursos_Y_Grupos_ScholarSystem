package com.cursos.servicio_cursos.services;

import java.util.List;

public interface InscriptionService {
  void deleteByGroupId(Long groupId);

  void saveAllByGroup(List<Long> studentsId, Long groupId);
}
