package com.duoc.fluxpuzzles;

import com.duoc.fluxpuzzles.Model.Puzzle;
import com.duoc.fluxpuzzles.Model.Usuario;
import com.duoc.fluxpuzzles.Controller.PuzzleController;
import com.duoc.fluxpuzzles.Service.PuzzleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PuzzleControllerTest {

    @Mock
    private PuzzleService puzzleService;

    @InjectMocks
    private PuzzleController puzzleController;

    @Test
    void listarPuzzles_retornaListaDePuzzles() {

        Usuario usuario = new Usuario(
                1,
                "Michael",
                25,
                "michael@email.com",
                "michael123",
                "123456",
                "USER"
        );

       Puzzle puzzle1 = new Puzzle(
                1,
                usuario,
                "Puzzle Dragon Ball",
                "Puzzle de 500 piezas",
                "Media"
        );

        Puzzle puzzle2 = new Puzzle(
                2,
                usuario,
                "Puzzle Naruto",
                "Puzzle de 1000 piezas",
                "Dificil"
        );

        List<Puzzle> puzzles = List.of(puzzle1, puzzle2);

        when(puzzleService.getPuzzles()).thenReturn(puzzles);

        ResponseEntity<List<Puzzle>> respuesta =
                puzzleController.listarPuzzles();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(2, respuesta.getBody().size());
    }

    @Test
    void crearPuzzle_retorna201() {

        Usuario usuario = new Usuario(
                1,
                "Michael",
                25,
                "michael@email.com",
                "michael123",
                "123456",
                "USER"
        );

        Puzzle puzzle = new Puzzle(
                1,
                usuario,
                "Puzzle Dragon Ball",
                "Puzzle de 500 piezas",
                "Media"
        );

        when(puzzleService.savePuzzle(puzzle))
                .thenReturn(puzzle);

        ResponseEntity<Puzzle> respuesta =
                puzzleController.crearPuzzle(puzzle);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(puzzle, respuesta.getBody());
    }

    @Test
    void obtenerPuzzle_retornaPuzzleCuandoExiste() {

        Usuario usuario = new Usuario(
                1,
                "Michael",
                25,
                "michael@email.com",
                "michael123",
                "123456",
                "USER"
        );

        Puzzle puzzle = new Puzzle(
                1,
                usuario,
                "Puzzle Dragon Ball",
                "Puzzle de 500 piezas",
                "Media"
        );

        when(puzzleService.getPuzzleId(1))
                .thenReturn(puzzle);

        ResponseEntity<Puzzle> respuesta =
                puzzleController.obtenerPuzzle(1);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(puzzle, respuesta.getBody());
    }

    @Test
    void obtenerPuzzle_retorna404CuandoNoExiste() {

        when(puzzleService.getPuzzleId(1))
                .thenReturn(null);

        ResponseEntity<Puzzle> respuesta =
                puzzleController.obtenerPuzzle(1);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void actualizarPuzzle_retorna200CuandoExiste() {

        Usuario usuario = new Usuario(
                1,
                "Goku",
                25,
                "kakaroto@email.com",
                "goku123",
                "123456",
                "USER"
        );

        Puzzle puzzle = new Puzzle(
                1,
                usuario,
                "Puzzle Dragon Ball",
                "Puzzle de 500 piezas",
                "Media"
        );

        when(puzzleService.updatePuzzle(any(Puzzle.class)))
                .thenReturn(puzzle);

        ResponseEntity<Puzzle> respuesta =
                puzzleController.actualizarPuzzle(1, puzzle);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
    }

    @Test
    void actualizarPuzzle_retorna404CuandoNoExiste() {

        Usuario usuario = new Usuario(
                1,
                "Goku",
                25,
                "kakaroto@email.com",
                "goku123",
                "123456",
                "USER"
        );

        Puzzle puzzle = new Puzzle(
                1,
                usuario,
                "Puzzle Dragon Ball",
                "Puzzle de 500 piezas",
                "Media"
        );

        when(puzzleService.updatePuzzle(any(Puzzle.class)))
                .thenReturn(null);

        ResponseEntity<Puzzle> respuesta =
                puzzleController.actualizarPuzzle(1, puzzle);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void eliminarPuzzle_retorna204() {

        ResponseEntity<Void> respuesta =
                puzzleController.eliminarPuzzle(1);

        verify(puzzleService).deletePuzzle(1);

        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }

    @Test
    void testError_lanzaRuntimeException() {

        assertThrows(
                RuntimeException.class,
                () -> puzzleController.testError()
        );
    }
}

