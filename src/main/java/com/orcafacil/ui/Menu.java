package com.orcafacil.ui;

import com.orcafacil.util.FormatadorUtil;

public class Menu {
    
    public void exibirBemVindo() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                     💰 BEM-VINDO AO ORCAFÁCIL                    ║");
        System.out.println("║              Sistema de Controle de Despesas Pessoais             ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");
    }
    
    public void exibirMenu() {
        System.out.println("\n" + FormatadorUtil.repeat("═", 60));
        System.out.println("                    💰 MENU PRINCIPAL");
        System.out.println(FormatadorUtil.repeat("═", 60));
        System.out.println("1. 📝 Adicionar Despesa");
        System.out.println("2. 📋 Listar Despesas");
        System.out.println("3. 💸 Resumo Financeiro");
        System.out.println("4. 🗑️  Remover Despesa");
        System.out.println("5. ✏️  Editar Despesa");
        System.out.println("6. 📊 Relatórios");
        System.out.println("7. 📈 Estatísticas");
        System.out.println("8. 💰 Orçamento");
        System.out.println("9. 📁 Exportar");
        System.out.println("0. 🚪 Sair");
        System.out.println(FormatadorUtil.repeat("═", 60));
    }
    
    public void exibirSeparador(String titulo) {
        System.out.println("\n" + FormatadorUtil.repeat("─", 60));
        System.out.println(titulo);
        System.out.println(FormatadorUtil.repeat("─", 60));
    }
    
    public void exibirSeparadorRelatorios() {
        System.out.println("\n" + FormatadorUtil.repeat("─", 60));
        System.out.println("📊 RELATÓRIOS");
        System.out.println(FormatadorUtil.repeat("─", 60));
        System.out.println("1. 📈 Gráfico por Categoria");
        System.out.println("2. 📅 Filtrar por Período");
        System.out.println("0. ↩️  Voltar");
    }
    
    public void exibirDespedida() {
        System.out.println("\n" + FormatadorUtil.repeat("═", 60));
        System.out.println("👋 Dados salvos! Até logo!");
        System.out.println(FormatadorUtil.repeat("═", 60) + "\n");
    }
}
