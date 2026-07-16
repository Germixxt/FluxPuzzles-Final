package com.duoc.fluxpuzzles.Controller;

import com.duoc.fluxpuzzles.Model.Usuario;
import com.duoc.fluxpuzzles.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operaciones CRUD sobre Usuarios.
 * 
 * Ruta base: /api/v1/usuarios
 * 
 * Seguridad:
 * - GET: requiere autenticación (ROLE_USER o ROLE_ADMIN)
 * - POST, PUT, DELETE: solo ROLE_ADMIN
 * 
 * Nota: El registro e inicio de sesión se manejan en AuthController (/api/v1/auth).
 * Este controlador es para administración general de usuarios por administradores.
 * 
 * Responsabilidades:
 * - Recibir requests HTTP y validar parámetros
 * - Delegar lógica de negocio a UsuarioService
 * - Retornar respuestas HTTP con códigos apropiados
 */
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * GET /api/v1/usuarios
     * Obtiene la lista de todos los usuarios.
     * @return 200 OK con lista de usuarios
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuarios() {
        System.out.println("[UsuarioController] -> getUsuarios");
        List<Usuario> usuarios = usuarioService.getUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * POST /api/v1/usuarios
     * Crea un nuevo usuario (solo para administradores).
     * Para registro público, usar POST /api/v1/auth/register
     * @param usuario datos del usuario (validado con @Valid)
     * @return 201 CREATED con el usuario creado
     */
    @PostMapping
    public ResponseEntity<Usuario> saveUsuario(@Valid @RequestBody Usuario usuario) {
        System.out.println("[UsuarioController] -> saveUsuario");
        Usuario nuevoUsuario = usuarioService.saveUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    /**
     * GET /api/v1/usuarios/{id}
     * Obtiene un usuario por su ID.
     * @param id ID del usuario
     * @return 200 OK con el usuario, o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioId(@PathVariable Integer id) {
        System.out.println("[UsuarioController] -> getUsuarioId");
        Usuario usuario = usuarioService.getUsuarioId(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * PUT /api/v1/usuarios/{id}
     * Actualiza un usuario existente (solo para administradores).
     * @param id ID del usuario a actualizar
     * @param usuario nuevos datos del usuario
     * @return 200 OK con usuario actualizado, o 404 si no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Integer id, @Valid @RequestBody Usuario usuario) {
        System.out.println("[UsuarioController] -> updateUsuario");
        usuario.setId(id);
        Usuario usuarioActualizado = usuarioService.updateUsuario(usuario);
        if (usuarioActualizado != null) {
            return ResponseEntity.ok(usuarioActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * DELETE /api/v1/usuarios/{id}
     * Elimina un usuario (solo para administradores).
     * @param id ID del usuario a eliminar
     * @return 204 NO CONTENT
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        System.out.println("[UsuarioController] -> deleteUsuario");
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
