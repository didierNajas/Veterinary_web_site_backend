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

import com.veterinary_web_site.backend.DTO.PropietarioRequestDTO;
import com.veterinary_web_site.backend.DTO.PropietarioResponseDTO;
import com.veterinary_web_site.backend.model.Propietario;
import com.veterinary_web_site.backend.service.PropietarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/propietarios")
public class PropietarioController {
    private final PropietarioService propietarioService;

    public PropietarioController(PropietarioService propietarioService) {
        this.propietarioService = propietarioService;
    }

    @GetMapping
    public ResponseEntity<List<PropietarioResponseDTO>> getAllPropietarios() {
        List<Propietario> propietarios = propietarioService.getAllPropietarios();
        List<PropietarioResponseDTO> dtos = propietarios.stream()
                .map(PropietarioResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropietarioResponseDTO> getPropietarioById(@PathVariable Integer id) {
        Propietario propietario = propietarioService.getPropietarioById(id);
        return ResponseEntity.ok(new PropietarioResponseDTO(propietario));
    }

    @PostMapping
    public ResponseEntity<PropietarioResponseDTO> createPropietario(@Valid @RequestBody PropietarioRequestDTO requestDTO) {
        Propietario propietario = new Propietario(
                null,
                requestDTO.getActivo(),
                requestDTO.getApellido(),
                requestDTO.getDireccion(),
                requestDTO.getFechaRegistro(),
                requestDTO.getNombre(),
                requestDTO.getTelefono()
        );
        Propietario savedPropietario = propietarioService.createPropietario(propietario);
        return ResponseEntity.ok(new PropietarioResponseDTO(savedPropietario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropietarioResponseDTO> updatePropietario(
            @PathVariable Integer id,
            @Valid @RequestBody PropietarioRequestDTO requestDTO) {
        Propietario propietarioDetails = new Propietario(
                id,
                requestDTO.getActivo(),
                requestDTO.getApellido(),
                requestDTO.getDireccion(),
                requestDTO.getFechaRegistro(),
                requestDTO.getNombre(),
                requestDTO.getTelefono()
        );
        Propietario updatedPropietario = propietarioService.updatePropietario(id, propietarioDetails);
        return ResponseEntity.ok(new PropietarioResponseDTO(updatedPropietario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePropietario(@PathVariable Integer id) {
        propietarioService.deletePropietario(id);
        return ResponseEntity.noContent().build();
    }
}