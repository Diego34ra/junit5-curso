package com.udemy.junit.service;

import com.udemy.junit.domain.Usuario;
import com.udemy.junit.domain.builders.UsuarioBuilder;
import com.udemy.junit.domain.exception.ValidationException;
import com.udemy.junit.service.repositories.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService service;

    @Mock
    private UsuarioRepository repository;

    @Test
    void criar_deveRetornarEmptyQuandoUsuarioInexistente() {
        when(service.getUsuarioPorEmail("mail@mail.com")).thenReturn(Optional.empty());

        Optional<Usuario> usuario = service.getUsuarioPorEmail("mail@mail.com");
        assertTrue(usuario.isEmpty());
    }

    @Test
    void criar_deveRetornarUsuarioPorEmail() {
        when(repository.getUserByEmail("mail@mail.com"))
                .thenReturn(Optional.of(UsuarioBuilder.umUsuario().agora()), (Optional<Usuario>[]) null);

        Optional<Usuario> usuario = service.getUsuarioPorEmail("mail@mail.com");
        assertTrue(usuario.isPresent());
        usuario = service.getUsuarioPorEmail("mail@mail.com");
        assertNull(usuario);
        usuario = service.getUsuarioPorEmail("mail123@mail.com");
        assertTrue(usuario.isEmpty());

        verify(repository, Mockito.times(2)).getUserByEmail("mail@mail.com");
        verify(repository, Mockito.times(1)).getUserByEmail("mail123@mail.com");
        verify(repository, never()).getUserByEmail("mail1234@mail.com");
    }

    @Test
    void criar_deveSalvarUsuarioComSucesso() {
        Usuario usuarioParaSalvar = UsuarioBuilder.umUsuario().agora();

        when(repository.salvar(usuarioParaSalvar)).thenReturn(UsuarioBuilder.umUsuario().agora());

        Usuario usuario = service.salvar(usuarioParaSalvar);
        assertNotNull(usuario.getId());

        verify(repository).getUserByEmail(usuarioParaSalvar.getEmail());
    }

    @Test
    void criar_deveRejeitarUsuarioExistente() {
        Usuario usuarioParaSalvar = UsuarioBuilder.umUsuario().comId(null).agora();

        when(repository.getUserByEmail(usuarioParaSalvar.getEmail())).thenReturn(Optional.of(UsuarioBuilder.umUsuario().agora()));

        ValidationException ex = Assertions.assertThrows(ValidationException.class, () -> service.salvar(usuarioParaSalvar));
        assertEquals("Usuário user@mail.com já cadastrado!", ex.getMessage());

        verify(repository).getUserByEmail(usuarioParaSalvar.getEmail());
        verify(repository,never()).salvar(usuarioParaSalvar);
    }
}