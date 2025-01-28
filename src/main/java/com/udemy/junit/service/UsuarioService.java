package com.udemy.junit.service;

import com.udemy.junit.domain.Usuario;
import com.udemy.junit.domain.exception.ValidationException;
import com.udemy.junit.service.repositories.UsuarioRepository;

import java.util.Optional;

public class UsuarioService {

    private UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario salvar(Usuario usuario) {
        repository.getUserByEmail(usuario.getEmail()).ifPresent( user -> {
            throw new ValidationException(String.format("Usuário %s já cadastrado!",usuario.getEmail()));
        });
        return repository.salvar(usuario);
    }

    public Optional<Usuario> getUsuarioPorEmail(String email) {
        return repository.getUserByEmail(email);
    }
}
