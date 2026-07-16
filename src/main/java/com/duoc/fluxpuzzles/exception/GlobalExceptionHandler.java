package com.duoc.fluxpuzzles.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * Manejador global de excepciones.
 * 
 * @RestControllerAdvice intercepta excepciones de todos los controladores
 * y las convierte en respuestas JSON estandarizadas.
 * 
 * Beneficios:
 * - Respuestas consistentes con ApiError
 * - Códigos HTTP apropiados
 * - Mensajes descriptivos para el cliente
 * - Manejo centralizado sin duplicación
 * 
 * Excepciones manejadas:
 * 1. MethodArgumentNotValidException: errores de validación (@Valid)
 * 2. WebClientResponseException: errores de APIs externas
 * 3. Exception: cualquier otra excepción no esperada
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja errores de validación (@Valid falla) -> 400 Bad Request
     * 
     * Se lanza cuando un DTO o entidad no cumple sus anotaciones de validación.
     * Ejemplo: @NotBlank, @Min, etc.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex) {

        // Recorre todos los campos que fallaron la validación y arma un mensaje
        StringBuilder detalle = new StringBuilder();
        for (FieldError campo : ex.getBindingResult().getFieldErrors()) {
            detalle.append(campo.getField())           // nombre del campo (ej: "nombre")
                   .append(": ")
                   .append(campo.getDefaultMessage())  // mensaje de la anotación
                   .append(", ");
        }

        ApiError error = new ApiError(400, "Error de validación", detalle.toString());
        return ResponseEntity.badRequest().body(error);
    }

    /**
     * Maneja cualquier otra excepción no esperada -> 500 Internal Server Error
     * 
     * Se ejecuta como último recurso si ninguno de los otros manejadores coincide.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericError(Exception ex) {
        ApiError error = new ApiError(500, "Error interno del servidor", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * Maneja errores al llamar APIs externas -> 404 o 502
     * 
     * Se lanza cuando WebClient falla al conectar o recibe respuesta de error.
     */
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ApiError> handleWebClientError(WebClientResponseException ex) {
        // Si la API externa retorna 404
        if (ex.getStatusCode().value() == 404) {
            ApiError error = new ApiError(404, "Recurso no encontrado en la API externa", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        // Cualquier otro error de API externa
        ApiError error = new ApiError(502, "Error al consultar la API externa", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(error);
    }
}