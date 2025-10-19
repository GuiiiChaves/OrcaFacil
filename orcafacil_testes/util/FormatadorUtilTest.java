package com.orcafacil.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FormatadorUtilTest {

    @Test
    void deveRepetirCaracterCorretamente() {
        String result = FormatadorUtil.repeat("-", 5);
        assertEquals("-----", result);
    }

    @Test
    void deveRetornarStringVaziaQuandoQuantidadeZero() {
        String result = FormatadorUtil.repeat("*", 0);
        assertEquals("", result);
    }

    @Test
    void deveFormatarMoedaCorretamente() {
        String moeda = FormatadorUtil.formatarMoeda(1250.5);
        assertTrue(moeda.contains("1.250") || moeda.contains("R$"));
    }
}
