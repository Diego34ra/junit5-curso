package com.udemy.junit.service.repositories;

import com.udemy.junit.domain.Conta;

import java.util.List;

public interface ContaRepository {

    Conta salvar (Conta conta);

    List<Conta> obterContasPorUsuario(Long usuarioId);

    void delete(Conta conta);
}
