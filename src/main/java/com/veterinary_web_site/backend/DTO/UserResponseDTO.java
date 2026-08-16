package com.veterinary_web_site.backend.DTO;

import com.veterinary_web_site.backend.model.Role;
import com.veterinary_web_site.backend.model.User;

public class UserResponseDTO {
    private Integer id;
    private String fullName;
    private String email;
    private Role role;
    private String telefono;
    private String direccion;
    private boolean activo;

    public UserResponseDTO() {
    }

    public UserResponseDTO(
            Integer id,
            String fullName,
            String email,
            Role role,
            String telefono,
            String direccion,
            boolean activo
    ) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.telefono = telefono;
        this.direccion = direccion;
        this.activo = activo;
    }

    public static UserResponseDTO fromUser(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getTelefono(),
                user.getDireccion(),
                user.isActivo()
        );
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
