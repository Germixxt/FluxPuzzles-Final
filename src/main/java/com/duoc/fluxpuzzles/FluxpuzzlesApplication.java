package com.duoc.fluxpuzzles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.duoc.fluxpuzzles.Model.Usuario;
import com.duoc.fluxpuzzles.Repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Clase principal de la aplicación Spring Boot: FluxPuzzles.
 * 
 * Esta clase marca el punto de entrada de la aplicación y activa:
 * - Component scanning: busca beans en el paquete com.duoc.fluxpuzzles
 * - Auto-configuration: configura automáticamente Spring según las librerías en classpath
 * - Configuration properties: carga application.properties
 * 
 * La aplicación proporciona una API REST con autenticación JWT para:
 * - Gestionar usuarios (registro, login)
 * - Administrar puzzles (crear, leer, actualizar, eliminar)
 * - Obtener retos diarios desde una API externa
 */


@SpringBootApplication
public class FluxpuzzlesApplication {

    public static void main(String[] args) {
        SpringApplication.run(FluxpuzzlesApplication.class, args);
    }

    @Bean
    CommandLineRunner initAdmin(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ROLE_ADMIN");
                usuarioRepository.save(admin);
            }
        };
    }
}
