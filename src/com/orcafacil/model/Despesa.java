package com.orcafacil.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Despesa {
    
    private static int proximoId = 1;
    private static final DateTimeFormatter FORMATO_DATA = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    private final int id;
    private String descricao;
    private String categoria;
    private double valor;
    private LocalDate data;
    
    public Despesa(String descricao, String categoria, double valor) {
        this(proximoId++, descricao, categoria, valor, LocalDate.now());
    }
    
    public Despesa(int id, String descricao, String categoria, double valor, LocalDate data) {
        this.id = id;
        this.descricao = descricao;
        this.categoria = categoria;
        this.valor = valor;
        this.data = data;
        
        if (id >= proximoId) {
            proximoId = id + 1;
        }
    }
    
    public int getId() { return id; }
    public String getDescricao() { return descricao; }
    public String getCategoria() { return categoria; }
    public double getValor() { return valor; }
    public LocalDate getData() { return data; }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao != null ? descricao.trim() : "";
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria != null ? categoria.trim() : "Outros";
    }
    
    public void setValor(double valor) {
        this.valor = Math.max(0, valor);
    }
    
    public String paraArquivo() {
        return String.format("%d;%s;%s;%.2f;%s", 
            id, descricao, categoria, valor, data.format(FORMATO_DATA));
    }
    
    public static Despesa deArquivo(String linha) throws IllegalArgumentException {
        try {
            String[] dados = linha.split(";");
            if (dados.length != 5) {
                throw new IllegalArgumentException("Formato inválido");
            }
            
            int id = Integer.parseInt(dados[0]);
            String descricao = dados[1];
            String categoria = dados[2];
            double valor = Double.parseDouble(dados[3]);
            LocalDate data = LocalDate.parse(dados[4], FORMATO_DATA);
            
            return new Despesa(id, descricao, categoria, valor, data);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao desserializar despesa: " + linha, e);
        }
    }
    
    public String toStringResumo() {
        return String.format("%-30s | %-15s | R$ %8.2f | %s", 
            descricao, categoria, valor, data.format(FORMATO_DATA));
    }
    
    @Override
    public String toString() {
        return String.format("[%d] %s | %s | R$ %.2f | %s", 
            id, descricao, categoria, valor, data.format(FORMATO_DATA));
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Despesa despesa = (Despesa) o;
        return id == despesa.id;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
