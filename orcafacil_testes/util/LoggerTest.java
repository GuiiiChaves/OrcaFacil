package com.orcafacil.util;

import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {

    private final ByteArrayOutputStream saida = new ByteArrayOutputStream();

    @BeforeEach
    void redirecionarSaida() {
        System.setOut(new PrintStream(saida));
        System.setErr(new PrintStream(saida));
    }

    @AfterEach
    void restaurarSaida() {
        System.setOut(System.out);
        System.setErr(System.err);
    }

    @Test
    void deveRegistrarMensagemDeInfo() {
        Logger.logInfo("Teste info");
        assertTrue(saida.toString().contains("Teste info"));
    }

    @Test
    void deveRegistrarMensagemDeErro() {
        Logger.logErro("Falha ao processar", new Exception("Exceção simulada"));
        String log = saida.toString();
        assertTrue(log.contains("Falha") || log.contains("Exceção"));
    }
}
