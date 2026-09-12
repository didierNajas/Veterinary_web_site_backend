package com.veterinary_web_site.backend.DTO;

import java.time.LocalDateTime;

import com.veterinary_web_site.backend.model.Propietario;

public class PropietarioResponseDTO {
    private Integer idPropietario;
    private Boolean activo;
    private String apellido;
    private String direccion;
    private LocalDateTime fechaRegistro;
    private String nombre;
    private String telefono;

    // Constructors
    public PropietarioResponseDTO() {
    }

    public PropietarioResponseDTO(Integer idPropietario, Boolean activo, String apellido, String direccion, LocalDateTime fechaRegistro, String nombre, String telefono) {
        this.idPropietario = idPropietario;
        this.activo = activo;
        this.apellido = apellido;
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    // Constructor from entity
    public PropietarioResponseDTO(Propietario propietario) {
        this.idPropietario = propietario.getIdPropietario();
        this.activo = propietario.getActivo();
        this.apellido = propietario.getApellido();
        this.direccion = propietario.getDireccion();
        this.fechaRegistro = propietario.getFechaRegistro();
        this.nombre = propietario.getNombre();
        this.telefono = propietario.getTelefono();
    }

    // Getters and Setters
    public Integer getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(Integer idPropietario) {
        this.idPropietario = idPropietario;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}