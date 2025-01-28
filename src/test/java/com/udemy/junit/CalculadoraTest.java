package com.udemy.junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;


class CalculadoraTest {

    Calculadora calculadora = new Calculadora();

    @Test
    void somar() {
        Assertions.assertEquals(3, calculadora.somar(1, 2));
    }

    @Test
    void dividir_deveRetornarNumeroInteiro() {
        float resultado = calculadora.dividir(6,2);

        Assertions.assertEquals(3 ,resultado);
    }

    @Test
    void dividir_deveRetornarNumeroNegativo() {
        float resultado = calculadora.dividir(6,-2);

        Assertions.assertEquals(-3 ,resultado);
    }

    @Test
    void dividir_deveRetornarNumeroDecimal() {
        float resultado = calculadora.dividir(10,3);

        Assertions.assertEquals(3.33 , resultado, 0.01);
    }

    @Test
    void dividir_deveRetornarZeroComNumeradorZero() {
        float resultado = calculadora.dividir(0,2);

        Assertions.assertEquals(0 , resultado);
    }

    @Test
    void dividir_deveExplodirComDenominadorZero() {
        Assertions.assertThrows(ArithmeticException.class, () -> calculadora.dividir(10, 0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Teste1","Teste2"})
    public void testStrings(String param) {
        System.out.println(param);
        Assertions.assertNotNull(param);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "6, 2, 3",
            "6, -2, -3",
            "10, 3, 3.3333333",
            "0, 2, 0"
    })
    public void dividir_deveDividirCorretamente(int num, int den, float res) {
        float resultado = calculadora.dividir(num,den);
        Assertions.assertEquals(res,resultado);
    }
}