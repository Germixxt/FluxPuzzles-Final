package com.duoc.fluxpuzzles.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiError {
    
    private int codigo;
    private String mensaje;
    private String detalle;
}
