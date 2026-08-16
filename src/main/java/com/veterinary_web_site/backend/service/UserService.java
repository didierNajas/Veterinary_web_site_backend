package com.veterinary_web_site.backend.service;

import com.veterinary_web_site.backend.DTO.UserRequestDTO;
import com.veterinary_web_site.backend.DTO.UserResponseDTO;
import com.veterinary_web_site.backend.model.Rol;
import com.veterinary_web_site.backend.model.Role;
import com.veterinary_web_site.backend.model.User;
import com.veterinary_web_site.backend.repository.RolRepository;
import com.veterinary_web_site.backend.repository.UserRepository;
import java.util.List;
import java.util.Locale;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            RolRepository rolRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> listAll(User currentUser) {
        ensureAdmin(currentUser);
        return userRepository.findAll().stream()
                .map(UserResponseDTO::fromUser)
                .toList();
    }

    @Transactional
    public UserResponseDTO update(Integer id, UserRequestDTO request, User currentUser) {
        User user = findUserOrThrow(id);
        ensureCanManage(user, currentUser);

        if (!user.isActivo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede actualizar un usuario inactivo");
        }

        String email = normalizeEmail(request.getEmail());
        if (userRepository.existsByEmailAndIdNot(email, id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe un usuario con ese correo");
        }

        String[] names = User.splitFullName(request.getFullName());
        user.setNombre(names[0]);
        user.setApellido(names[1].isBlank() ? names[0] : names[1]);
        user.setEmail(email);
        user.setTelefono(normalizeOptional(request.getTelefono()));
        user.setDireccion(normalizeOptional(request.getDireccion()));

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getRole() != null && !request.getRole().isBlank()) {
            if (currentUser.getRole() != Role.ADMIN) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo un administrador puede cambiar el rol");
            }
            Role role = Role.fromRequest(request.getRole());
            Rol rol = rolRepository.findByNombreRolIgnoreCase(role.getDbName())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "El rol indicado no existe en la base de datos"
                    ));
            user.setRol(rol);
        }

        return UserResponseDTO.fromUser(userRepository.save(user));
    }

    @Transactional
    public UserResponseDTO softDelete(Integer id, User currentUser) {
        User user = findUserOrThrow(id);
        ensureCanManage(user, currentUser);

        if (!user.isActivo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya esta inactivo");
        }

        user.setActivo(false);
        return UserResponseDTO.fromUser(userRepository.save(user));
    }

    private User findUserOrThrow(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    private void ensureCanManage(User target, User currentUser) {
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No autenticado");
        }
        boolean isAdmin = currentUser.getRole() == Role.ADMIN;
        boolean isSelf = currentUser.getId().equals(target.getId());
        if (!isAdmin && !isSelf) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para gestionar este usuario");
        }
    }

    private void ensureAdmin(User currentUser) {
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No autenticado");
        }
        if (currentUser.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo un administrador puede listar usuarios");
        }
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    private String normalizeOptional(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
