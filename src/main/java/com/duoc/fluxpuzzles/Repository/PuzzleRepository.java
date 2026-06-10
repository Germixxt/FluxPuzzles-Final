package com.duoc.fluxpuzzles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.duoc.fluxpuzzles.Model.Puzzle;

public interface PuzzleRepository extends JpaRepository<Puzzle, Integer> {

}