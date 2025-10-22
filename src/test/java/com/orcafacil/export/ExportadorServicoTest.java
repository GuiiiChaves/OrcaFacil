package com.orcafacil.export;

import com.orcafacil.model.Despesa;
import org.junit.jupiter.api.*;
import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ExportadorServicoTest {

    @TempDir
    File tempDir;

    @Test
    void deveExportarCSV() throws Exception {
        ExportadorServico e = new ExportadorServico(tempDir.getAbsolutePath());
        String caminho = e.exportarCSV(List.of(new Despesa("Água", "Casa", 50)));
        assertTrue(new File(caminho).exists());
    }

    @Test
    void deveExportarJSON() throws Exception {
        ExportadorServico e = new ExportadorServico(tempDir.getAbsolutePath());
        String caminho = e.exportarJSON(List.of(new Despesa("Luz", "Casa", 80)));
        assertTrue(new File(caminho).exists());
    }
}
