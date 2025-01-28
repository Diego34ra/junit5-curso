package com.udemy.junit.domain;

import com.udemy.junit.domain.builders.UsuarioBuilder;
import com.udemy.junit.domain.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Domínio: Usuário")
class UsuarioTest {

    @Test
    void criar_deveCriarUsuarioValido(){
        Usuario usuario = UsuarioBuilder.umUsuario().agora();

        assertAll("Usuario",
                () -> assertEquals(1L,usuario.getId()),
                () -> assertEquals("user@mail.com",usuario.getEmail()),
                () -> assertEquals("123456",usuario.getSenha())
        );

//        assertEquals(1L,usuario.getId());
//        assertEquals("user@mail.com",usuario.getEmail());
//        assertEquals("123456",usuario.getSenha());

    }

    @Test
    void criar_deveRejeitarUsuarioSemNome(){
        ValidationException ex = Assertions.assertThrows(ValidationException.class,() -> UsuarioBuilder.umUsuario().comNome(null).agora());

        assertEquals("Nome é obrigatório.",ex.getMessage());
    }

    @Test
    void criar_deveRejeitarUsuarioSemEmail(){
        ValidationException ex = Assertions.assertThrows(ValidationException.class,() -> UsuarioBuilder.umUsuario().comEmail(null).agora());

        assertEquals("Email é obrigatório.",ex.getMessage());
    }

    @Test
    void criar_deveRejeitarUsuarioSemSenha(){
        ValidationException ex = Assertions.assertThrows(ValidationException.class,() -> UsuarioBuilder.umUsuario().comSenha(null).agora());

        assertEquals("Senha é obrigatória.",ex.getMessage());
    }

    @ParameterizedTest(name = "[{index}] - {4}")
    @CsvFileSource(files = "src/test/resources/campoObrigatoriosUsuario.csv", nullValues = "NULL", numLinesToSkip = 1)
//    @CsvSource(value = {
//            "1, NULL, user@email.com, 123456,Nome é obrigatório.",
//            "1, Nome Usuario, NULL, 123456,Email é obrigatório.",
//            "1, Nome Usuario, user@email.com, NULL,Senha é obrigatória."
//
//    }, nullValues = "NULL")
    public void cirar_deveValidarCamposObrigatorios(Long id, String nome, String email, String senha, String mensagem){
        ValidationException ex = Assertions.assertThrows(ValidationException.class,() -> UsuarioBuilder.umUsuario().comId(id).comNome(nome).comEmail(email).comSenha(senha).agora());
        assertEquals(mensagem,ex.getMessage());
    }

}