package com.duoc.fluxpuzzles.Repository;

import com.duoc.fluxpuzzles.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNombreUsuarioAndActivoTrue(String nombreUsuario);

    Optional<Usuario> findByCorreoAndActivoTrue(String correo);

    Optional<Usuario> findByIdAndActivoTrue(Integer id);
    
    boolean existsByNombreUsuario(String nombreUsuario);
    
    boolean existsByCorreo(String correo);
 
    List<Usuario> findByActivoTrue();
}