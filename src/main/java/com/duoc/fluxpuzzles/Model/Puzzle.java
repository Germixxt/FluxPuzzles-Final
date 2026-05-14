package com.duoc.fluxpuzzles.Model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity

@Table(name = "puzzles")

public class Puzzle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "id_usuario_creador", referencedColumnName = "id")
    public Usuario creador;

    @OneToMany
    private List<Solucion> soluciones;

    @NotBlank(message = "debe ingresar el nombre del puzzle")
    @Column(unique = true)
    private String nombrePuzzle;

    @NotBlank(message = "debe colocar una descripcion")
    private String descripcion;

    @NotBlank(message = "debe ingresar la dificultad")
    private String dificultad;


    private boolean activo = true; // Atributo para el borrado lógico
}
