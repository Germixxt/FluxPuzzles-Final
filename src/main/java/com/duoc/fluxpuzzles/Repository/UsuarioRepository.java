package com.duoc.fluxpuzzles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.duoc.fluxpuzzles.Model.Usuario;
import java.util.Optional;

/**
 * Repositorio (Data Access Object) para la entidad Usuario.
 * 
 * Hereda de JpaRepository que proporciona métodos CRUD estándar.
 * Agrega un método personalizado para buscar usuarios por nombre de usuario.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    /**
     * Busca un usuario por su nombre de usuario (username).
     * 
     * Usado en:
     * - Autenticación (login)
     * - Registro (verificar que no exista)
     * - Carga de detalles de usuario en Spring Security
     * 
     * @param username nombre de usuario a buscar
     * @return Optional con el usuario si existe, vacío si no
     */
    Optional<Usuario> findByUsername(String username);
}