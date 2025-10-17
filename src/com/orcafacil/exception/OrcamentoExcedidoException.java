package com.orcafacil.exception;

public class OrcamentoExcedidoException extends DespesaException {
    
    private final double limiteOrcamento;
    private final double totalAtual;
    
    public OrcamentoExcedidoException(double limiteOrcamento, double totalAtual) {
        super(String.format("⚠️  ATENÇÃO: Orçamento excedido! Limite: R$ %.2f | Total atual: R$ %.2f", 
            limiteOrcamento, totalAtual));
        this.limiteOrcamento = limiteOrcamento;
        this.totalAtual = totalAtual;
    }
    
    public double getLimiteOrcamento() {
        return limiteOrcamento;
    }
    
    public double getTotalAtual() {
        return totalAtual;
    }
}
