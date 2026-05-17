package com.duoc.fluxpuzzles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalApiDTO {
    
    private Integer userId;
    private Integer id;
    private String title;
    private String body;
    
}