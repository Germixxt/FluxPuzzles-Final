package com.duoc.fluxpuzzles.Service;
import com.duoc.fluxpuzzles.Model.Usuario;
import com.duoc.fluxpuzzles.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Obtiene solo los usuarios que no han sido "borrados" (activo = true).
     * Ideal para mostrar en listas de la aplicación.
     */
    public List<Usuario> obtenerTodosActivos() {
        return usuarioRepository.findByActivoTrue();
    }

    /**
     * Busca un usuario por su ID, pero solo si está activo.
     */
    public Optional<Usuario> obtenerPorId(int id) {
        return usuarioRepository.findByIdAndActivoTrue(id);
    }

    /**
     * Registra un nuevo usuario con validaciones de lógica de negocio.
     * Lanza una excepción si se rompen las reglas de unicidad.
     */
    public Usuario registrarUsuario(Usuario usuario) throws Exception {
        // Validación: El correo debe ser único
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new Exception("Error: El correo electrónico ya está registrado.");
        }

        // Validación: El nombre de usuario debe ser único
        if (usuarioRepository.existsByNombreUsuario(usuario.getNombreUsuario())) {
            throw new Exception("Error: El nombre de usuario ya existe.");
        }

        // Validación: Edad mínima para participar
        if (usuario.getEdad() < 13) {
            throw new Exception("Error: Debes ser mayor de 13 años para registrarte.");
        }

        // Aseguramos que el usuario nuevo siempre entre como activo
        usuario.setActivo(true);
        
        return usuarioRepository.save(usuario);
    }

    /**
     * Actualiza los datos de un usuario existente.
     */
    public Usuario actualizarUsuario(int id, Usuario datosNuevos) throws Exception {
        return usuarioRepository.findByIdAndActivoTrue(id).map(usuario -> {
            usuario.setNombre(datosNuevos.getNombre());
            usuario.setEdad(datosNuevos.getEdad());
            // No solemos permitir cambiar el correo o nombreUsuario tan fácil, 
            // pero si tu proyecto lo requiere, agrégalos aquí.
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new Exception("Usuario no encontrado o inactivo"));
    }

    /**
     * IMPLEMENTACIÓN DE BORRADO LÓGICO
     * En lugar de borrar de HeidiSQL, cambiamos el estado a 'false'.
     */
    public boolean eliminarLogicamente(int id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setActivo(false); // Desactivamos el "interruptor"
            usuarioRepository.save(usuario); // Persistimos el cambio
            return true;
        }
        return false;
    }
}