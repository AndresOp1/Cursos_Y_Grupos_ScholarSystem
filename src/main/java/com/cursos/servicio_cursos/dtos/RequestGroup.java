package com.cursos.servicio_cursos.dtos;


public class RequestGroup {
    private String code;
    private String name;
    private int credits;
    private Long courseId;
    
    public RequestGroup() {}
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
    
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
}