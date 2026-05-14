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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity

@Table (name = "usuarios")

public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "debe ingresar un nombre")
    @Column(unique = true)
    private String nombre;

    @OneToMany
    private List<RegistroJuego> partidasJugadas;

    @NotNull(message = "debe ingresar su edad")
    private int edad;

    @Column(unique = true)
    @NotBlank(message = "debe ingresar su correo")
    private String correo;
    

    @NotBlank(message = "debe ingresar su nombre de usuario")
    private String nombreUsuario;
    
    @Column(name = "activo")
    private boolean activo = true;
}
