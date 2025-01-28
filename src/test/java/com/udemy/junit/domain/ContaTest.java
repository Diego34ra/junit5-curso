package com.udemy.junit.domain;

import com.udemy.junit.domain.builders.ContaBuilder;
import com.udemy.junit.domain.builders.UsuarioBuilder;
import com.udemy.junit.domain.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ContaTest {

    @Test
    void criar_deveCriarContaValida(){
        Conta conta = ContaBuilder.umaConta().agora();

        Assertions.assertAll("Conta",
                () -> Assertions.assertEquals(1L, conta.getId()),
                () -> Assertions.assertEquals("Conta Válida", conta.getNome()),
                () -> Assertions.assertEquals(UsuarioBuilder.umUsuario().agora(), conta.getUsuario())
        );
    }

    @ParameterizedTest(name = "[{index}] - {3}")
    @MethodSource(value = "dataProvider")
    void criar_deveRejeitarContaInvalida(Long id, String nome, Usuario usuario, String mensagem){
        String errorMessage = Assertions.assertThrows(ValidationException.class, () -> ContaBuilder.umaConta().comId(id).comNome(nome).comUsuario(usuario).agora()).getMessage();

        Assertions.assertEquals(mensagem,errorMessage);
    }

    private static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of(1L, null, UsuarioBuilder.umUsuario().agora(), "Nome é obrigatório."),
                Arguments.of(1L, "Conta Válida", null, "Usuário é obrigatório.")
        );
    }
}
