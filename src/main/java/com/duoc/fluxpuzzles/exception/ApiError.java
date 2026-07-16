package com.duoc.fluxpuzzles.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para respuestas de error estandarizadas.
 * 
 * Se usa en GlobalExceptionHandler para enviar errores al cliente
 * en formato JSON consistente.
 * 
 * Ejemplo:
 *   {
 *     "codigo": 400,
 *     "mensaje": "Error de validación",
 *     "detalle": "nombre: no puede estar vacío, edad: debe ser mayor a 0"
 *   }
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiError {
    
    /** Código HTTP de error (400, 404, 500, etc.) */
    private int codigo;
    
    /** Mensaje general del error */
    private String mensaje;
    
    /** Detalles específicos del error */
    private String detalle;
}
