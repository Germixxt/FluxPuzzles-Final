package com.duoc.fluxpuzzles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse {
    
    private String mensaje;
    private boolean exito;
}
