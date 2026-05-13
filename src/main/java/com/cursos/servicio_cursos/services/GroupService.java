package com.cursos.servicio_cursos.services;

import com.cursos.servicio_cursos.dtos.RequestGroup;
import com.cursos.servicio_cursos.dtos.RequestStudentsInscriptions;
import com.cursos.servicio_cursos.dtos.ResponseCourse;
import com.cursos.servicio_cursos.dtos.ResponseGroup;
import com.cursos.servicio_cursos.entities.CourseEntity;
import com.cursos.servicio_cursos.entities.GroupEntity;
import com.cursos.servicio_cursos.entities.UserEntity;
import com.cursos.servicio_cursos.exceptions.AsingTeacherException;
import com.cursos.servicio_cursos.exceptions.CourseNotFoundException;
import com.cursos.servicio_cursos.exceptions.GroupNotFoundException;
import com.cursos.servicio_cursos.exceptions.InvalidDayOfWeekException;
import com.cursos.servicio_cursos.exceptions.InvalidTeacherException;
import com.cursos.servicio_cursos.exceptions.UserNotFoundException;
import com.cursos.servicio_cursos.mappers.GroupMapper;
import com.cursos.servicio_cursos.mappers.ScheduleMapper;
import com.cursos.servicio_cursos.entities.InscriptionEntity;
import com.cursos.servicio_cursos.entities.InscriptionId;
import com.cursos.servicio_cursos.repositories.CourseRepository;
import com.cursos.servicio_cursos.repositories.GroupRepository;
import com.cursos.servicio_cursos.repositories.ScheduleRepository;
import com.cursos.servicio_cursos.repositories.UserRepository;

import jakarta.transaction.Transactional;

import com.cursos.servicio_cursos.entities.ScheduleEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.cursos.servicio_cursos.repositories.InscriptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {

    private final GroupMapper groupMapper;
    private final ScheduleRepository scheduleRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepo;
    private final InscriptionRepository inscriptionRepository;
    private final ScheduleMapper scheduleMapper;

    // CREATE: Crear un nuevo grupo
    public ResponseGroup createGroup(RequestGroup requestGroup) {

        CourseEntity course = courseRepo.findByCode(requestGroup.getCourseId())
                .orElseThrow(CourseNotFoundException::new);

        UserEntity teacher = null;
        if (requestGroup.getTeacherId() != null) {
            teacher = userRepository.findById(requestGroup.getTeacherId())
                    .orElseThrow(UserNotFoundException::new);
        }

        List<ScheduleEntity> schedules = null;
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
                .schedules(schedules == null ? List.of() : schedules)
                .build();
        if (teacher != null) {
            group.setTeacher(teacher);
        }
        GroupEntity savedGroup = groupRepository.save(group);

        if (schedules != null) {
            schedules.stream().forEach(s -> s.setGroup(savedGroup));
            log.info("schedules entities: {}", schedules);
            scheduleRepository.saveAll(schedules);
        }

        return extracted(savedGroup);

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
                .collect(Collectors.toList());

        for (UserEntity student : students) {
            InscriptionId inscriptionId = new InscriptionId(student.getId(), group.getGroupId());
            InscriptionEntity newInscription = InscriptionEntity.builder()
                    .id(inscriptionId)
                    .group(group)
                    .user(student)
                    .inscriptionDate(LocalDateTime.now())
                    .build();
            inscriptionRepository.save(newInscription);
        }
    }

    public List<ResponseGroup> findGroupsByCourseCode(Long courseCode) {
        return groupRepository.findGroupsByCourseCode(courseCode).stream().map(groupMapper::fromEntityToResopnse)
                .toList();
    }
}
