package com.duoc.fluxpuzzles.security;


import com.duoc.fluxpuzzles.Model.Usuario;
import com.duoc.fluxpuzzles.Repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Implementación de UserDetailsService requerida por Spring Security.
 *
 * Spring la llama internamente durante el proceso de autenticación
 * (AuthenticationManager.authenticate) para cargar al usuario desde la BD
 * y comparar su contraseña encriptada con la recibida en el login.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority(usuario.getRole()))
        );
    }
}