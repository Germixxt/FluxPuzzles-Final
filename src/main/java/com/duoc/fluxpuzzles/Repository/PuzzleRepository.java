package com.duoc.fluxpuzzles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.duoc.fluxpuzzles.Model.Puzzle;

/**
 * Repositorio (Data Access Object) para la entidad Puzzle.
 * 
 * Hereda de JpaRepository que proporciona métodos CRUD estándar:
 * - save(Puzzle): insertar o actualizar
 * - findById(id): obtener por ID
 * - findAll(): obtener todos
 * - deleteById(id): eliminar por ID
 * - existsById(id): verificar existencia
 * 
 * Los métodos se generan automáticamente en tiempo de ejecución.
 */
public interface PuzzleRepository extends JpaRepository<Puzzle, Integer> {

}