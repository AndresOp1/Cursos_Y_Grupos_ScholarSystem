package com.cursos.servicio_cursos.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String fullName;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    // Relación: Muchos usuarios tienen UN rol
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;
    
    // Relación: Un usuario tiene muchas inscripciones (pendiente por crear )
    @OneToMany(mappedBy = "user")
    private List<InscriptionEntity> inscriptions;

    @ManyToMany
    private List<GroupEntity> groups; // Los grupos a los que pertenece el usuario, me toco crearlo por que una busqueda en UserRepository me lo pedia
    // para buscar usuarios por el codigo del grupo, entonces como un usuario puede pertenecer a muchos grupos y un grupo puede tener muchos usuarios, entonces es una relacion ManyToMany
    
    // Constructor vacío
    public UserEntity() {}
    
    // Constructor con campos básicos
    public UserEntity(String fullName, String email, RoleEntity role) {
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public RoleEntity getRole() {
        return role;
    }
    
    public void setRole(RoleEntity role) {
        this.role = role;
    }
    
    public List<InscriptionEntity> getInscriptions() {
        return inscriptions;
    }
    
    public void setInscriptions(List<InscriptionEntity> inscriptions) {
        this.inscriptions = inscriptions;
    }
}