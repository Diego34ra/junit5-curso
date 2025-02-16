package com.udemy.junit.external;

import com.udemy.junit.domain.Conta;

public interface ContaEvent {

    public enum EventType {CREATED, UPDATED, DELETED}

    void dispatch(Conta conta, EventType type) throws Exception;
}
