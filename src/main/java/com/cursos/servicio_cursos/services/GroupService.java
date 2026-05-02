package com.cursos.servicio_cursos.services;

import com.cursos.servicio_cursos.dtos.RequestGroup;
import com.cursos.servicio_cursos.dtos.ResponseCourse;
import com.cursos.servicio_cursos.dtos.ResponseGroup;
import com.cursos.servicio_cursos.entities.CourseEntity;
import com.cursos.servicio_cursos.entities.GroupEntity;
import com.cursos.servicio_cursos.entities.UserEntity;
import com.cursos.servicio_cursos.exceptions.CourseNotFoundException;
import com.cursos.servicio_cursos.exceptions.InvalidDayOfWeekException;
import com.cursos.servicio_cursos.exceptions.InvalidTeacherException;
import com.cursos.servicio_cursos.exceptions.UserNotFoundException;
import com.cursos.servicio_cursos.mappers.GroupMapper;
import com.cursos.servicio_cursos.mappers.ScheduleMapper;
import com.cursos.servicio_cursos.entities.InscriptionEntity;
import com.cursos.servicio_cursos.repositories.CourseRepository;
import com.cursos.servicio_cursos.repositories.GroupRepository;
import com.cursos.servicio_cursos.repositories.ScheduleRepository;
import com.cursos.servicio_cursos.repositories.UserRepository;
import com.cursos.servicio_cursos.entities.ScheduleEntity;

import lombok.RequiredArgsConstructor;

import com.cursos.servicio_cursos.repositories.InscriptionRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
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
            scheduleRepository.saveAll(schedules);
        }

        return extracted(savedGroup);

    }

    private ResponseGroup extracted(GroupEntity savedGroup) {
        return ResponseGroup.builder()
                .schedules(savedGroup.getSchedules().stream().map(scheduleMapper::toDto).toList())
                .groupName(savedGroup.getName())
                .teacherId(savedGroup.getTeacher() == null ? null : savedGroup.getTeacher().getId())
                .course(ResponseCourse.builder()
                        .code(savedGroup.getCourse().getCode())
                        .name(savedGroup.getCourse().getName())
                        .credits(savedGroup.getCourse().getCredits()).build())
                .build();
    }

    // UPDATE: Asignar profesor a un grupo
    public GroupEntity assignTeacher(Long idTeacher, Long groupId) {
        // Buscar el grupo
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado con id: " + groupId));

        // Buscar el profesor (usuario)
        UserEntity teacher = userRepository.findById(idTeacher)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + idTeacher));

        if (!"PROFESOR".equals(teacher.getRole().getName())) {
            throw new InvalidTeacherException();
        }

        // Asignar el profesor al grupo
        group.setTeacher(teacher);

        return groupRepository.save(group);
    }

    // CREATE: Asignar estudiantes a un grupo (crear inscripciones)
    public void assignStudents(List<Long> studentsId, @NonNull Long groupId) {
        // Buscar el grupo
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado con id: " + groupId));

        // Para cada estudiante, crear una inscripción
        for (Long studentId : studentsId) {
            UserEntity student = userRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con id: " + studentId));

            // Verificar si ya está inscrito
            boolean alreadyInscribed = inscriptionRepository.findByUserIdAndGroupId(studentId, groupId).isPresent();

            if (!alreadyInscribed) {
                InscriptionEntity inscription = InscriptionEntity.builder()
                        .group(group)
                        .user(student)
                        .build();
                inscriptionRepository.save(inscription);
            }
        }
    }

    public List<ResponseGroup> findGroupsByCourseCode(Long courseCode) {
        return groupRepository.findGroupsByCourseCode(courseCode).stream().map(groupMapper::fromEntityToResopnse)
                .toList();
    }
}
