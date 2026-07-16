package com.duoc.fluxpuzzles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para mapear respuestas de la API externa JSONPlaceholder.
 * 
 * Se usa al consumir endpoints como:
 *   GET https://jsonplaceholder.typicode.com/posts/{id}
 *   GET https://jsonplaceholder.typicode.com/todos
 * 
 * Ejemplo de respuesta:
 *   { "userId": 1, "id": 1, "title": "Puzzle 1", "body": "Description..." }
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalApiDTO {
    
    /** ID del usuario propietario del recurso en la API externa */
    private Integer userId;
    
    /** ID único del recurso */
    private Integer id;
    
    /** Título del puzzle o reto */
    private String title;
    
    /** Descripción o contenido del puzzle */
    private String body;
    
}