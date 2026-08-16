package com.veterinary_web_site.backend.controller;

import com.veterinary_web_site.backend.DTO.UserRequestDTO;
import com.veterinary_web_site.backend.DTO.UserResponseDTO;
import com.veterinary_web_site.backend.model.User;
import com.veterinary_web_site.backend.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(UserResponseDTO.fromUser(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> list(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(userService.listAll(currentUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserRequestDTO request,
            @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.ok(userService.update(id, request, currentUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDTO> softDelete(
            @PathVariable Integer id,
            @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.ok(userService.softDelete(id, currentUser));
    }
}
