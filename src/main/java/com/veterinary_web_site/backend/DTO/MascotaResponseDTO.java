package com.veterinary_web_site.backend.DTO;

import java.time.LocalDateTime;

import com.veterinary_web_site.backend.model.Mascota;

public class MascotaResponseDTO {

    private Integer idMascota;
    private Integer idPropietario;
    private Integer idVeterinario;
    private String nombre;
    private String especie;
    private String raza;
    private LocalDateTime fechaNacimiento;
    private Double peso;
    private Boolean activo;
    private LocalDateTime fechaRegistro;
    private String sexo;

    public MascotaResponseDTO(Mascota mascota) {
        this.idMascota = mascota.getIdMascota();
        this.idPropietario = mascota.getPropietario() != null ? mascota.getPropietario().getIdPropietario() : null;
        this.idVeterinario = mascota.getVeterinario() != null ? mascota.getVeterinario().getId() : null;
        this.nombre = mascota.getNombre();
        this.especie = mascota.getEspecie();
        this.raza = mascota.getRaza();
        this.fechaNacimiento = mascota.getFechaNacimiento();
        this.peso = mascota.getPeso();
        this.activo = mascota.getActivo();
        this.fechaRegistro = mascota.getFechaRegistro();
        this.sexo = mascota.getSexo();
    }

    // Getters and Setters
    public Integer getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(Integer idMascota) {
        this.idMascota = idMascota;
    }

    public Integer getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(Integer idPropietario) {
        this.idPropietario = idPropietario;
    }

    public Integer getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(Integer idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public LocalDateTime getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDateTime fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}