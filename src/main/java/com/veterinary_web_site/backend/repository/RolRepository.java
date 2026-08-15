package com.veterinary_web_site.backend.repository;

import com.veterinary_web_site.backend.model.Rol;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Byte> {
    Optional<Rol> findByNombreRolIgnoreCase(String nombreRol);
}
