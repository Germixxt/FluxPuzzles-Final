package com.duoc.fluxpuzzles.dto;

import lombok.Data;

/**
 * DTO para solicitudes de autenticación (login y registro).
 * 
 * Se envía en el body de POST /api/v1/auth/login y /api/v1/auth/register
 * Ejemplo:
 *   { "username": "juan", "password": "micontraseña123" }
 */
@Data
public class AuthRequest {
    
    /** Nombre de usuario único */
    private String username;
    
    /** Contraseña del usuario (se encripta con BCrypt al guardar) */
    private String password;
}
