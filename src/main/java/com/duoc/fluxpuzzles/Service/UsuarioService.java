package com.duoc.fluxpuzzles.Service;

import com.duoc.fluxpuzzles.Model.Usuario;
import com.duoc.fluxpuzzles.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getUsuarios() {
        System.out.println("[UsuarioService] -> getUsuarios");
        return usuarioRepository.findAll();
    }

    public Usuario saveUsuario(Usuario usuario) {
        System.out.println("[UsuarioService] -> saveUsuario");
        return usuarioRepository.save(usuario);
    }

    public Usuario getUsuarioId(Integer id) {
        System.out.println("[UsuarioService] -> getUsuarioId");
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario updateUsuario(Usuario usuario) {
        System.out.println("[UsuarioService] -> updateUsuario");
        if (usuarioRepository.existsById(usuario.getId())) {
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    public void deleteUsuario(Integer id) {
        System.out.println("[UsuarioService] -> deleteUsuario");
        usuarioRepository.deleteById(id);
    }
}