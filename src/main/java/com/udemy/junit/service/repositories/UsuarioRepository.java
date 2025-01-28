package com.udemy.junit.service.repositories;

import com.udemy.junit.domain.Usuario;

import java.util.Optional;

public interface UsuarioRepository {
    Usuario salvar (Usuario usuario);

    Optional<Usuario> getUserByEmail(String email);
}
