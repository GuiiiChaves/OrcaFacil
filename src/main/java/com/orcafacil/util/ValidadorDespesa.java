package com.orcafacil.util;

import com.orcafacil.exception.DespesaException;
import java.time.LocalDate;

public class ValidadorDespesa {
    
    private static final double VALOR_MINIMO = 0.01;
    private static final double VALOR_MAXIMO = 999999.99;
    
    public static void validarDescricao(String descricao) throws DespesaException {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new DespesaException("❌ Descrição não pode estar vazia!");
        }
        if (descricao.length() > 100) {
            throw new DespesaException("❌ Descrição não pode exceder 100 caracteres!");
        }
    }
    
    public static void validarCategoria(String categoria) throws DespesaException {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new DespesaException("❌ Categoria não pode estar vazia!");
        }
        if (categoria.length() > 50) {
            throw new DespesaException("❌ Categoria não pode exceder 50 caracteres!");
        }
    }
    
    public static void validarValor(double valor) throws DespesaException {
        if (valor < VALOR_MINIMO) {
            throw new DespesaException(String.format("❌ Valor deve ser maior que R$ %.2f!", VALOR_MINIMO));
        }
        if (valor > VALOR_MAXIMO) {
            throw new DespesaException(String.format("❌ Valor não pode exceder R$ %.2f!", VALOR_MAXIMO));
        }
    }
    
    public static void validarData(LocalDate data) throws DespesaException {
        if (data.isAfter(LocalDate.now())) {
            throw new DespesaException("❌ A data não pode ser futura!");
        }
    }
    
    public static void validarDespesaCompleta(String descricao, String categoria, 
                                               double valor, LocalDate data) throws DespesaException {
        validarDescricao(descricao);
        validarCategoria(categoria);
        validarValor(valor);
        validarData(data);
    }
}
