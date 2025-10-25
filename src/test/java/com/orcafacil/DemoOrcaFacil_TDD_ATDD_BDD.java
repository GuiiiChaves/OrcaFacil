package com.orcafacil.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Demonstração dos testes do OrçaFácil aplicando TDD, ATDD e BDD.
 * Combina trechos reais de testes das classes:
 * - Despesa
 * - GerenciadorOrcamento
 * - RelatorioServico
 * 
 * Cada parte mostra como o mesmo sistema é testado em níveis diferentes.
 */

public class DemoOrcaFacil_TDD_ATDD_BDD {

    /* =========================
     *  CÓDIGO DE PRODUÇÃO SIMPLIFICADO
     * ========================= */
    static class Despesa {
        String nome;
        String categoria;
        double valor;

        Despesa(String nome, String categoria, double valor) {
            if (valor <= 0)
                throw new IllegalArgumentException("Valor inválido");
            this.nome = nome;
            this.categoria = categoria;
            this.valor = valor;
        }
    }

    static class GerenciadorOrcamento {
        double limiteMensal;

        void setOrcamentoMensal(double valor) {
            this.limiteMensal = valor;
        }

        void validarOrcamento(List<Despesa> despesas) {
            double total = despesas.stream().mapToDouble(d -> d.valor).sum();
            if (total > limiteMensal)
                throw new IllegalStateException("Orçamento excedido!");
        }
    }

    static class RelatorioServico {
        double calcularTotal(List<Despesa> despesas) {
            return despesas.stream().mapToDouble(d -> d.valor).sum();
        }
    }

    /* =========================
     *  TDD – Test Driven Development
     * =========================
     * Testes unitários que garantem o comportamento básico de cada classe.
     */
    @Test
    void tdd_despesaNaoDeveAceitarValorNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new Despesa("Transporte", "Geral", -50));
    }

    @Test
    void tdd_relatorioDeveCalcularTotalCorretamente() {
        RelatorioServico r = new RelatorioServico();
        List<Despesa> despesas = List.of(
                new Despesa("Aluguel", "Moradia", 1000),
                new Despesa("Energia", "Contas", 300)
        );
        assertEquals(1300, r.calcularTotal(despesas));
    }

    /* =========================
     *  ATDD – Acceptance Test Driven Development
     * =========================
     * Representa critérios de aceite reais do OrçaFácil.
     */
    @Test
    void atdd_deveDetectarOrcamentoExcedido() {
        GerenciadorOrcamento g = new GerenciadorOrcamento();
        g.setOrcamentoMensal(500);

        List<Despesa> despesas = List.of(
                new Despesa("Supermercado", "Alimentação", 300),
                new Despesa("Combustível", "Transporte", 250)
        );

        assertThrows(IllegalStateException.class, () -> g.validarOrcamento(despesas));
    }

    @Test
    void atdd_deveValidarOrcamentoDentroDoLimite() {
        GerenciadorOrcamento g = new GerenciadorOrcamento();
        g.setOrcamentoMensal(600);

        List<Despesa> despesas = List.of(
                new Despesa("Supermercado", "Alimentação", 300),
                new Despesa("Transporte", "Combustível", 200)
        );

        assertDoesNotThrow(() -> g.validarOrcamento(despesas));
    }

    /* =========================
     *  BDD – Behavior Driven Development
     * =========================
     * Teste descritivo usando Given / When / Then
     * inspirado em Gherkin, simulando o comportamento esperado do usuário.
     */
    @Test
    void bdd_cenarioDeOrcamentoExcedido() {
        // Given
        GerenciadorOrcamento g = new GerenciadorOrcamento();
        g.setOrcamentoMensal(400);
        List<Despesa> despesas = new ArrayList<>();
        despesas.add(new Despesa("Lazer", "Entretenimento", 200));
        despesas.add(new Despesa("Restaurante", "Alimentação", 250));

        // When
        Exception e = assertThrows(IllegalStateException.class, () -> g.validarOrcamento(despesas));

        // Then
        assertEquals("Orçamento excedido!", e.getMessage());
    }

    @Test
    void bdd_cenarioDeCalculoCorretoNoRelatorio() {
        // Given
        RelatorioServico relatorio = new RelatorioServico();
        List<Despesa> despesas = List.of(
                new Despesa("Internet", "Serviços", 100),
                new Despesa("Água", "Contas", 80)
        );

        // When
        double total = relatorio.calcularTotal(despesas);

        // Then
        assertEquals(180, total, "O relatório deve somar corretamente as despesas");
    }
}
