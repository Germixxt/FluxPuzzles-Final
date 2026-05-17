package com.duoc.fluxpuzzles.Service;

import com.duoc.fluxpuzzles.Model.Puzzle;
import com.duoc.fluxpuzzles.Repository.PuzzleRepository;
import com.duoc.fluxpuzzles.dto.ExternalApiDTO;
import com.duoc.fluxpuzzles.dto.PuzzleCreadorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class PuzzleService {

    @Autowired
    private PuzzleRepository puzzleRepository;

    @Autowired
    private WebClient webClient;

    public List<Puzzle> getPuzzles() {
        System.out.println("[PuzzleService] -> getPuzzles");
        return puzzleRepository.findAll();
    }

    public Puzzle savePuzzle(Puzzle puzzle) {
        System.out.println("[PuzzleService] -> savePuzzle");
        return puzzleRepository.save(puzzle);
    }

    public Puzzle getPuzzleId(Integer id) {
        System.out.println("[PuzzleService] -> getPuzzleId");
        return puzzleRepository.findById(id).orElse(null);
    }

    public Puzzle updatePuzzle(Puzzle puzzle) {
        System.out.println("[PuzzleService] -> updatePuzzle");
        if (puzzleRepository.existsById(puzzle.getId())) {
            return puzzleRepository.save(puzzle);
        }
        return null;
    }

    public void deletePuzzle(Integer id) {
        System.out.println("[PuzzleService] -> deletePuzzle");
        puzzleRepository.deleteById(id);
    }

    public ExternalApiDTO[] obtenerRetoDiario() {
        System.out.println("[PuzzleService] -> obtenerRetoDiario");
        ExternalApiDTO[] retos = webClient.get()
            .uri("/todos?userId=1&_limit=5")
            .retrieve()
            .bodyToMono(ExternalApiDTO[].class)
            .block();
        return retos;
    }

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