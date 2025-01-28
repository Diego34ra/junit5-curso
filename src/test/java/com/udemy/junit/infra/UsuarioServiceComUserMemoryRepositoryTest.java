package com.udemy.junit.infra;

import com.udemy.junit.domain.Usuario;
import com.udemy.junit.domain.builders.UsuarioBuilder;
import com.udemy.junit.domain.exception.ValidationException;
import com.udemy.junit.service.UsuarioService;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioServiceComUserMemoryRepositoryTest {
    private static UsuarioService service = new UsuarioService(new UsuarioMemoryRepository());

    @Test
    @Order(1)
    public void salvar_deveSalvarUsuarioValido() {
        Usuario usuario = service.salvar(UsuarioBuilder.umUsuario().comId(null).agora());
        Assertions.assertNotNull(usuario.getId());
    }

    @Test
    @Order(2)
    public void salvar_deveRejeitarUsuarioExistente() {
        ValidationException ex = Assertions.assertThrows(ValidationException.class, () ->
                service.salvar(UsuarioBuilder.umUsuario().comId(null).agora()));
        Assertions.assertEquals("Usuário user@mail.com já cadastrado!",ex.getMessage());
    }
}