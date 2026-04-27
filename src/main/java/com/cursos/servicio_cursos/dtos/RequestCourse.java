// los dtos (objeto de transferencia de datos) como su nombre lo dice es una clase con la que puedo transportar datos entre diferentes partes de mi aplicacion, por ejemplo
//  entre el controlador y el servicio, o entre el servicio y el repositorio, etc.
// por ejemplo la entidad de curso tiene id, code, name, credits, groups, entonces si el cliente pide listar los cursos quizas solo quiera id y name, no toda la lista d elos 
// grupos para eso me sirven los dtos
// para eos nesecito los Request que es lo que el cliente me va a enviar para crear o actualizar un curso, y el Response que es lo que yo le voy a devolver al cliente 
// cuando me pida listar los cursos, por ejemplo
package com.cursos.servicio_cursos.dtos;

public class RequestCourse {
    private String code;
    private String name;
    private int credits;

    public RequestCourse() {
    } // constructor vacio para que spring pueda crear el objeto a partir de la peticion.


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
    
}
