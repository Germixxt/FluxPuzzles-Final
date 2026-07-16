package com.duoc.fluxpuzzles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que combina información del puzzle con su creador.
 * 
 * Se usa en el endpoint GET /api/v1/puzzles/con-creador
 * para devolver puzzles junto con el nombre del usuario que los creó.
 * 
 * Ejemplo:
 *   { "nombrePuzzle": "Puzzle 1", "nombreCreador": "Juan Pérez" }
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PuzzleCreadorDTO {
    
    /** Nombre del puzzle */
    private String nombrePuzzle;
    
    /** Nombre del usuario creador */
    private String nombreCreador;
}
