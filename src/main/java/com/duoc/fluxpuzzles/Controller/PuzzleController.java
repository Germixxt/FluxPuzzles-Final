package com.duoc.fluxpuzzles.Controller;

import com.duoc.fluxpuzzles.Model.Puzzle;
import com.duoc.fluxpuzzles.Service.PuzzleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/puzzles") // URL base: localhost:8080/puzzles
public class PuzzleController {

    @Autowired
    private PuzzleService puzzleService;

    // 1. Listar todos los puzzles que están activos para el público
    @GetMapping("/todos")
    public List<Puzzle> listarTodos() {
        return puzzleService.obtenerTodosVisibles();
    }

    // 2. Crear un nuevo puzzle
    @PostMapping("/crear")
    public ResponseEntity<Puzzle> crear(@RequestBody Puzzle puzzle) {
        Puzzle nuevoPuzzle = puzzleService.crearPuzzle(puzzle);
        return ResponseEntity.ok(nuevoPuzzle);
    }

    // 3. Buscar por dificultad (ejemplo: /puzzles/dificultad/Facil)
    @GetMapping("/dificultad/{nivel}")
    public List<Puzzle> buscarPorDificultad(@PathVariable String nivel) {
        return puzzleService.buscarPorDificultad(nivel);
    }

    /**
     * 4. Eliminar un puzzle (Borrado Lógico con validación de dueño)
     * URL: localhost:8080/puzzles/eliminar/5?idUsuario=1
     */
    @DeleteMapping("/eliminar/{idPuzzle}")
    public ResponseEntity<String> eliminar(
            @PathVariable int idPuzzle, 
            @RequestParam int idUsuario) {
        try {
            boolean exito = puzzleService.eliminarPropioPuzzle(idPuzzle, idUsuario);
            if (exito) {
                return ResponseEntity.ok("El puzzle ha sido desactivado exitosamente.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Si el ID del usuario no coincide con el creador, capturamos el error
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}