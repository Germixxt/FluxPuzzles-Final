package com.duoc.fluxpuzzles.Repository;

import com.duoc.fluxpuzzles.Model.Puzzle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PuzzleRepository extends JpaRepository<Puzzle, Integer> {

    List<Puzzle> findByActivoTrue();
    
    Optional<Puzzle> findByIdAndActivoTrue(Integer id);

    List<Puzzle> findByIdUsuarioCreadorAndActivoTrue(int idUsuarioCreador);
    
    List<Puzzle> findByDificultadAndActivoTrue(String dificultad);

    boolean existsByNombrePuzzle(String nombrePuzzle);
}