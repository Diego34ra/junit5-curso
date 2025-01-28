package com.udemy.junit.service;

import com.udemy.junit.domain.Conta;
import com.udemy.junit.domain.exception.ValidationException;
import com.udemy.junit.external.ContaEvent;
import com.udemy.junit.service.repositories.ContaRepository;

import java.util.List;

import static com.udemy.junit.external.ContaEvent.*;

public class ContaService {

    private ContaRepository repository;
    private ContaEvent event;

    public ContaService(ContaRepository repository, ContaEvent event) {
        this.repository = repository;
        this.event = event;
    }

    public Conta salvar(Conta conta){
        List<Conta> contas = repository.obterContasPorUsuario(conta.getUsuario().getId());
        contas.stream().forEach(contaExistente -> {
            if(conta.getNome().equalsIgnoreCase(contaExistente.getNome()))
                throw new ValidationException("Usuário já possui uma conta com este nome");
        });
        Conta contaPersistida = repository.salvar(conta);
        try {
            event.dispatch(contaPersistida, EventType.CREATED);
        } catch (Exception e) {
            repository.delete(contaPersistida);
            throw new RuntimeException("Falha na criação da conta, tente novamente");
        }
        return contaPersistida;
    }
}
