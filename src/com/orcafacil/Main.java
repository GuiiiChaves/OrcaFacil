package com.orcafacil;

import com.orcafacil.exception.DespesaException;
import com.orcafacil.exception.OrcamentoExcedidoException;
import com.orcafacil.export.ExportadorServico;
import com.orcafacil.model.Despesa;
import com.orcafacil.service.GerenciadorOrcamento;
import com.orcafacil.service.PersistenciaServico;
import com.orcafacil.service.RelatorioServico;
import com.orcafacil.ui.Menu;
import com.orcafacil.util.FormatadorUtil;
import com.orcafacil.util.Logger;
import com.orcafacil.util.ValidadorDespesa;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    
    private static final String ARQUIVO_DADOS = "data/despesas.txt";
    
    private final List<Despesa> despesas = new ArrayList<>();
    private final GerenciadorOrcamento orcamento = new GerenciadorOrcamento();
    private final PersistenciaServico persistencia = new PersistenciaServico(ARQUIVO_DADOS);
    private final RelatorioServico relatorio = new RelatorioServico();
    private final ExportadorServico exportador = new ExportadorServico("data/exports");
    private final Menu menu = new Menu();
    private final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        Main app = new Main();
        app.executar();
    }
    
    private void executar() {
        menu.exibirBemVindo();
        criarDiretorio();
        carregarDados();
        
        boolean rodando = true;
        while (rodando) {
            menu.exibirMenu();
            int opcao = lerOpcao();
            
            switch (opcao) {
                case 1: adicionarDespesa(); break;
                case 2: listarDespesas(); break;
                case 3: exibirResumo(); break;
                case 4: removerDespesa(); break;
                case 5: editarDespesa(); break;
                case 6: exibirRelatorios(); break;
                case 7: exibirEstatisticas(); break;
                case 8: gerenciarOrcamento(); break;
                case 9: exportarDados(); break;
                case 0: salvarDados(); menu.exibirDespedida(); rodando = false; break;
                default: System.out.println("❌ Opção inválida!");
            }
        }
    }
    
    private void adicionarDespesa() {
        try {
            menu.exibirSeparador("ADICIONAR NOVA DESPESA");
            System.out.print("Descrição: ");
            String descricao = scanner.nextLine().trim();
            ValidadorDespesa.validarDescricao(descricao);
            System.out.print("Categoria: ");
            String categoria = scanner.nextLine().trim();
            if (categoria.isEmpty()) categoria = "Outros";
            ValidadorDespesa.validarCategoria(categoria);
            System.out.print("Valor (R$): ");
            double valor = Double.parseDouble(scanner.nextLine());
            ValidadorDespesa.validarValor(valor);
            
            try {
                orcamento.validarOrcamento(despesas, valor);
            } catch (OrcamentoExcedidoException e) {
                System.out.println(e.getMessage());
                System.out.print("Deseja adicionar mesmo assim? (s/N): ");
                if (!scanner.nextLine().toLowerCase().startsWith("s")) {
                    System.out.println("❌ Despesa não adicionada.");
                    return;
                }
            }
            
            Despesa despesa = new Despesa(descricao, categoria, valor);
            despesas.add(despesa);
            System.out.println("✅ Despesa adicionada!");
            System.out.println("   " + despesa);
            if (orcamento.verificarAlerta(despesas)) {
                System.out.println("🟡 ALERTA: 80% do orçamento foi atingido!");
            }
            Logger.logInfo("Despesa: " + descricao);
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
            Logger.logErro("Erro ao adicionar", e);
        }
    }
    
    private void listarDespesas() {
        menu.exibirSeparador("LISTA DE DESPESAS");
        if (despesas.isEmpty()) {
            System.out.println("📭 Nenhuma despesa.");
            return;
        }
        System.out.printf("%-5s %-30s %-15s %-12s %s\n", "ID", "Descrição", "Categoria", "Valor", "Data");
        System.out.println(FormatadorUtil.repeat("─", 80));
        for (Despesa d : despesas) {
            System.out.println(d.toStringResumo());
        }
        System.out.println(FormatadorUtil.repeat("─", 80));
        Map<String, Object> stats = relatorio.calcularEstatisticas(despesas);
        System.out.printf("📊 Total: %d | R$ %.2f\n", despesas.size(), stats.get("total"));
    }
    
    private void exibirResumo() {
        menu.exibirSeparador("RESUMO FINANCEIRO");
        if (despesas.isEmpty()) {
            System.out.println("📭 Sem despesas.");
            return;
        }
        Map<String, Object> stats = relatorio.calcularEstatisticas(despesas);
        System.out.printf("💸 Total: R$ %.2f\n", stats.get("total"));
        System.out.printf("📊 Qtd: %d\n", stats.get("quantidade"));
        System.out.printf("📉 Média: R$ %.2f\n", stats.get("media"));
        System.out.println();
        System.out.println(orcamento.statusOrcamento(despesas));
    }
    
    private void removerDespesa() {
        menu.exibirSeparador("REMOVER DESPESA");
        if (despesas.isEmpty()) {
            System.out.println("📭 Sem despesas.");
            return;
        }
        try {
            System.out.print("ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            Despesa d = null;
            for (Despesa desp : despesas) {
                if (desp.getId() == id) {
                    d = desp;
                    break;
                }
            }
            if (d != null) {
                System.out.println("Encontrada: " + d);
                System.out.print("Confirma? (s/N): ");
                if (scanner.nextLine().toLowerCase().startsWith("s")) {
                    despesas.remove(d);
                    System.out.println("✅ Removida!");
                    Logger.logInfo("Despesa removida: " + id);
                }
            } else {
                System.out.println("❌ Não encontrada!");
            }
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }
    
    private void editarDespesa() {
        menu.exibirSeparador("EDITAR DESPESA");
        if (despesas.isEmpty()) {
            System.out.println("📭 Sem despesas.");
            return;
        }
        try {
            System.out.print("ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            Despesa d = null;
            for (Despesa desp : despesas) {
                if (desp.getId() == id) {
                    d = desp;
                    break;
                }
            }
            if (d == null) {
                System.out.println("❌ Não encontrada!");
                return;
            }
            System.out.println("Atual: " + d);
            System.out.print("Descrição (Enter=sem mudança): ");
            String desc = scanner.nextLine().trim();
            if (!desc.isEmpty()) {
                ValidadorDespesa.validarDescricao(desc);
                d.setDescricao(desc);
            }
            System.out.print("Categoria: ");
            String cat = scanner.nextLine().trim();
            if (!cat.isEmpty()) {
                ValidadorDespesa.validarCategoria(cat);
                d.setCategoria(cat);
            }
            System.out.print("Valor: ");
            String val = scanner.nextLine().trim();
            if (!val.isEmpty()) {
                double v = Double.parseDouble(val);
                ValidadorDespesa.validarValor(v);
                d.setValor(v);
            }
            System.out.println("✅ Atualizada!");
            Logger.logInfo("Editada: " + id);
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
    
    private void exibirRelatorios() {
        boolean sair = false;
        while (!sair) {
            menu.exibirSeparadorRelatorios();
            int opcao = lerOpcao();
            if (opcao == 1) {
                System.out.println(relatorio.gerarGraficoCategoria(despesas));
            } else if (opcao == 2) {
                filtrarPorPeriodo();
            } else if (opcao == 0) {
                sair = true;
            } else {
                System.out.println("❌ Inválida!");
            }
        }
    }
    
    private void filtrarPorPeriodo() {
        menu.exibirSeparador("FILTRO POR PERÍODO");
        System.out.println("1. 7 dias | 2. 30 dias | 3. Mês atual | 4. Customizado");
        int filtro = lerOpcao();
        List<Despesa> filtradas = new ArrayList<>();
        
        switch (filtro) {
            case 1: filtradas = relatorio.filtrarUltimosDias(despesas, 7); break;
            case 2: filtradas = relatorio.filtrarUltimosDias(despesas, 30); break;
            case 3: filtradas = relatorio.filtrarMesAtual(despesas); break;
            case 4:
                try {
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    System.out.print("Início (dd/MM/yyyy): ");
                    LocalDate inicio = LocalDate.parse(scanner.nextLine(), fmt);
                    System.out.print("Fim: ");
                    LocalDate fim = LocalDate.parse(scanner.nextLine(), fmt);
                    filtradas = relatorio.filtrarPorPeriodo(despesas, inicio, fim);
                } catch (Exception e) {
                    System.out.println("❌ Data inválida!");
                }
                break;
        }
        
        if (filtradas.isEmpty()) {
            System.out.println("📭 Nenhuma despesa.");
        } else {
            System.out.printf("📋 %d despesas\n", filtradas.size());
            for (Despesa d : filtradas) System.out.println("   " + d);
        }
    }
    
    private void exibirEstatisticas() {
        menu.exibirSeparador("ESTATÍSTICAS");
        if (despesas.isEmpty()) {
            System.out.println("📭 Sem dados.");
            return;
        }
        Map<String, Object> stats = relatorio.calcularEstatisticas(despesas);
        System.out.printf("💰 Total: R$ %.2f\n", stats.get("total"));
        System.out.printf("📊 Qtd: %d\n", stats.get("quantidade"));
        System.out.printf("📈 Máx: R$ %.2f\n", stats.get("maximo"));
        System.out.printf("📉 Mín: R$ %.2f\n", stats.get("minimo"));
        System.out.printf("📊 Média: R$ %.2f\n", stats.get("media"));
        System.out.printf("📌 Mediana: R$ %.2f\n", stats.get("mediana"));
        System.out.printf("🎯 Top: %s\n", stats.get("categoriaMaior"));
    }
    
    private void gerenciarOrcamento() {
        menu.exibirSeparador("ORÇAMENTO");
        if (orcamento.temOrcamento()) {
            System.out.println(orcamento.statusOrcamento(despesas));
            System.out.println();
        }
        System.out.print("Novo orçamento (R$): ");
        try {
            double valor = Double.parseDouble(scanner.nextLine());
            ValidadorDespesa.validarValor(valor);
            orcamento.setOrcamentoMensal(valor);
            System.out.println("✅ Orçamento atualizado!");
            System.out.println(orcamento.statusOrcamento(despesas));
            Logger.logInfo("Orçamento: R$ " + valor);
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
    
    private void exportarDados() {
        menu.exibirSeparador("EXPORTAR");
        if (despesas.isEmpty()) {
            System.out.println("📭 Sem despesas.");
            return;
        }
        System.out.println("1. CSV | 2. JSON");
        try {
            String caminho = null;
            if (lerOpcao() == 1) {
                caminho = exportador.exportarCSV(despesas);
            } else {
                caminho = exportador.exportarJSON(despesas);
            }
            if (caminho != null) {
                System.out.println("✅ Exportado!");
                System.out.println("📁 " + caminho);
                Logger.logInfo("Exportado: " + caminho);
            }
        } catch (IOException e) {
            System.out.println("❌ Erro: " + e.getMessage());
            Logger.logErro("Erro export", e);
        }
    }
    
    private int lerOpcao() {
        try {
            System.out.print("Opção: ");
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private void criarDiretorio() {
        java.io.File d = new java.io.File("data");
        if (!d.exists()) d.mkdirs();
    }
    
    private void carregarDados() {
        despesas.addAll(persistencia.carregar());
        GerenciadorOrcamento orc = persistencia.carregarOrcamento();
        orcamento.setOrcamentoMensal(orc.getOrcamentoMensal());
    }
    
    private void salvarDados() {
        persistencia.salvar(despesas, orcamento);
    }
}
