package com.cursos.servicio_cursos.dtos;

public class ResponseCourse {
    
    private Long  id;
    private String name;

    public ResponseCourse( Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
