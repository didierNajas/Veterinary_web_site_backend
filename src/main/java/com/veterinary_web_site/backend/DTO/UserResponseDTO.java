package com.veterinary_web_site.backend.DTO;

import com.veterinary_web_site.backend.model.Role;
import com.veterinary_web_site.backend.model.User;

public class UserResponseDTO {
    private Integer id;
    private String fullName;
    private String email;
    private Role role;

    public UserResponseDTO() {
    }

    public UserResponseDTO(Integer id, String fullName, String email, Role role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    public static UserResponseDTO fromUser(User user) {
        return new UserResponseDTO(user.getId(), user.getFullName(), user.getEmail(), user.getRole());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
