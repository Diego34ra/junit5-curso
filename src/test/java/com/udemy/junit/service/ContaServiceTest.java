package com.udemy.junit.service;

import com.udemy.junit.domain.Conta;
import com.udemy.junit.domain.builders.ContaBuilder;
import com.udemy.junit.domain.exception.ValidationException;
import com.udemy.junit.external.ContaEvent;
import com.udemy.junit.service.repositories.ContaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static com.udemy.junit.external.ContaEvent.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

    @InjectMocks
    private ContaService service;

    @Mock
    private ContaRepository repository;

    @Mock
    private ContaEvent event;

    @Captor
    private ArgumentCaptor<Conta> contaCaptor;


    @Test
    void salvar_deveSalvarContaComSucesso() throws Exception {
        Conta contaParaSalvar = ContaBuilder.umaConta().comId(null).agora();

        when(repository.salvar(contaParaSalvar)).thenReturn(ContaBuilder.umaConta().agora());
        Mockito.doNothing().when(event).dispatch(ContaBuilder.umaConta().agora(), EventType.CREATED);

        Conta contaSalvada = service.salvar(contaParaSalvar);
        assertNotNull(contaSalvada.getId());

        verify(repository).salvar(contaCaptor.capture());
        assertNull(contaCaptor.getValue().getId());
    }

    @Test
    void salvar_deveRejeitarContaRepetida() {
        Conta contaParaSalvar = ContaBuilder.umaConta().comId(null).agora();

        when(repository.obterContasPorUsuario(contaParaSalvar.getUsuario().getId())).thenReturn(Collections.singletonList(ContaBuilder.umaConta().agora()));

        String mensagem = Assertions.assertThrows(ValidationException.class, () -> service.salvar(contaParaSalvar)).getMessage();

        Assertions.assertEquals("Usuário já possui uma conta com este nome", mensagem);
    }

    @Test
    void salvar_deveSalvarContasMesmoJaExistindoOutras() {
        Conta contaParaSalvar = ContaBuilder.umaConta().comId(null).agora();

        when(repository.obterContasPorUsuario(contaParaSalvar.getUsuario().getId())).thenReturn(Collections.singletonList(ContaBuilder.umaConta().comNome("Outra Conta").agora()));
        when(repository.salvar(contaParaSalvar)).thenReturn(ContaBuilder.umaConta().agora());

        Conta contaSalvada = null;
        try {
            contaSalvada = service.salvar(contaParaSalvar);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertNotNull(contaSalvada.getId());
    }

    @Test
    void salvar_naoDeveManterContaSemEvento() {
        Conta contaParaSalvar = ContaBuilder.umaConta().comId(null).agora();
        Conta contaSalva = ContaBuilder.umaConta().agora();

        when(repository.salvar(contaParaSalvar)).thenReturn(contaSalva);
        try {
            Mockito.doThrow(new Exception("Falha catastrófica")).when(event).dispatch(contaSalva, EventType.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String mensagem = Assertions.assertThrows(Exception.class, () -> service.salvar(contaParaSalvar)).getMessage();

        Assertions.assertEquals("Falha na criação da conta, tente novamente", mensagem);

        Mockito.verify(repository).delete(contaSalva);
    }
}