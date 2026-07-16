package com.duoc.fluxpuzzles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para respuestas de autenticación (login exitoso).
 * 
 * Se devuelve en POST /api/v1/auth/login si las credenciales son válidas.
 * Ejemplo:
 *   { "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." }
 * 
 * El cliente debe guardar este token y enviarlo en cada request:
 *   Authorization: Bearer <token>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    /** JWT válido por 24 horas, contiene username y rol */
    private String token;
}