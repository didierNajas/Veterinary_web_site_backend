package com.veterinary_web_site.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veterinary_web_site.backend.model.Mascota;

public interface MascotaRepository extends JpaRepository<Mascota, Integer> {
    // Additional custom queries can be added here if needed
}