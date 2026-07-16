package com.duoc.fluxpuzzles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para usuarios.
 * 
 * Contiene información pública del usuario (sin contraseña ni rol).
 * Se usa para transferir datos entre capas evitando exponer datos sensibles.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioDTO {
    
    /** ID único del usuario */
    private Long id;
    
    /** Nombre completo del usuario */
    private String nombre;
    
    /** Correo electrónico del usuario */
    private String correo;
    
    /** Nombre de usuario (único, usado para login) */
    private String username;
}
