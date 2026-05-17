package com.duoc.fluxpuzzles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.duoc.fluxpuzzles.Model.Puzzle;

@Repository
public interface PuzzleRepository extends JpaRepository<Puzzle, Integer> {

}