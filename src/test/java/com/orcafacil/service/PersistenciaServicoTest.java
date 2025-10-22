package com.orcafacil.service;

import com.orcafacil.model.Despesa;
import org.junit.jupiter.api.*;
import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PersistenciaServicoTest {

    @TempDir
    File tempDir;

    @Test
    void deveSalvarECarregarDespesas() {
        File arq = new File(tempDir, "dados.txt");
        PersistenciaServico p = new PersistenciaServico(arq.getAbsolutePath());
        List<Despesa> lista = List.of(new Despesa("Água", "Casa", 50.0));

        p.salvar(lista, new GerenciadorOrcamento());
        List<Despesa> carregadas = p.carregar();

        assertFalse(carregadas.isEmpty());
    }

    @Test
    void deveCriarArquivoSeNaoExistir() {
        File arq = new File(tempDir, "novo.txt");
        PersistenciaServico p = new PersistenciaServico(arq.getAbsolutePath());
        assertDoesNotThrow(p::carregar);
    }
}
