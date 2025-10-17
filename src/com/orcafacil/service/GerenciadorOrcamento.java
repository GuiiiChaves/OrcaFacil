package com.orcafacil.service;

import com.orcafacil.exception.OrcamentoExcedidoException;
import com.orcafacil.model.Despesa;
import java.util.List;

public class GerenciadorOrcamento {
    
    private double orcamentoMensal = 0;
    private static final double PERCENTUAL_ALERTA = 0.80;
    
    public void setOrcamentoMensal(double valor) {
        this.orcamentoMensal = valor;
    }
    
    public double getOrcamentoMensal() {
        return orcamentoMensal;
    }
    
    public boolean temOrcamento() {
        return orcamentoMensal > 0;
    }
    
    public double calcularTotalDespesas(List<Despesa> despesas) {
        return despesas.stream()
            .mapToDouble(Despesa::getValor)
            .sum();
    }
    
    public void validarOrcamento(List<Despesa> despesas, double novaQuantia) 
            throws OrcamentoExcedidoException {
        
        if (orcamentoMensal <= 0) {
            return;
        }
        
        double totalAtual = calcularTotalDespesas(despesas);
        double totalComNova = totalAtual + novaQuantia;
        
        if (totalComNova > orcamentoMensal) {
            throw new OrcamentoExcedidoException(orcamentoMensal, totalComNova);
        }
    }
    
    public boolean verificarAlerta(List<Despesa> despesas) {
        if (orcamentoMensal <= 0) return false;
        
        double total = calcularTotalDespesas(despesas);
        double limiteAlerta = orcamentoMensal * PERCENTUAL_ALERTA;
        
        return total >= limiteAlerta;
    }
    
    public String statusOrcamento(List<Despesa> despesas) {
        if (orcamentoMensal <= 0) {
            return "❌ Nenhum orçamento definido";
        }
        
        double total = calcularTotalDespesas(despesas);
        double percentualUsado = (total / orcamentoMensal) * 100;
        double disponivel = orcamentoMensal - total;
        
        String emoji = percentualUsado >= 100 ? "🔴" : 
                       percentualUsado >= 80 ? "🟡" : "🟢";
        
        return String.format("%s Orçamento: R$ %.2f | Gasto: R$ %.2f | Disponível: R$ %.2f | %.1f%%",
            emoji, orcamentoMensal, total, disponivel, percentualUsado);
    }
    
    public String paraArquivo() {
        return "ORCAMENTO:" + orcamentoMensal;
    }
    
    public static GerenciadorOrcamento deArquivo(String linha) {
        GerenciadorOrcamento gerenciador = new GerenciadorOrcamento();
        try {
            if (linha.startsWith("ORCAMENTO:")) {
                double valor = Double.parseDouble(linha.substring(10));
                gerenciador.setOrcamentoMensal(valor);
            }
        } catch (Exception e) {
            // Ignora erros
        }
        return gerenciador;
    }
}
