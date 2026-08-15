package com.veterinary_web_site.backend.service;

import com.veterinary_web_site.backend.DTO.AuthRequestDTO;
import com.veterinary_web_site.backend.DTO.AuthResponseDTO;
import com.veterinary_web_site.backend.DTO.RegisterRequestDTO;
import com.veterinary_web_site.backend.DTO.UserResponseDTO;
import com.veterinary_web_site.backend.model.Rol;
import com.veterinary_web_site.backend.model.Role;
import com.veterinary_web_site.backend.model.User;
import com.veterinary_web_site.backend.repository.RolRepository;
import com.veterinary_web_site.backend.repository.UserRepository;
import com.veterinary_web_site.backend.security.JwtService;
import java.util.Locale;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository,
            RolRepository rolRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponseDTO register(RegisterRequestDTO request) {
        String email = normalizeEmail(request.getEmail());

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo");
        }

        Role role = Role.fromRequest(request.getRole());
        Rol rol = rolRepository.findByNombreRolIgnoreCase(role.getDbName())
                .orElseThrow(() -> new IllegalArgumentException("El rol indicado no existe en la base de datos"));

        String[] names = User.splitFullName(request.getFullName());
        User user = new User(
                names[0],
                names[1].isBlank() ? names[0] : names[1],
                email,
                passwordEncoder.encode(request.getPassword()),
                rol
        );

        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);
        return new AuthResponseDTO(token, UserResponseDTO.fromUser(savedUser));
    }

    public AuthResponseDTO login(AuthRequestDTO request) {
        String email = normalizeEmail(request.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, request.getPassword())
        );

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Credenciales invalidas"));
        String token = jwtService.generateToken(user);
        return new AuthResponseDTO(token, UserResponseDTO.fromUser(user));
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
