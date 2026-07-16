package com.duoc.fluxpuzzles.Controller;

import com.duoc.fluxpuzzles.Service.RetoDiarioService;
import com.duoc.fluxpuzzles.dto.ExternalApiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para obtener retos diarios desde API externa.
 * 
 * Ruta base: /api/v1/reto-diario
 * 
 * Responsabilidades:
 * - Recibir requests del cliente
 * - Delegar a RetoDiarioService para consumir API externa (JSONPlaceholder)
 * - Retornar retos/puzzles externos
 * 
 * Nota: Estos datos vienen de una API externa, no de la BD local.
 */
@RestController
@RequestMapping("/api/v1/reto-diario")
public class RetoDiarioController {

    @Autowired
    private RetoDiarioService retoDiarioService;

    /**
     * GET /api/v1/reto-diario?id=1
     * Obtiene un reto/puzzle desde la API externa JSONPlaceholder.
     * 
     * @param id ID del reto a obtener (default: 1)
     * @return 200 OK con datos del reto (title, body, userId, etc.)
     */
    @GetMapping
    public ResponseEntity<ExternalApiDTO> obtenerRetoDiario(@RequestParam(defaultValue = "1") int id) {
        System.out.println("[RetoDiarioController] -> obtenerRetoDiario id=" + id);
        ExternalApiDTO puzzle = retoDiarioService.obtenerPuzzleExterno(id);
        return ResponseEntity.ok(puzzle);
    }
}
