package com.orcafacil.export;

import com.orcafacil.model.Despesa;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExportadorServico {
    
    private static final DateTimeFormatter DATA_HORA = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
    
    private final String diretorio;
    
    public ExportadorServico(String diretorio) {
        this.diretorio = diretorio;
        new File(diretorio).mkdirs();
    }
    
    public String exportarCSV(List<Despesa> despesas) throws IOException {
        String nomeArquivo = String.format("despesas_%s.csv", 
            LocalDateTime.now().format(DATA_HORA));
        File arquivo = new File(diretorio, nomeArquivo);
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivo))) {
            writer.println("ID,Descrição,Categoria,Valor,Data");
            
            for (Despesa despesa : despesas) {
                writer.println(String.format("%d,\"%s\",\"%s\",%.2f,%s",
                    despesa.getId(),
                    despesa.getDescricao(),
                    despesa.getCategoria(),
                    despesa.getValor(),
                    despesa.getData()
                ));
            }
        }
        
        return arquivo.getAbsolutePath();
    }
    
    public String exportarJSON(List<Despesa> despesas) throws IOException {
        String nomeArquivo = String.format("despesas_%s.json", 
            LocalDateTime.now().format(DATA_HORA));
        File arquivo = new File(diretorio, nomeArquivo);
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivo))) {
            writer.println("{");
            writer.println("  \"despesas\": [");
            
            for (int i = 0; i < despesas.size(); i++) {
                Despesa d = despesas.get(i);
                writer.println("    {");
                writer.printf("      \"id\": %d,\n", d.getId());
                writer.printf("      \"descricao\": \"%s\",\n", d.getDescricao());
                writer.printf("      \"categoria\": \"%s\",\n", d.getCategoria());
                writer.printf("      \"valor\": %.2f,\n", d.getValor());
                writer.printf("      \"data\": \"%s\"\n", d.getData());
                writer.print("    }");
                if (i < despesas.size() - 1) {
                    writer.println(",");
                } else {
                    writer.println();
                }
            }
            
            writer.println("  ]");
            writer.println("}");
        }
        
        return arquivo.getAbsolutePath();
    }
}
