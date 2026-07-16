package com.duoc.fluxpuzzles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para respuestas genéricas de la API.
 * 
 * Se usa para enviar mensajes y estados de éxito/fracaso al cliente.
 * Ejemplo:
 *   { "mensaje": "Operación exitosa", "exito": true }
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse {
    
    /** Mensaje descriptivo de la operación */
    private String mensaje;
    
    /** Indica si la operación fue exitosa */
    private boolean exito;
}
