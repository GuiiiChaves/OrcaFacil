import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class despesa {
    private static int proximoId = 1;
    private int id;
    private String descricao;
    private String categoria;
    private double valor;
    private LocalDate data;
    
    public despesa(String descricao, String categoria, double valor) {
        this.id = proximoId++;
        this.descricao = descricao;
        this.categoria = categoria;
        this.valor = valor;
        this.data = LocalDate.now();
    }
    
    // Construtor para carregar dados
    public despesa(int id, String descricao, String categoria, double valor, LocalDate data) {
        this.id = id;
        this.descricao = descricao;
        this.categoria = categoria;
        this.valor = valor;
        this.data = data;
        
        if (id >= proximoId) {
            proximoId = id + 1;
        }
    }
    
    // Getters
    public int getId() { return id; }
    public String getDescricao() { return descricao; }
    public String getCategoria() { return categoria; }
    public double getValor() { return valor; }
    public LocalDate getData() { return data; }
    
    public String paraArquivo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return id + ";" + descricao + ";" + categoria + ";" + valor + ";" + data.format(formatter);
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("[%d] %s | %s | R$ %.2f | %s", 
            id, descricao, categoria, valor, data.format(formatter));
    }
}