package com.duoc.fluxpuzzles.Controller;

import com.duoc.fluxpuzzles.Model.Usuario;
import com.duoc.fluxpuzzles.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuarios() {
        System.out.println("[UsuarioController] -> getUsuarios");
        List<Usuario> usuarios = usuarioService.getUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<Usuario> saveUsuario(@Valid @RequestBody Usuario usuario) {
        System.out.println("[UsuarioController] -> saveUsuario");
        Usuario nuevoUsuario = usuarioService.saveUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioId(@PathVariable Integer id) {
        System.out.println("[UsuarioController] -> getUsuarioId");
        Usuario usuario = usuarioService.getUsuarioId(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        System.out.println("[UsuarioController] -> deleteUsuario");
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
