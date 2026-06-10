package com.duoc.fluxpuzzles.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.duoc.fluxpuzzles.Model.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String username);
}