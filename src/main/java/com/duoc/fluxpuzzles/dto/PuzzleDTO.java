package com.duoc.fluxpuzzles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PuzzleDTO {
    
    private Long id;
    private String nombre;
    private String dificultad;
}
