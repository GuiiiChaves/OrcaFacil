import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {
    private static final String ARQUIVO_DADOS = "data/despesas.txt";
    private static List<despesa> despesas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("💰 Bem-vindo ao OrçaFácil!");
        System.out.println("Sistema Simples de Controle de Despesas");
        
        criarDiretorio();
        carregarDados();
        
        while (true) {
            exibirMenu();
            int opcao = lerOpcao();
            
            switch (opcao) {
                case 1 -> adicionarDespesa();
                case 2 -> listarDespesas();
                case 3 -> calcularTotal();
                case 4 -> removerDespesa();
                case 0 -> {
                    salvarDados();
                    System.out.println("👋 Dados salvos! Até logo!");
                    return;
                }
                default -> System.out.println("❌ Opção inválida!");
            }
        }
    }
    
    private static void exibirMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("💰 ORÇAFÁCIL - MENU PRINCIPAL");
        System.out.println("=".repeat(40));
        System.out.println("1. 📝 Adicionar Despesa");
        System.out.println("2. 📋 Listar Despesas");
        System.out.println("3. �� Ver Total Gasto");
        System.out.println("4. ��️  Remover Despesa");
        System.out.println("0. 🚪 Sair");
        System.out.println("=".repeat(40));
        System.out.print("Escolha uma opção: ");
    }
    
    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void adicionarDespesa() {
        System.out.println("\n📝 ADICIONAR NOVA DESPESA");
        System.out.println("─".repeat(30));
        
        try {
            System.out.print("Descrição: ");
            String descricao = scanner.nextLine().trim();
            
            if (descricao.isEmpty()) {
                System.out.println("❌ Descrição não pode estar vazia!");
                return;
            }
            
            System.out.print("Categoria: ");
            String categoria = scanner.nextLine().trim();
            
            if (categoria.isEmpty()) {
                categoria = "Outros";
            }
            
            System.out.print("Valor (R$): ");

            double valor = Double.parseDouble(scanner.nextLine());
            
            if (valor <= 0) {
                System.out.println("❌ Valor deve ser maior que zero!");
                return;
            }
            
            despesa despesa = new despesa(descricao, categoria, valor);
            despesas.add(despesa);
            
            System.out.println("✅ Despesa adicionada com sucesso!");
            System.out.println("   " + despesa);
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Valor inválido! Use apenas números.");
        } catch (Exception e) {
            System.out.println("❌ Erro ao adicionar despesa: " + e.getMessage());
        }
    }
    
    private static void listarDespesas() {
        System.out.println("\n📋 LISTA DE DESPESAS");
        System.out.println("─".repeat(50));
        
        if (despesas.isEmpty()) {
            System.out.println("📭 Nenhuma despesa cadastrada ainda.");
            return;
        }
        
        for (despesa despesa : despesas) {
            System.out.println(despesa);
        }
        
        System.out.println("─".repeat(50));
        System.out.printf("📊 Total de despesas: %d\n", despesas.size());
    }
    
    private static void calcularTotal() {
        System.out.println("\n💰 RESUMO FINANCEIRO");
        System.out.println("─".repeat(30));
        
        if (despesas.isEmpty()) {
            System.out.println("📭 Nenhuma despesa para calcular.");
            return;
        }
        
        double total = 0;
        for (despesa despesa : despesas) {
            total += despesa.getValor();
        }
        
           System.out.printf("💸 Total gasto: R$ %.2f\n", total);
           System.out.printf("📊 Número de despesas: %d\n", despesas.size());
           System.out.printf("📉 Média por despesa: R$ %.2f\n", total / despesas.size());
    }
    
    private static void removerDespesa() {
        System.out.println("\n🗑️ REMOVER DESPESA");
        System.out.println("─".repeat(30));
        
        if (despesas.isEmpty()) {
            System.out.println("📭 Nenhuma despesa para remover.");
            return;
        }
        
        listarDespesas();
        
        try {
            System.out.print("\nDigite o ID da despesa para remover: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            despesa despesaParaRemover = null;
            for (despesa despesa : despesas) {
                if (despesa.getId() == id) {
                    despesaParaRemover = despesa;
                    break;
                }
            }
            
            if (despesaParaRemover != null) {
                System.out.println("Despesa encontrada: " + despesaParaRemover);
                System.out.print("Confirma a remoção? (s/N): ");
                String confirmacao = scanner.nextLine().toLowerCase();
                
                if (confirmacao.startsWith("s")) {
                    despesas.remove(despesaParaRemover);
                    System.out.println("✅ Despesa removida com sucesso!");
                } else {
                    System.out.println("❌ Remoção cancelada.");
                }
            } else {
                System.out.println("❌ Despesa com ID " + id + " não encontrada!");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido! Use apenas números.");
        }
    }
    
    private static void criarDiretorio() {
        File diretorio = new File("data");
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
    }
    
    private static void carregarDados() {
        File arquivo = new File(ARQUIVO_DADOS);
        
        if (!arquivo.exists()) {
            System.out.println("📂 Criando novo arquivo de dados...");
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            int contador = 0;
            
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                
                if (dados.length == 5) {
                    try {
                        int id = Integer.parseInt(dados[0]);
                        String descricao = dados[1];
                        String categoria = dados[2];
                        double valor = Double.parseDouble(dados[3]);
                        LocalDate data = LocalDate.parse(dados[4], 
                            DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        
                        despesas.add(new despesa(id, descricao, categoria, valor, data));
                        contador++;
                    } catch (Exception e) {
                        System.out.println("⚠️  Linha ignorada (formato inválido): " + linha);
                    }
                }
            }
            
            if (contador > 0) {
                System.out.printf("📂 %d despesas carregadas do arquivo.\n", contador);
            }
            
        } catch (IOException e) {
            System.out.println("⚠️  Erro ao carregar dados: " + e.getMessage());
        }
    }
    
    private static void salvarDados() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_DADOS))) {
            for (despesa despesa : despesas) {
                writer.println(despesa.paraArquivo());
            }
            System.out.printf("💾 %d despesas salvas no arquivo.\n", despesas.size());
            
        } catch (IOException e) {
            System.out.println("❌ Erro ao salvar dados: " + e.getMessage());
        }
    }
}
