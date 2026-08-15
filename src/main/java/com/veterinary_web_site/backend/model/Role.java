package com.veterinary_web_site.backend.model;

import java.util.Locale;

public enum Role {
    ADMIN("admin"),
    VETERINARIAN("veterinario"),
    CUSTOMER("cliente");

    private final String dbName;

    Role(String dbName) {
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }

    public static Role fromDbName(String value) {
        if (value == null || value.isBlank()) {
            return CUSTOMER;
        }
        String normalized = value.trim().toLowerCase(Locale.ROOT);
        for (Role role : values()) {
            if (role.dbName.equals(normalized)) {
                return role;
            }
        }
        return valueOf(normalized.toUpperCase(Locale.ROOT));
    }

    public static Role fromRequest(String value) {
        if (value == null || value.isBlank()) {
            return CUSTOMER;
        }
        String normalized = value.trim().toLowerCase(Locale.ROOT);
        return switch (normalized) {
            case "admin", "administrador" -> ADMIN;
            case "veterinario", "veterinarian" -> VETERINARIAN;
            case "cliente", "customer" -> CUSTOMER;
            default -> valueOf(normalized.toUpperCase(Locale.ROOT));
        };
    }
}
