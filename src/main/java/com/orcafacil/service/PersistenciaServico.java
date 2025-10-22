package com.orcafacil.service;

import com.orcafacil.model.Despesa;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaServico {
    
    private final String caminhoArquivo;
    
    public PersistenciaServico(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }
    
    public List<Despesa> carregar() {
        List<Despesa> despesas = new ArrayList<>();
        File arquivo = new File(caminhoArquivo);
        
        if (!arquivo.exists()) {
            return despesas;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("ORCAMENTO:")) {
                    continue;
                }
                
                try {
                    Despesa despesa = Despesa.deArquivo(linha);
                    despesas.add(despesa);
                } catch (IllegalArgumentException e) {
                    System.out.println("⚠️  Linha ignorada: " + linha);
                }
            }
            
            if (!despesas.isEmpty()) {
                System.out.printf("✅ %d despesas carregadas.\n", despesas.size());
            }
            
        } catch (IOException e) {
            System.err.println("❌ Erro ao carregar dados: " + e.getMessage());
        }
        
        return despesas;
    }
    
    public void salvar(List<Despesa> despesas, GerenciadorOrcamento orcamento) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(caminhoArquivo))) {
            
            if (orcamento != null && orcamento.temOrcamento()) {
                writer.println(orcamento.paraArquivo());
            }
            
            for (Despesa despesa : despesas) {
                writer.println(despesa.paraArquivo());
            }
            
            System.out.printf("💾 %d despesas salvas.\n", despesas.size());
            
        } catch (IOException e) {
            System.err.println("❌ Erro ao salvar dados: " + e.getMessage());
        }
    }
    
    public GerenciadorOrcamento carregarOrcamento() {
        GerenciadorOrcamento orcamento = new GerenciadorOrcamento();
        File arquivo = new File(caminhoArquivo);
        
        if (!arquivo.exists()) {
            return orcamento;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String primeiraLinha = reader.readLine();
            if (primeiraLinha != null && primeiraLinha.startsWith("ORCAMENTO:")) {
                orcamento = GerenciadorOrcamento.deArquivo(primeiraLinha);
            }
        } catch (IOException e) {
            System.err.println("⚠️  Erro ao carregar orçamento: " + e.getMessage());
        }
        
        return orcamento;
    }
}
