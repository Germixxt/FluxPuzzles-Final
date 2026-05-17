package com.duoc.fluxpuzzles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PuzzleCreadorDTO {
    private String nombrePuzzle;
    private String nombreCreador;
}
