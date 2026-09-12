package com.veterinary_web_site.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veterinary_web_site.backend.model.Propietario;

public interface PropietarioRepository extends JpaRepository<Propietario, Integer> {
    // Additional custom queries can be added here if needed
}