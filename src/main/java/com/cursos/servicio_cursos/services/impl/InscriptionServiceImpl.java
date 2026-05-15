package com.cursos.servicio_cursos.services.impl;

import com.cursos.servicio_cursos.entities.InscriptionEntity;
import com.cursos.servicio_cursos.entities.InscriptionId;
import com.cursos.servicio_cursos.exceptions.InvalidInscriptionException;
import com.cursos.servicio_cursos.repositories.InscriptionRepository;
import com.cursos.servicio_cursos.services.GroupService;
import com.cursos.servicio_cursos.services.InscriptionService;
import com.cursos.servicio_cursos.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InscriptionServiceImpl implements InscriptionService {
  private final UserService userService;
  private final GroupService groupService;
  private final InscriptionRepository inscriptionRepo;

  @Override
  @Transactional
  public void deleteByGroupId(Long groupId) {
    var inscriptions = inscriptionRepo.findAllByGroupId(groupId);
    inscriptionRepo.deleteAll(inscriptions);
  }

  @Override
  @Transactional
  public void saveAllByGroup(List<Long> studentsId, Long groupId) {
    var users = userService.findAllByIds(studentsId);
    var group = groupService.findById(groupId);

    var timestamp = LocalDateTime.now();
    List<InscriptionEntity> inscriptions = users.stream().map(u -> {
      if (!u.getRole().getName().equals("ESTUDIANTE")) {
        throw new InvalidInscriptionException(
                String.format("El rol del usuario %d no es de estudiante",
                        u.getId()));
      }
      var insId = new InscriptionId(u.getId(), group.getGroupId());
      return InscriptionEntity.builder().id(insId).inscriptionDate(timestamp)
              .user(u).group(group).build();
    }).toList();
    inscriptionRepo.saveAll(inscriptions);
  }
}
