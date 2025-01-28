package com.udemy.junit.infra;

import com.udemy.junit.domain.Usuario;
import com.udemy.junit.service.repositories.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioMemoryRepository implements UsuarioRepository {

    private List<Usuario> usuarios;
    private Long currentId;

    public UsuarioMemoryRepository() {
        currentId = 0L;
        usuarios = new ArrayList<>();
        salvar(new Usuario(null,"Usuario #1", "user1@mail.com","123456"));
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        Usuario newUsuario = new Usuario(nextId(),usuario.getNome(),usuario.getEmail(),usuario.getSenha());
        usuarios.add(newUsuario);
        nextId();
        return newUsuario;
    }

    @Override
    public Optional<Usuario> getUserByEmail(String email) {
        return usuarios.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    private Long nextId(){
        return ++currentId;
    }
}
