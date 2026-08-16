package com.veterinary_web_site.backend.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String fullName;

    @Email(message = "El correo no tiene un formato valido")
    @NotBlank(message = "El correo es obligatorio")
    private String email;

    private String role;

    @Size(max = 20, message = "El telefono no puede superar 20 caracteres")
    private String telefono;

    @Size(max = 255, message = "La direccion no puede superar 255 caracteres")
    private String direccion;

    @Size(min = 6, message = "La contrasena debe tener minimo 6 caracteres")
    private String password;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
