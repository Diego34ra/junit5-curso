package com.udemy.junit.infra;

import com.udemy.junit.domain.Usuario;
import com.udemy.junit.domain.builders.UsuarioBuilder;
import com.udemy.junit.service.repositories.UsuarioRepository;

import java.util.Optional;

public class UsuarioDummyRepository implements UsuarioRepository {
    @Override
    public Usuario salvar(Usuario usuario) {
        return UsuarioBuilder.umUsuario()
                .comNome(usuario.getNome())
                .comEmail(usuario.getEmail())
                .comSenha(usuario.getSenha())
                .agora();
    }

    @Override
    public Optional<Usuario> getUserByEmail(String email) {
        if("user@mail.com".equals(email))
            return Optional.of(UsuarioBuilder.umUsuario().comEmail(email).agora());
        return Optional.empty();
    }
}
