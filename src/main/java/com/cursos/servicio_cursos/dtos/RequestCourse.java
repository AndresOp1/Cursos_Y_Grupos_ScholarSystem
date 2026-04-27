// los dtos (objeto de transferencia de datos) como su nombre lo dice es una clase con la que puedo transportar datos entre diferentes partes de mi aplicacion, por ejemplo
//  entre el controlador y el servicio, o entre el servicio y el repositorio, etc.
// por ejemplo la entidad de curso tiene id, code, name, credits, groups, entonces si el cliente pide listar los cursos quizas solo quiera id y name, no toda la lista d elos 
// grupos para eso me sirven los dtos
// para eos nesecito los Request que es lo que el cliente me va a enviar para crear o actualizar un curso, y el Response que es lo que yo le voy a devolver al cliente 
// cuando me pida listar los cursos, por ejemplo
package com.cursos.servicio_cursos.dtos;

import lombok.Builder;

@Builder
public record RequestCourse(
        long code,
        String name,
        int credits) {
}
