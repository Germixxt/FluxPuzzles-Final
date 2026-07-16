package com.duoc.fluxpuzzles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para puzzles.
 * 
 * Contiene la información básica de un puzzle para transferencia entre capas.
 * Se usa cuando se necesita una representación simplificada del modelo Puzzle.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PuzzleDTO {
    
    /** ID único del puzzle */
    private Long id;
    
    /** Nombre del puzzle */
    private String nombre;
    
    /** Nivel de dificultad (fácil, medio, difícil, etc.) */
    private String dificultad;
}
