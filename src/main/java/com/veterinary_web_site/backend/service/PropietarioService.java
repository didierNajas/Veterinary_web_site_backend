package com.veterinary_web_site.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.veterinary_web_site.backend.model.Propietario;
import com.veterinary_web_site.backend.repository.PropietarioRepository;

@Service
public class PropietarioService {
    private final PropietarioRepository propietarioRepository;

    public PropietarioService(PropietarioRepository propietarioRepository) {
        this.propietarioRepository = propietarioRepository;
    }

    public List<Propietario> getAllPropietarios() {
        return propietarioRepository.findAll();
    }

    public Propietario getPropietarioById(Integer id) {
        return propietarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Propietario not found with id: " + id));
    }

    @Transactional
    public Propietario createPropietario(Propietario propietario) {
        return propietarioRepository.save(propietario);
    }

    @Transactional
    public Propietario updatePropietario(Integer id, Propietario propietarioDetails) {
        Propietario propietario = getPropietarioById(id);
        propietario.setActivo(propietarioDetails.getActivo());
        propietario.setApellido(propietarioDetails.getApellido());
        propietario.setDireccion(propietarioDetails.getDireccion());
        propietario.setFechaRegistro(propietarioDetails.getFechaRegistro());
        propietario.setNombre(propietarioDetails.getNombre());
        propietario.setTelefono(propietarioDetails.getTelefono());
        return propietarioRepository.save(propietario);
    }

    @Transactional
    public void deletePropietario(Integer id) {
        Propietario propietario = getPropietarioById(id);
        propietarioRepository.delete(propietario);
    }
}