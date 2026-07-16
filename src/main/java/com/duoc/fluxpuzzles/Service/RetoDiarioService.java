package com.duoc.fluxpuzzles.Service;

import com.duoc.fluxpuzzles.dto.ExternalApiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Servicio para obtener retos diarios desde una API externa.
 * 
 * Responsabilidades:
 * - Consumir la API externa JSONPlaceholder
 * - Obtener un post/reto específ
ico por ID
 * - Mapear la respuesta a ExternalApiDTO
 * 
 * Integración externa:
 *   GET https://jsonplaceholder.typicode.com/posts/{id}
 */
@Service
public class RetoDiarioService {

    @Autowired
    private WebClient webClient;

    /**
     * Obtiene un puzzle/reto de la API externa por ID.
     * 
     * @param id ID del reto a obtener en la API externa
     * @return datos del reto (title, body, userId, etc.)
     */
    public ExternalApiDTO obtenerPuzzleExterno(int id) {
        System.out.println("[RetoDiarioService] -> obtenerPuzzleExterno");
        ExternalApiDTO puzzle = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/posts/{id}")
                .build(id))
            .retrieve()
            .bodyToMono(ExternalApiDTO.class)
            .block();
        return puzzle;
    }
}




