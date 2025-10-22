package com.orcafacil.service;

import com.orcafacil.model.Despesa;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class RelatorioServicoTest {

    @Test
    void deveCalcularEstatisticasCorretamente() {
        RelatorioServico r = new RelatorioServico();
        List<Despesa> despesas = List.of(
            new Despesa("Café", "Alimentação", 10),
            new Despesa("Almoço", "Alimentação", 20),
            new Despesa("Internet", "Serviços", 100)
        );

        Map<String, Object> stats = r.calcularEstatisticas(despesas);
        assertEquals(130.0, (double) stats.get("total"));
        assertEquals(3, stats.get("quantidade"));
        assertTrue((double) stats.get("media") > 0);
    }
}
