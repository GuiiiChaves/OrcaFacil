package com.orcafacil.service;

import com.orcafacil.model.Despesa;
import com.orcafacil.util.FormatadorUtil;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class RelatorioServico {
    
    public Map<String, Double> totalPorCategoria(List<Despesa> despesas) {
        return despesas.stream()
            .collect(Collectors.groupingBy(
                Despesa::getCategoria,
                Collectors.summingDouble(Despesa::getValor)
            ))
            .entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }
    
    public List<Despesa> filtrarPorPeriodo(List<Despesa> despesas, 
                                           LocalDate dataInicio, LocalDate dataFim) {
        return despesas.stream()
            .filter(d -> !d.getData().isBefore(dataInicio) && 
                         !d.getData().isAfter(dataFim))
            .collect(Collectors.toList());
    }
    
    public List<Despesa> filtrarMesAtual(List<Despesa> despesas) {
        YearMonth mesAtual = YearMonth.now();
        return despesas.stream()
            .filter(d -> YearMonth.from(d.getData()).equals(mesAtual))
            .collect(Collectors.toList());
    }
    
    public List<Despesa> filtrarUltimosDias(List<Despesa> despesas, int dias) {
        LocalDate dataLimite = LocalDate.now().minusDays(dias);
        return filtrarPorPeriodo(despesas, dataLimite, LocalDate.now());
    }
    
    public Map<String, Object> calcularEstatisticas(List<Despesa> despesas) {
        if (despesas.isEmpty()) {
            return Collections.emptyMap();
        }
        
        List<Double> valores = despesas.stream()
            .map(Despesa::getValor)
            .sorted()
            .collect(Collectors.toList());
        
        double total = valores.stream().mapToDouble(Double::doubleValue).sum();
        double media = total / valores.size();
        double minimo = valores.get(0);
        double maximo = valores.get(valores.size() - 1);
        double mediana = calcularMediana(valores);
        
        Map<String, Double> porCategoria = totalPorCategoria(despesas);
        String categoriaMaior = porCategoria.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("N/A");
        
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("total", total);
        stats.put("quantidade", despesas.size());
        stats.put("media", media);
        stats.put("minimo", minimo);
        stats.put("maximo", maximo);
        stats.put("mediana", mediana);
        stats.put("categoriaMaior", categoriaMaior);
        
        return stats;
    }
    
    private double calcularMediana(List<Double> valores) {
        int tamanho = valores.size();
        if (tamanho % 2 == 1) {
            return valores.get(tamanho / 2);
        } else {
            return (valores.get(tamanho / 2 - 1) + valores.get(tamanho / 2)) / 2;
        }
    }
    
    public String gerarGraficoCategoria(List<Despesa> despesas) {
        if (despesas.isEmpty()) {
            return "Sem dados para gerar gráfico.";
        }
        
        Map<String, Double> porCategoria = totalPorCategoria(despesas);
        double total = porCategoria.values().stream().mapToDouble(Double::doubleValue).sum();
        
        StringBuilder grafico = new StringBuilder();
        grafico.append("\n📊 GRÁFICO DE GASTOS POR CATEGORIA\n");
        grafico.append(FormatadorUtil.repeat("─", 60)).append("\n");
        
        for (Map.Entry<String, Double> entry : porCategoria.entrySet()) {
            String categoria = entry.getKey();
            double valor = entry.getValue();
            double percentual = (valor / total) * 100;
            int barras = (int) (percentual / 2);
            
            String barra = FormatadorUtil.repeat("█", Math.max(0, barras));
            grafico.append(String.format("%-15s │ %s %.1f%% (R$ %.2f)\n", 
                categoria, barra, percentual, valor));
        }
        
        grafico.append(FormatadorUtil.repeat("─", 60)).append("\n");
        grafico.append(String.format("TOTAL: R$ %.2f\n", total));
        
        return grafico.toString();
    }
}
