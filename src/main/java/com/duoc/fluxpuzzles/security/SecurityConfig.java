package com.duoc.fluxpuzzles.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * CONFIGURACIÓN CENTRAL DE SPRING SECURITY
 * 
 * Responsabilidades:
 * 1. Definir qué rutas son públicas y cuáles requieren JWT
 * 2. Establecer roles y permisos por método HTTP
 * 3. Configurar seguridad stateless (sin sesiones en servidor)
 * 4. Registrar JwtFilter en la cadena de filtros
 * 5. Configurar BCrypt para encriptación de contraseñas
 * 
 * Modelo de acceso:
 * - ROLE_USER: solo lectura (GET)
 * - ROLE_ADMIN: acceso total (GET, POST, PUT, DELETE)
 * - /api/v1/auth/**: público (sin autenticación)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilita CSRF (no es necesario en APIs REST stateless con JWT)
            .csrf(csrf -> csrf.disable())

            // Sin sesiones en servidor: cada request se autentica solo con el JWT
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Reglas de autorización por ruta y método HTTP
            .authorizeHttpRequests(auth -> auth
                // Endpoints de autenticación: públicos (no requieren token)
                .requestMatchers("/api/v1/auth/**").permitAll()

                // Swagger UI y OpenAPI docs: públicos
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**"
                ).permitAll()

                // Lectura (GET): cualquier usuario autenticado (USER o ADMIN)
                .requestMatchers(HttpMethod.GET, "/api/v1/**").hasAnyRole("USER", "ADMIN")

                // Escritura (POST, PUT, DELETE): solo ADMIN
                .requestMatchers(HttpMethod.POST, "/api/v1/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasRole("ADMIN")

                // Cualquier otra ruta requiere autenticación
                .anyRequest().authenticated()
            )

            // Agrega el filtro JWT antes del filtro de autenticación por usuario/contraseña
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * BCrypt para encriptar contraseñas. Factor de costo 10 (por defecto).
     * NUNCA se guarda la contraseña en texto plano en la BD.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Expone el AuthenticationManager como bean para usarlo en AuthController.
     * Spring lo configura automáticamente usando UserDetailsServiceImpl y PasswordEncoder.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}