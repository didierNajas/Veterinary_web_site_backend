package com.veterinary_web_site.backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.veterinary_web_site.backend.DTO.MascotaRequestDTO;
import com.veterinary_web_site.backend.DTO.MascotaResponseDTO;
import com.veterinary_web_site.backend.model.Mascota;
import com.veterinary_web_site.backend.service.MascotaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {
    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping
    public ResponseEntity<List<MascotaResponseDTO>> getAllMascotas() {
        List<Mascota> mascotas = mascotaService.getAllMascotas();
        List<MascotaResponseDTO> dtos = mascotas.stream()
                .map(MascotaResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> getMascotaById(@PathVariable Integer id) {
        Mascota mascota = mascotaService.getMascotaById(id);
        return ResponseEntity.ok(new MascotaResponseDTO(mascota));
    }

    @PostMapping
    public ResponseEntity<MascotaResponseDTO> createMascota(@Valid @RequestBody MascotaRequestDTO requestDTO) {
        Mascota createdMascota = mascotaService.createMascota(
                requestDTO.getIdPropietario(),
                requestDTO.getIdVeterinario(),
                requestDTO.getNombre(),
                requestDTO.getEspecie(),
                requestDTO.getRaza(),
                requestDTO.getFechaNacimiento(),
                requestDTO.getPeso(),
                requestDTO.getActivo(),
                requestDTO.getFechaRegistro(),
                requestDTO.getSexo()
        );
        return ResponseEntity.ok(new MascotaResponseDTO(createdMascota));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> updateMascota(@PathVariable Integer id, @Valid @RequestBody MascotaRequestDTO requestDTO) {
        Mascota updatedMascota = mascotaService.updateMascota(
                id,
                requestDTO.getIdPropietario(),
                requestDTO.getIdVeterinario(),
                requestDTO.getNombre(),
                requestDTO.getEspecie(),
                requestDTO.getRaza(),
                requestDTO.getFechaNacimiento(),
                requestDTO.getPeso(),
                requestDTO.getActivo(),
                requestDTO.getFechaRegistro(),
                requestDTO.getSexo()
        );
        return ResponseEntity.ok(new MascotaResponseDTO(updatedMascota));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMascota(@PathVariable Integer id) {
        mascotaService.deleteMascota(id);
        return ResponseEntity.noContent().build();
    }
}