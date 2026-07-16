package com.duoc.fluxpuzzles.Controller;

import com.duoc.fluxpuzzles.Model.Puzzle;
import com.duoc.fluxpuzzles.Service.PuzzleService;
import com.duoc.fluxpuzzles.dto.PuzzleCreadorDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operaciones CRUD sobre Puzzles.
 * 
 * Ruta base: /api/v1/puzzles
 * 
 * Seguridad:
 * - GET: requiere autenticación (ROLE_USER o ROLE_ADMIN)
 * - POST, PUT, DELETE: solo ROLE_ADMIN
 * 
 * Responsabilidades:
 * - Recibir requests HTTP y validar parámetros
 * - Delegar lógica de negocio a PuzzleService
 * - Retornar respuestas HTTP con códigos apropiados
 */
@RestController
@RequestMapping("/api/v1/puzzles")
public class PuzzleController {

    @Autowired
    private PuzzleService puzzleService;

    /**
     * GET /api/v1/puzzles
     * Obtiene la lista de todos los puzzles.
     * @return 200 OK con lista de puzzles
     */
    @GetMapping
    public ResponseEntity<List<Puzzle>> listarPuzzles() {
        System.out.println("[PuzzleController] -> listarPuzzles");
        List<Puzzle> puzzles = puzzleService.getPuzzles();
        return ResponseEntity.ok(puzzles);
    }

    /**
     * POST /api/v1/puzzles
     * Crea un nuevo puzzle.
     * @param puzzle datos del puzzle (validado con @Valid)
     * @return 201 CREATED con el puzzle creado
     */
    @PostMapping
    public ResponseEntity<Puzzle> crearPuzzle(@Valid @RequestBody Puzzle puzzle) {
        System.out.println("[PuzzleController] -> crearPuzzle");
        Puzzle nuevoPuzzle = puzzleService.savePuzzle(puzzle);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPuzzle);
    }

    /**
     * GET /api/v1/puzzles/{id}
     * Obtiene un puzzle por su ID.
     * @param id ID del puzzle
     * @return 200 OK con el puzzle, o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Puzzle> obtenerPuzzle(@PathVariable Integer id) {
        System.out.println("[PuzzleController] -> obtenerPuzzle");
        Puzzle puzzle = puzzleService.getPuzzleId(id);
        if (puzzle != null) {
            return ResponseEntity.ok(puzzle);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * PUT /api/v1/puzzles/{id}
     * Actualiza un puzzle existente.
     * @param id ID del puzzle a actualizar
     * @param puzzle nuevos datos del puzzle
     * @return 200 OK con puzzle actualizado, o 404 si no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<Puzzle> actualizarPuzzle(@PathVariable Integer id, @Valid @RequestBody Puzzle puzzle) {
        System.out.println("[PuzzleController] -> actualizarPuzzle");
        puzzle.setId(id);
        Puzzle puzzleActualizado = puzzleService.updatePuzzle(puzzle);
        if (puzzleActualizado != null) {
            return ResponseEntity.ok(puzzleActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * DELETE /api/v1/puzzles/{id}
     * Elimina un puzzle.
     * @param id ID del puzzle a eliminar
     * @return 204 NO CONTENT
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPuzzle(@PathVariable Integer id) {
        System.out.println("[PuzzleController] -> eliminarPuzzle");
        puzzleService.deletePuzzle(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/v1/puzzles/con-creador
     * Obtiene puzzles junto con el nombre de su creador (usuario).
     * @return 200 OK con lista de DTOs (nombrePuzzle, nombreCreador)
     */
    @GetMapping("/con-creador")
    public ResponseEntity<List<PuzzleCreadorDTO>> puzzlesConCreador() {
        System.out.println("[PuzzleController] -> puzzlesConCreador");
        List<PuzzleCreadorDTO> puzzles = puzzleService.getPuzzlesConCreador();
        return ResponseEntity.ok(puzzles);
    }

    /**
     * GET /api/v1/puzzles/test-error
     * Endpoint de prueba para verificar manejo de errores.
     * Lanza una excepción que debe ser capturada por GlobalExceptionHandler.
     */
    @GetMapping("/test-error")
    public ResponseEntity<Void> testError() {
        System.out.println("[PuzzleController] -> testError");
        throw new RuntimeException("Este es un error de prueba lanzado intencionalmente");
    }
}
