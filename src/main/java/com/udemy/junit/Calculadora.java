package com.udemy.junit;

public class Calculadora {

    public int somar(int a, int b){
        return a + b;
    }

    public float dividir(int a, int b){
        if(b == 0)
            throw new ArithmeticException("Erro ao efetuar a divisão com denominador zero.");
        return (float) a / b;
    }

    public static void main(String[] args) {
        Calculadora calculadora = new Calculadora();
        System.out.println(calculadora.somar(2,3));
    }
}
