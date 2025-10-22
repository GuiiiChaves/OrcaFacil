package com.orcafacil.service;

import com.orcafacil.exception.OrcamentoExcedidoException;
import com.orcafacil.model.Despesa;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GerenciadorOrcamentoTest {

    @Test
    void deveDetectarOrcamentoExcedido() {
        GerenciadorOrcamento g = new GerenciadorOrcamento();
        g.setOrcamentoMensal(100);
        List<Despesa> despesas = List.of(new Despesa("Teste", "Geral", 90));
        assertThrows(OrcamentoExcedidoException.class,
            () -> g.validarOrcamento(despesas, 20));
    }

    @Test
    void deveEmitirAlertaAoAtingir80PorCento() {
        GerenciadorOrcamento g = new GerenciadorOrcamento();
        g.setOrcamentoMensal(1000);
        List<Despesa> despesas = List.of(new Despesa("Compra", "Mercado", 850));
        assertTrue(g.verificarAlerta(despesas));
    }

    @Test
    void deveRetornarStatusCorreto() {
        GerenciadorOrcamento g = new GerenciadorOrcamento();
        g.setOrcamentoMensal(1000);
        String status = g.statusOrcamento(List.of(new Despesa("Luz", "Casa", 200)));
        assertTrue(status.contains("Orçamento"));
    }
}
