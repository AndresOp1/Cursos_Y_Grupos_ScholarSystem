package com.cursos.servicio_cursos.services;

import com.cursos.servicio_cursos.dtos.*;
import com.cursos.servicio_cursos.entities.CourseEntity;
import com.cursos.servicio_cursos.entities.GroupEntity;
import com.cursos.servicio_cursos.entities.UserEntity;
import com.cursos.servicio_cursos.exceptions.*;
import com.cursos.servicio_cursos.mappers.GroupMapper;
import com.cursos.servicio_cursos.mappers.ScheduleMapper;
import com.cursos.servicio_cursos.mappers.UserMapper;
import com.cursos.servicio_cursos.repositories.CourseRepository;
import com.cursos.servicio_cursos.repositories.GroupRepository;
import com.cursos.servicio_cursos.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService {

  private final GroupMapper groupMapper;
  private final ScheduleService scheduleService;
  private final GroupRepository groupRepository;
  private final UserRepository userRepository;
  private final CourseRepository courseRepo;
  private final InscriptionService inscriptionService;
  private final ScheduleMapper scheduleMapper;
  private final UserMapper userMapper;

  @Transactional
  public GroupEntity findById(Long id) {
    return groupRepository.findById(id)
            .orElseThrow(() -> new GroupNotFoundException(id));
  }

  // CREATE: Crear un nuevo grupo
  @Transactional
  public ResponseGroup createGroup(RequestGroup requestGroup) {

    if (requestGroup.getCapacity() < 10) {
      throw new IllegalArgumentException("La capacidad minima de un grupo es de 10 estudiantes");
    }

    CourseEntity course = courseRepo.findByCode(requestGroup.getCourseId())
            .orElseThrow(CourseNotFoundException::new);

    UserEntity teacher = null;
    if (requestGroup.getTeacherId() != null) {
      teacher = userRepository.findById(requestGroup.getTeacherId())
              .orElseThrow(UserNotFoundException::new);
    }

    GroupEntity group = GroupEntity.builder()
            .name(requestGroup.getName())
            .course(course)
            .capacity(requestGroup.getCapacity())
            .build();
    if (teacher != null) {
      group.setTeacher(teacher);
    }
    log.info("apunto de guardar grupo {}", group);
    GroupEntity savedGroup = groupRepository.save(group);

    if (requestGroup.getSchedules() != null)
      scheduleService.saveAll(requestGroup.getSchedules(),
              savedGroup.getGroupId());

    return extracted(savedGroup);

  }

  @Transactional
  public void updateGroup(Long groupId, UpdateGroupRequest req) {
    GroupEntity group = groupRepository.findById(groupId).orElseThrow(GroupNotFoundException::new);
    log.info("update request for course id {}, request {}", groupId, req);

    GroupEntity updatedGroup = GroupEntity.builder()
            .groupId(group.getGroupId())
            .course(group.getCourse())
            .build();

    UserEntity teacher = req.teacherId() == null ? updatedGroup.getTeacher()
            : userRepository.findById(req.teacherId())
              .orElseThrow(UserNotFoundException::new);

    log.info("Se esta guardando el profesor {}", teacher);
    if (teacher != null && !teacher.getRole().getName().equals("PROFESOR"))
      throw new InvalidTeacherException();

    updatedGroup.setTeacher(teacher);
    updatedGroup.setName(req.groupName() == null ? updatedGroup.getName() : req.groupName());

    if (req.capacity() < 10) {
      throw new InvalidGroupCapacityException();
    }
    updatedGroup.setCapacity(req.capacity());

    log.info("guardando entidad grupo: {}", updatedGroup);
    groupRepository.save(updatedGroup);

    scheduleService.deleteAllByGroupId(groupId);
    inscriptionService.deleteByGroupId(groupId);

    scheduleService.saveAll(req.schedules(), groupId);
    inscriptionService.saveAllByGroup(req.studentsIds(), groupId);
  }

  private ResponseGroup extracted(GroupEntity savedGroup) {
    return ResponseGroup.builder()
            .schedules(savedGroup.getSchedules() == null ? null
                    : savedGroup.getSchedules().stream().map(scheduleMapper::toDto)
                      .toList())
            .groupName(savedGroup.getName())
            .teacher(userMapper.fromEntityToResponse(savedGroup.getTeacher()))
            .course(ResponseCourse.builder()
                    .code(savedGroup.getCourse().getCode())
                    .name(savedGroup.getCourse().getName())
                    .credits(savedGroup.getCourse().getCredits()).build())
            .build();
  }

  public GroupDetails getGroupDetails(Long groupId) {
    GroupEntity group = groupRepository.findById(groupId).orElseThrow(GroupNotFoundException::new);
    return GroupDetails.builder()
            .id(group.getGroupId())
            .groupName(group.getName())
            .capacity(group.getCapacity())
            .teacher(group.getTeacher() == null ? null
                    : userMapper.fromEntityToResponse(group.getTeacher()))
            .schedules(group.getSchedules().stream().map(scheduleMapper::toDto).toList())
            .course(ResponseCourse.builder()
                    .code(group.getCourse().getCode())
                    .name(group.getCourse().getName())
                    .credits(group.getCourse().getCredits()).build())
            .students(group.getInscriptions().stream()
                    .map(i -> userMapper.fromEntityToResponse(i.getUser()))
                    .toList())
            .build();
  }

  public List<ResponseGroup> findGroupsByCourseCode(Long courseCode) {
    return groupRepository.findGroupsByCourseCode(courseCode).stream()
            .map(groupMapper::fromEntityToResponse)
            .toList();
  }

  public PageResponse<ResponseGroup> findGroupsByUser(Long userId,
                                                      int page, int size) {

    if (userId == null) {
      throw new IllegalArgumentException("El id del usuario no puede ser nulo");
    }

    UserEntity user = userRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);

    if (!user.getEmail().equals(getAuthenticatedEmail())) {
      throw new UnauthorizedOperationException();
    }

    Pageable pageable = PageRequest.of(page, size);
    Page<GroupEntity> res;
    if (isTeacher(user)) {
      res = groupRepository.findByTeacher(user, pageable);
    } else {
      res = groupRepository.findByStudent(user, pageable);
    }
    return mapsToPageResponse(res);
  }

  private PageResponse<ResponseGroup> mapsToPageResponse(Page<GroupEntity> page) {
    return PageResponse.<ResponseGroup>builder()
            .pageSize(page.getSize())
            .isFirst(page.isFirst())
            .isLast(page.isLast())
            .totalPages(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .currentPage(page.getNumber())
            .content(page.getContent().stream().map(groupMapper::fromEntityToResponse).toList())
            .build();
  }

  private boolean isTeacher(UserEntity user) {
    return user.getRole().getName().equals("PROFESOR");
  }

  private String getAuthenticatedEmail() {
    Authentication auth = SecurityContextHolder.getContext()
            .getAuthentication();
    return auth.getName();
  }

}
