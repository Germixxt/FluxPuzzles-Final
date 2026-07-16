package com.duoc.fluxpuzzles.Service;

import com.duoc.fluxpuzzles.Model.Usuario;
import com.duoc.fluxpuzzles.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de lógica de negocio para Usuarios.
 * 
 * Responsabilidades:
 * - CRUD completo: crear, leer, actualizar, eliminar usuarios
 * - Delegar operaciones de BD al repositorio
 * - Validar existencia antes de actualizar o eliminar
 * 
 * Nota: La autenticación (login/register) se maneja en AuthController,
 * este servicio es para administración general de usuarios.
 * 
 * Patrón: Intermediario entre Controllers (entrada HTTP) y Repository (BD).
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Obtiene todos los usuarios de la base de datos.
     * @return lista de todos los usuarios
     */
    public List<Usuario> getUsuarios() {
        System.out.println("[UsuarioService] -> getUsuarios");
        return usuarioRepository.findAll();
    }

    /**
     * Crea un nuevo usuario.
     * La contraseña debe venir ya encriptada por BCrypt (desde AuthController).
     * @param usuario datos del usuario a guardar
     * @return usuario guardado con ID asignado
     */
    public Usuario saveUsuario(Usuario usuario) {
        System.out.println("[UsuarioService] -> saveUsuario");
        return usuarioRepository.save(usuario);
    }

    /**
     * Obtiene un usuario por su ID.
     * @param id ID del usuario a buscar
     * @return usuario encontrado o null
     */
    public Usuario getUsuarioId(Integer id) {
        System.out.println("[UsuarioService] -> getUsuarioId");
        return usuarioRepository.findById(id).orElse(null);
    }

    /**
     * Actualiza un usuario existente.
     * @param usuario usuario con ID y datos nuevos
     * @return usuario actualizado o null si no existe
     */
    public Usuario updateUsuario(Usuario usuario) {
        System.out.println("[UsuarioService] -> updateUsuario");
        if (usuarioRepository.existsById(usuario.getId())) {
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    /**
     * Elimina un usuario por su ID.
     * @param id ID del usuario a eliminar
     */
    public void deleteUsuario(Integer id) {
        System.out.println("[UsuarioService] -> deleteUsuario");
        usuarioRepository.deleteById(id);
    }
}