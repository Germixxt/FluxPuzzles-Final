package com.duoc.fluxpuzzles.Service;

import com.duoc.fluxpuzzles.Model.Puzzle;
import com.duoc.fluxpuzzles.Repository.PuzzleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PuzzleService {

    @Autowired
    private PuzzleRepository puzzleRepository;

    // 1. Crear Puzzle
    public Puzzle crearPuzzle(Puzzle puzzle) {
        puzzle.setActivo(true); // Siempre entra como activo
        return puzzleRepository.save(puzzle);
    }

    // 2. Listar todos los puzzles activos
    public List<Puzzle> obtenerTodosVisibles() {
        return puzzleRepository.findByActivoTrue();
    }

    // 3. Buscar por dificultad
    public List<Puzzle> buscarPorDificultad(String dificultad) {
        // Podrías agregar un método findByDificultadAndActivoTrue en el Repository
        return puzzleRepository.findAll().stream()
                .filter(p -> p.isActivo() && p.getDificultad().equalsIgnoreCase(dificultad))
                .toList();
    }

    /**
     * BORRADO PROPIO (Seguridad Senior)
     * @param idPuzzle El puzzle que se quiere borrar
     * @param idUsuario El usuario que está intentando borrarlo
     */
    public boolean eliminarPropioPuzzle(int idPuzzle, int idUsuario) throws Exception {
        Optional<Puzzle> puzzleOpt = puzzleRepository.findById(idPuzzle);

        if (puzzleOpt.isPresent()) {
            Puzzle puzzle = puzzleOpt.get();

            // VALIDACIÓN CRÍTICA: ¿Es el dueño?
            if (!puzzle.getIdUsuarioCreador().equals(idUsuario)) {
                throw new RuntimeException("No tienes permisos para eliminar este puzzle");
            }

            puzzle.setActivo(false); // Borrado lógico
            puzzleRepository.save(puzzle);
            return true;
        }
        return false;
    }
}