package com.duoc.fluxpuzzles.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad JPA que representa un puzzle en la base de datos.
 * 
 * Tabla: puzzles
 * Responsabilidades:
 * - Almacenar información del puzzle (nombre, descripción, dificultad)
 * - Mantener la relación con su creador (Usuario)
 * 
 * Relación:
 * - OneToOne con Usuario: cada puzzle pertenece a exactamente un usuario
 * - La columna "usuario_id" es clave foránea que referencia a usuarios.id
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "puzzles")
public class Puzzle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /** Nombre o título del puzzle */
    private String nombrePuzzle;
    
    /** Descripción detallada del puzzle */
    private String descripcion;
    
    /** Nivel de dificultad (fácil, medio, difícil, etc.) */
    private String dificultad;
}
