package com.cursos.servicio_cursos.services;

import com.cursos.servicio_cursos.dtos.RequestGroup;
import com.cursos.servicio_cursos.entities.CourseEntity;
import com.cursos.servicio_cursos.entities.GroupEntity;
import com.cursos.servicio_cursos.entities.UserEntity;
import com.cursos.servicio_cursos.exceptions.CourseNotFoundException;
import com.cursos.servicio_cursos.exceptions.InvalidDayOfWeekException;
import com.cursos.servicio_cursos.exceptions.UserNotFoundException;
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

    private final ScheduleRepository scheduleRepository;
    private GroupRepository groupRepository;
    private UserRepository userRepository;
    private CourseRepository courseRepo;
    private InscriptionRepository inscriptionRepository;
    private ScheduleMapper scheduleMapper;

    // CREATE: Crear un nuevo grupo
    public GroupEntity createGroup(RequestGroup requestGroup) {

        CourseEntity course = courseRepo.findByCode(requestGroup.getCourseId())
                .orElseThrow(CourseNotFoundException::new);

        UserEntity teacher = userRepository.findById(requestGroup.getTeacherId())
                .orElseThrow(UserNotFoundException::new);

        List<ScheduleEntity> schedules = null;
        try {
            schedules = requestGroup.getSchedules().stream().map(scheduleMapper::toEntity)
                    .toList();
        } catch (IllegalArgumentException e) {
            throw new InvalidDayOfWeekException();
        }

        if (schedules != null) {
            scheduleRepository.saveAll(schedules);
        }

        GroupEntity group = GroupEntity.builder()
                .name(requestGroup.getName())
                .course(course)
                .teacher(teacher)
                .schedules(schedules == null ? List.of() : schedules)
                .build();

        return groupRepository.save(group);
    }

    // UPDATE: Asignar profesor a un grupo
    public GroupEntity assignTeacher(Long idTeacher, Long groupId) {
        // Buscar el grupo
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado con id: " + groupId));

        // Buscar el profesor (usuario)
        UserEntity teacher = userRepository.findById(idTeacher)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + idTeacher));

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
            boolean alreadyInscribed = inscriptionRepository.existsByUserIdAndGroupId(studentId, groupId);
            if (!alreadyInscribed) {
                InscriptionEntity inscription = InscriptionEntity.builder()
                        .group(group)
                        .user(student)
                        .build();
                inscriptionRepository.save(inscription);
            }
        }
    }
}
