package com.cursos.servicio_cursos.services;

import com.cursos.servicio_cursos.dtos.RequestGroup;
import com.cursos.servicio_cursos.entities.GroupEntity;
import com.cursos.servicio_cursos.entities.UserEntity;
import com.cursos.servicio_cursos.entities.InscriptionEntity;
import com.cursos.servicio_cursos.repositories.GroupRepository;
import com.cursos.servicio_cursos.repositories.UserRepository;
import com.cursos.servicio_cursos.repositories.InscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GroupService {
    
    @Autowired
    private GroupRepository groupRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private InscriptionRepository inscriptionRepository;
    
    // CREATE: Crear un nuevo grupo
    public GroupEntity createGroup(RequestGroup requestGroup) {
        GroupEntity group = new GroupEntity();
        group.setCode(requestGroup.getCode());
        group.setName(requestGroup.getName());
        group.setCredits(requestGroup.getCredits());
        
        
        
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
                InscriptionEntity inscription = new InscriptionEntity(student, group);
                inscriptionRepository.save(inscription);
            }
        }
    }
}
