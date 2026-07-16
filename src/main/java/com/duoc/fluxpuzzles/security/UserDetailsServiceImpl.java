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
 * (AuthenticationManager.authenticate) para:
 * 1. Cargar al usuario desde la base de datos por username
 * 2. Obtener su contraseña encriptada
 * 3. Obtener sus roles/autoridades
 * 4. El AuthenticationManager compara la contraseña ingresada con la almacenada
 * 
 * Flujo en login:
 *   POST /api/v1/auth/login → AuthController → AuthenticationManager → 
 *   UserDetailsServiceImpl.loadUserByUsername → UsuarioRepository
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Carga un usuario desde la base de datos por su nombre de usuario.
     * 
     * @param username nombre de usuario a buscar
     * @return UserDetails del usuario
     * @throws UsernameNotFoundException si el usuario no existe
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Convierte el Usuario a UserDetails que Spring Security entiende
        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority(usuario.getRole()))
        );
    }
}