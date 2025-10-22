package com.orcafacil.exception;

public class DespesaException extends Exception {
    
    public DespesaException(String mensagem) {
        super(mensagem);
    }
    
    public DespesaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
