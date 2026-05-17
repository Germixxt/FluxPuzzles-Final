package com.duoc.fluxpuzzles.Controller;

import com.duoc.fluxpuzzles.Service.RetoDiarioService;
import com.duoc.fluxpuzzles.dto.ExternalApiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reto-diario")
public class RetoDiarioController {

    @Autowired
    private RetoDiarioService retoDiarioService;

    @GetMapping
    public ResponseEntity<ExternalApiDTO> obtenerRetoDiario(@RequestParam(defaultValue = "1") int id) {
        System.out.println("[RetoDiarioController] -> obtenerRetoDiario id=" + id);
        ExternalApiDTO puzzle = retoDiarioService.obtenerPuzzleExterno(id);
        return ResponseEntity.ok(puzzle);
    }
}
