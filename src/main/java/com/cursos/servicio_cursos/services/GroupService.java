package com.cursos.servicio_cursos.services;

import com.cursos.servicio_cursos.dtos.*;
import com.cursos.servicio_cursos.entities.*;
import com.cursos.servicio_cursos.exceptions.*;
import com.cursos.servicio_cursos.mappers.GroupMapper;
import com.cursos.servicio_cursos.mappers.ScheduleMapper;
import com.cursos.servicio_cursos.mappers.UserMapper;
import com.cursos.servicio_cursos.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {

  private final GroupMapper groupMapper;
  private final ScheduleRepository scheduleRepo;
  private final GroupRepository groupRepository;
  private final UserRepository userRepository;
  private final CourseRepository courseRepo;
  private final InscriptionRepository inscriptionRepo;
  private final ScheduleMapper scheduleMapper;
  private final UserMapper userMapper;

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

    List<ScheduleEntity> schedules;
    log.info("reqwuest schedules: {}", requestGroup.getSchedules());
    try {
      schedules = requestGroup.getSchedules().stream().map(scheduleMapper::toEntity)
              .toList();
    } catch (IllegalArgumentException e) {
      throw new InvalidDayOfWeekException();
    }

    GroupEntity group = GroupEntity.builder()
            .name(requestGroup.getName())
            .course(course)
            .schedules(schedules)
            .capacity(requestGroup.getCapacity())
            .build();
    if (teacher != null) {
      group.setTeacher(teacher);
    }
    log.info("apunto de guardar grupo {}", group);
    GroupEntity savedGroup = groupRepository.save(group);

    schedules.forEach(s -> s.setGroup(savedGroup));
    log.info("schedules entities: {}", schedules);
    scheduleRepo.saveAll(schedules);


    return extracted(savedGroup);

  }

  @Transactional
  public void updateGroup(Long groupId, UpdateGroupRequest req) {
    GroupEntity group = groupRepository.findById(groupId).orElseThrow(GroupNotFoundException::new);
    log.info("update request for course id {}, request {}", groupId, req);
    UserEntity teacher = req.teacherId() == null ?
            group.getTeacher() :
            userRepository.findById(req.teacherId())
            .orElseThrow(UserNotFoundException::new);

    String name = req.groupName() == null ?
            group.getName() : req.groupName();

    if (!teacher.getRole().getName().equals("PROFESOR"))
      throw new InvalidTeacherException();

    if (req.capacity() < 10) {
      throw new InvalidGroupCapacityException();
    }

    // update schedules
    scheduleRepo.deleteAllByGroupId(groupId);
    List<ScheduleEntity> schedules = req.schedules().stream()
            .map(scheduleMapper::toEntity).toList();
    scheduleRepo.saveAll(schedules);

    // update inscriptions
    inscriptionRepo.deleteByGroupId(groupId);
    List<UserEntity> students = userRepository.findAllById(req.studentsIds());
    List<InscriptionEntity> inscriptions = students.stream().map(u -> {
      var inscriptionId = new InscriptionId(u.getId(), group.getGroupId());
      return InscriptionEntity.builder().user(u).group(group)
              .inscriptionDate(LocalDateTime.now())
              .id(inscriptionId).build();
    }).toList();
    inscriptionRepo.saveAll(inscriptions);
    groupRepository.updateGroup(groupId, name,
            teacher, req.capacity());
  }

  private ResponseGroup extracted(GroupEntity savedGroup) {
    return ResponseGroup.builder()
            .schedules(savedGroup.getSchedules().stream().map(scheduleMapper::toDto).toList())
            .groupName(savedGroup.getName())
            .teacher(savedGroup.getTeacher())
            .course(ResponseCourse.builder()
                    .code(savedGroup.getCourse().getCode())
                    .name(savedGroup.getCourse().getName())
                    .credits(savedGroup.getCourse().getCredits()).build())
            .build();
  }

  // UPDATE: Asignar profesor a un grupo
  @Transactional
  public ResponseGroup assignTeacher(Long idTeacher, Long groupId) {
    log.info("Modificando grupo {}...", groupId);
    // Buscar el grupo
    groupRepository.findById(groupId)
            .orElseThrow(() -> new GroupNotFoundException(groupId));

    // Buscar el profesor (usuario)
    UserEntity teacher = userRepository.findById(idTeacher)
            .orElseThrow(InvalidTeacherException::new);

    if (!"PROFESOR".equals(teacher.getRole().getName())) {
      throw new InvalidTeacherException();
    }
    int rowCounts = groupRepository.asingTeacher(teacher, groupId);
    log.info("filas afectadas: {}", rowCounts);
    if (rowCounts == 1) {
      GroupEntity groupEntity = groupRepository.findById(groupId).orElseThrow(GroupNotFoundException::new);
      return groupMapper.fromEntityToResopnse(groupEntity);
    }
    throw new AsingTeacherException();
  }

  // CREATE: Asignar estudiantes a un grupo (crear inscripciones)
  @Transactional
  public void assignStudents(RequestStudentsInscriptions studentsInscriptions) {
    GroupEntity group = groupRepository.findById(studentsInscriptions.groupId())
            .orElseThrow(GroupNotFoundException::new);

    List<UserEntity> students = studentsInscriptions.studentsEmails().stream()
            .map(email -> userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email)))
            .toList();

    for (UserEntity student : students) {
      InscriptionId inscriptionId = new InscriptionId(student.getId(), group.getGroupId());
      InscriptionEntity newInscription = InscriptionEntity.builder()
              .id(inscriptionId)
              .group(group)
              .user(student)
              .inscriptionDate(LocalDateTime.now())
              .build();
      inscriptionRepo.save(newInscription);
    }
    log.info("Se inscribieron los estudiantes: {} al grupo {}", studentsInscriptions.studentsEmails(), group);
  }

  public GroupDetails getGroupDetails(Long groupId) {
    GroupEntity group = groupRepository.findById(groupId).orElseThrow(GroupNotFoundException::new);
    return GroupDetails.builder()
            .id(group.getGroupId())
            .groupName(group.getName())
            .capacity(group.getCapacity())
            .teacher(userMapper.fromEntityToResponse(group.getTeacher()))
            .schedules(group.getSchedules().stream().map(scheduleMapper::toDto).toList())
            .course(ResponseCourse.builder()
                    .code(group.getCourse().getCode())
                    .name(group.getCourse().getName())
                    .credits(group.getCourse().getCredits()).build())
            .students(group.getInscriptions().stream().map(i -> userMapper.fromEntityToResponse(i.getUser()))
                    .toList())
            .build();
  }

  public List<ResponseGroup> findGroupsByCourseCode(Long courseCode) {
    return groupRepository.findGroupsByCourseCode(courseCode).stream().map(groupMapper::fromEntityToResopnse)
            .toList();
  }
}
