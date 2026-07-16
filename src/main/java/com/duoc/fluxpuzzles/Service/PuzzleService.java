package com.duoc.fluxpuzzles.Service;

import com.duoc.fluxpuzzles.Model.Puzzle;
import com.duoc.fluxpuzzles.Repository.PuzzleRepository;
import com.duoc.fluxpuzzles.dto.ExternalApiDTO;
import com.duoc.fluxpuzzles.dto.PuzzleCreadorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * Servicio de lógica de negocio para Puzzles.
 * 
 * Responsabilidades:
 * - CRUD completo: crear, leer, actualizar, eliminar puzzles
 * - Consultar API externa (JSONPlaceholder) para retos diarios
 * - Transformar datos entre modelos y DTOs
 * - Delegar operaciones de BD al repositorio
 * 
 * Patrón: Intermed
iario entre Controllers (entrada HTTP) y Repository (BD).
 */
@Service
public class PuzzleService {

    @Autowired
    private PuzzleRepository puzzleRepository;

    @Autowired
    private WebClient webClient;

    /**
     * Obtiene todos los puzzles de la base de datos.
     * @return lista de todos los puzzles
     */
    public List<Puzzle> getPuzzles() {
        System.out.println("[PuzzleService] -> getPuzzles");
        return puzzleRepository.findAll();
    }

    /**
     * Crea o actualiza un puzzle.
     * @param puzzle datos del puzzle a guardar
     * @return puzzle guardado con ID asignado
     */
    public Puzzle savePuzzle(Puzzle puzzle) {
        System.out.println("[PuzzleService] -> savePuzzle");
        return puzzleRepository.save(puzzle);
    }

    /**
     * Obtiene un puzzle por su ID.
     * @param id ID del puzzle a buscar
     * @return puzzle encontrado o null
     */
    public Puzzle getPuzzleId(Integer id) {
        System.out.println("[PuzzleService] -> getPuzzleId");
        return puzzleRepository.findById(id).orElse(null);
    }

    /**
     * Actualiza un puzzle existente.
     * @param puzzle puzzle con ID y datos nuevos
     * @return puzzle actualizado o null si no existe
     */
    public Puzzle updatePuzzle(Puzzle puzzle) {
        System.out.println("[PuzzleService] -> updatePuzzle");
        if (puzzleRepository.existsById(puzzle.getId())) {
            return puzzleRepository.save(puzzle);
        }
        return null;
    }

    /**
     * Elimina un puzzle por su ID.
     * @param id ID del puzzle a eliminar
     */
    public void deletePuzzle(Integer id) {
        System.out.println("[PuzzleService] -> deletePuzzle");
        puzzleRepository.deleteById(id);
    }

    /**
     * Obtiene retos diarios desde la API externa (JSONPlaceholder).
     * Consulta los últimos 5 "todos" del usuario 1.
     * @return array de retos externos
     */
    public ExternalApiDTO[] obtenerRetoDiario() {
        System.out.println("[PuzzleService] -> obtenerRetoDiario");
        ExternalApiDTO[] retos = webClient.get()
            .uri("/todos?userId=1&_limit=5")
            .retrieve()
            .bodyToMono(ExternalApiDTO[].class)
            .block();
        return retos;
    }

    /**
     * Obtiene todos los puzzles con el nombre de su creador.
     * Mapea: Puzzle -> PuzzleCreadorDTO
     * @return lista de DTOs con nombre de puzzle y creador
     */
    public List<PuzzleCreadorDTO> getPuzzlesConCreador() {
        System.out.println("[PuzzleService] -> getPuzzlesConCreador");
        return puzzleRepository.findAll().stream()
            .map(puzzle -> new PuzzleCreadorDTO(
                puzzle.getNombrePuzzle(),
                puzzle.getUsuario().getNombre()
            ))
            .toList();
    }
}