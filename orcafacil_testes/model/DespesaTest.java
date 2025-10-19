package com.orcafacil.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DespesaTest {

    @Test
    void deveCriarDespesaComValoresValidos() {
        Despesa d = new Despesa("Café", "Alimentação", 12.5);
        assertEquals("Café", d.getDescricao());
        assertEquals("Alimentação", d.getCategoria());
        assertEquals(12.5, d.getValor());
    }

    @Test
    void deveGerarResumoFormatado() {
        Despesa d = new Despesa("Internet", "Serviços", 99.9);
        assertTrue(d.toStringResumo().contains("Internet"));
    }

    @Test
    void deveLancarExcecaoParaValorNegativo() {
        assertThrows(Exception.class, () -> new Despesa("Erro", "Teste", -5));
    }
}
