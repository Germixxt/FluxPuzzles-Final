package com.duoc.fluxpuzzles.Service;

import com.duoc.fluxpuzzles.dto.ExternalApiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RetoDiarioService {

    @Autowired
    private WebClient webClient;

    public ExternalApiDTO obtenerPuzzleExterno(int id) {
        System.out.println("[RetoDiarioService] -> obtenerPuzzleExterno");
        ExternalApiDTO puzzle = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/posts/{id}")
                .build(id))
            .retrieve()
            .bodyToMono(ExternalApiDTO.class)
            .block();
        return puzzle;
    }
}
