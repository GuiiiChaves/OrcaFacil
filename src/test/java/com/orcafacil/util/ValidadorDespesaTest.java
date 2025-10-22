package com.orcafacil.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidadorDespesaTest {

    @Test
    void deveValidarDescricaoCorreta() {
        assertDoesNotThrow(() -> ValidadorDespesa.validarDescricao("Compra mensal"));
    }

    @Test
    void deveLancarErroSeDescricaoVazia() {
        assertThrows(Exception.class, () -> ValidadorDespesa.validarDescricao(""));
    }

    @Test
    void deveValidarCategoriaCorreta() {
        assertDoesNotThrow(() -> ValidadorDespesa.validarCategoria("Alimentação"));
    }

    @Test
    void deveLancarErroSeCategoriaVazia() {
        assertThrows(Exception.class, () -> ValidadorDespesa.validarCategoria(" "));
    }

    @Test
    void deveLancarErroSeValorInvalido() {
        assertThrows(Exception.class, () -> ValidadorDespesa.validarValor(0));
    }

    @Test
    void deveAceitarValorPositivo() {
        assertDoesNotThrow(() -> ValidadorDespesa.validarValor(150.75));
    }
}
