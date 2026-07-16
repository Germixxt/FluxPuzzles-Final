package com.duoc.fluxpuzzles.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuración de WebClient para consumir APIs externas de forma reactiva.
 * 
 * WebClient es un cliente HTTP no bloqueante de Spring que permite:
 * - Realizar llamadas a APIs externas (como JSONPlaceholder)
 * - Trabajar con flujos reactivos (Mono/Flux)
 * - Manejo eficiente de recursos sin threads dedicados
 * 
 * La URL base se configura en application.properties:
 *   jsonplaceholder.base-url=https://jsonplaceholder.typicode.com
 */
@Configuration
public class WebClientConfig {
    
    @Value("${jsonplaceholder.base-url}")
    private String baseUrl;
    
    /**
     * Bean de WebClient: inyectable en servicios.
     * Proporciona la URL base y se personaliza aquí para usarlo en toda la app.
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }
}
