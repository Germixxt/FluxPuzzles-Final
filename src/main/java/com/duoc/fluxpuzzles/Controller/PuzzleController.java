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

@RestController
@RequestMapping("/api/v1/puzzles")
public class PuzzleController {

    @Autowired
    private PuzzleService puzzleService;

    @GetMapping
    public ResponseEntity<List<Puzzle>> listarPuzzles() {
        System.out.println("[PuzzleController] -> listarPuzzles");
        List<Puzzle> puzzles = puzzleService.getPuzzles();
        return ResponseEntity.ok(puzzles);
    }

    @PostMapping
    public ResponseEntity<Puzzle> crearPuzzle(@Valid @RequestBody Puzzle puzzle) {
        System.out.println("[PuzzleController] -> crearPuzzle");
        Puzzle nuevoPuzzle = puzzleService.savePuzzle(puzzle);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPuzzle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Puzzle> obtenerPuzzle(@PathVariable Integer id) {
        System.out.println("[PuzzleController] -> obtenerPuzzle");
        Puzzle puzzle = puzzleService.getPuzzleId(id);
        if (puzzle != null) {
            return ResponseEntity.ok(puzzle);
        }
        return ResponseEntity.notFound().build();
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPuzzle(@PathVariable Integer id) {
        System.out.println("[PuzzleController] -> eliminarPuzzle");
        puzzleService.deletePuzzle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/con-creador")
    public ResponseEntity<List<PuzzleCreadorDTO>> puzzlesConCreador() {
        System.out.println("[PuzzleController] -> puzzlesConCreador");
        List<PuzzleCreadorDTO> puzzles = puzzleService.getPuzzlesConCreador();
        return ResponseEntity.ok(puzzles);
    }

    @GetMapping("/test-error")
    public ResponseEntity<Void> testError() {
        System.out.println("[PuzzleController] -> testError");
        throw new RuntimeException("Este es un error de prueba lanzado intencionalmente");
    }
}



