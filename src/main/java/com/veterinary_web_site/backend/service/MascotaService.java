package com.veterinary_web_site.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.veterinary_web_site.backend.model.Mascota;
import com.veterinary_web_site.backend.model.Propietario;
import com.veterinary_web_site.backend.model.User;
import com.veterinary_web_site.backend.repository.MascotaRepository;
import com.veterinary_web_site.backend.repository.PropietarioRepository;
import com.veterinary_web_site.backend.repository.UserRepository;

@Service
public class MascotaService {
    private final MascotaRepository mascotaRepository;
    private final PropietarioRepository propietarioRepository;
    private final UserRepository userRepository;

    public MascotaService(MascotaRepository mascotaRepository, PropietarioRepository propietarioRepository, UserRepository userRepository) {
        this.mascotaRepository = mascotaRepository;
        this.propietarioRepository = propietarioRepository;
        this.userRepository = userRepository;
    }

    public List<Mascota> getAllMascotas() {
        return mascotaRepository.findAll();
    }

    public Mascota getMascotaById(Integer id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota not found with id: " + id));
    }

    @Transactional
    public Mascota createMascota(Integer idPropietario, Integer idVeterinario, String nombre, String especie, String raza, java.time.LocalDateTime fechaNacimiento, Double peso, Boolean activo, java.time.LocalDateTime fechaRegistro, String sexo) {
        Propietario propietario = idPropietario != null ? propietarioRepository.findById(idPropietario)
                .orElseThrow(() -> new RuntimeException("Propietario not found with id: " + idPropietario)) : null;
        User veterinario = idVeterinario != null ? userRepository.findById(idVeterinario)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + idVeterinario)) : null;
        
        Mascota mascota = new Mascota(null, propietario, veterinario, nombre, especie, raza, fechaNacimiento, peso, activo, fechaRegistro, sexo);
        return mascotaRepository.save(mascota);
    }

    @Transactional
    public Mascota updateMascota(Integer id, Integer idPropietario, Integer idVeterinario, String nombre, String especie, String raza, java.time.LocalDateTime fechaNacimiento, Double peso, Boolean activo, java.time.LocalDateTime fechaRegistro, String sexo) {
        Mascota mascota = getMascotaById(id);
        Propietario propietario = idPropietario != null ? propietarioRepository.findById(idPropietario)
                .orElseThrow(() -> new RuntimeException("Propietario not found with id: " + idPropietario)) : null;
        User veterinario = idVeterinario != null ? userRepository.findById(idVeterinario)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + idVeterinario)) : null;
        
        mascota.setActivo(activo);
        mascota.setEspecie(especie);
        mascota.setFechaNacimiento(fechaNacimiento);
        mascota.setFechaRegistro(fechaRegistro);
        mascota.setNombre(nombre);
        mascota.setPeso(peso);
        mascota.setRaza(raza);
        mascota.setSexo(sexo);
        mascota.setPropietario(propietario);
        mascota.setVeterinario(veterinario);
        return mascotaRepository.save(mascota);
    }

    @Transactional
    public void deleteMascota(Integer id) {
        Mascota mascota = getMascotaById(id);
        mascotaRepository.delete(mascota);
    }
}