package com.duoc.fluxpuzzles.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad JPA que representa un usuario en la base de datos.
 * 
 * Tabla: usuarios
 * Responsabilidades:
 * - Almacenar información del usuario (nombre, email, credenciales)
 * - Gestionar datos de autenticación (username, password encriptada, rol)
 * - Ser creador de puzzles
 * 
 * Campos de seguridad:
 * - password: se almacena encriptada con BCrypt, NUNCA en texto plano
 * - role: define permisos (ROLE_USER, ROLE_ADMIN)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Nombre completo del usuario */
    private String nombre;
    
    /** Edad del usuario */
    private Integer edad;
    
    /** Correo electrónico del usuario */
    private String correo;
    
    /** Nombre de usuario único para login */
    private String username;
    
    /** Contraseña encriptada con BCrypt */
    private String password;
    
    /** Rol del usuario: ROLE_USER o ROLE_ADMIN */
    private String role;
}