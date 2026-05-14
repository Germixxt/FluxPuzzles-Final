package com.duoc.fluxpuzzles.Controller;

import com.duoc.fluxpuzzles.Model.Usuario;
import com.duoc.fluxpuzzles.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios") // Esta será la URL base: localhost:8080/usuarios
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // 1. Endpoint para obtener todos los activos
    @GetMapping("/listar")
    public List<Usuario> listarUsuarios() {
        return usuarioService.obtenerTodosActivos();
    }

    // 2. Endpoint para registrar un nuevo usuario
    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        try {
            Usuario nuevo = usuarioService.registrarUsuario(usuario);
            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            // Si el service lanza error (correo duplicado, edad, etc.), lo capturamos aquí
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 3. Endpoint para borrar (lógico)
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        boolean eliminado = usuarioService.eliminarLogicamente(id);
        if (eliminado) {
            return ResponseEntity.ok("Usuario desactivado correctamente.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
