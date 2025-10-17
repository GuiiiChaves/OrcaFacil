# 📋 Histórias de Usuário - OrçaFácil

## Visão Geral
Este documento contém todas as histórias de usuário do projeto OrçaFácil v2.0, um gerenciador de despesas pessoais desenvolvido em Java 21 LTS.

---

## 1. Cadastro de Despesas
**Como** usuário, **quero** cadastrar uma despesa informando descrição, categoria, valor e data, **para que** eu consiga acompanhar meus gastos de forma organizada.

### Critérios de Aceitação
- ✅ Campo de descrição obrigatório
- ✅ Campo de categoria obrigatório
- ✅ Campo de valor obrigatório (números decimais)
- ✅ Data automática (data do sistema)
- ✅ Validação de entrada com mensagens de erro

### Implementação no Código
- **Classe**: `Main.java` - método `adicionarDespesa()`
- **Validação**: `ValidadorDespesa.validarDescricao()`, `validarCategoria()`, `validarValor()`
- **Modelo**: `Despesa.java` - construtor `Despesa(String, String, double)`
- **Persistência**: `PersistenciaServico.salvar()`

---

## 2. Definição de Orçamento
**Como** usuário, **quero** definir um orçamento mensal limite, **para que** eu possa controlar se estou gastando dentro do planejado.

### Critérios de Aceitação
- ✅ Menu para definir/atualizar orçamento
- ✅ Armazenar valor do orçamento
- ✅ Validação de valor mínimo
- ✅ Persistência do orçamento em arquivo

### Implementação no Código
- **Classe**: `Main.java` - método `gerenciarOrcamento()`
- **Serviço**: `GerenciadorOrcamento.setOrcamentoMensal()`, `getOrcamentoMensal()`
- **Persistência**: `GerenciadorOrcamento.paraArquivo()`, `deArquivo(String)`
- **Salvamento**: `PersistenciaServico.salvar()`, `carregarOrcamento()`

---

## 3. Visualização da Lista de Despesas
**Como** usuário, **quero** visualizar todas as despesas cadastradas em uma lista, **para que** eu possa consultar meu histórico de gastos.

### Critérios de Aceitação
- ✅ Exibir todas as despesas em formato tabular
- ✅ Mostrar ID, descrição, categoria, valor e data
- ✅ Indicar quando não há despesas
- ✅ Calcular total ao listar

### Implementação no Código
- **Classe**: `Main.java` - método `listarDespesas()`
- **Formatação**: `Despesa.toStringResumo()`
- **Utilitário**: `FormatadorUtil.repeat()` para linhas separadoras
- **Estatísticas**: `RelatorioServico.calcularEstatisticas()`

---

## 4. Acompanhamento do Orçamento
**Como** usuário, **quero** receber alertas quando meus gastos atingem 80% do orçamento, **para que** eu possa tomar decisões antes de excedê-lo.

### Critérios de Aceitação
- ✅ Alerta ao atingir 80% do orçamento
- ✅ Bloqueio ao tentar exceder orçamento
- ✅ Opção de confirmar adição mesmo excedendo
- ✅ Status visual com emojis (🟢/🟡/🔴)

### Implementação no Código
- **Classe**: `Main.java` - método `adicionarDespesa()` 
- **Validação**: `GerenciadorOrcamento.validarOrcamento()`
- **Alerta**: `GerenciadorOrcamento.verificarAlerta()`
- **Exceção**: `OrcamentoExcedidoException` com mensagem customizada
- **Status**: `GerenciadorOrcamento.statusOrcamento()`

---

## 5. Relatório por Categoria
**Como** usuário, **quero** visualizar um relatório dos meus gastos agrupados por categoria, **para que** eu identifique em qual área estou gastando mais.

### Critérios de Aceitação
- ✅ Agrupar despesas por categoria
- ✅ Calcular total por categoria
- ✅ Mostrar percentual de cada categoria
- ✅ Gráfico ASCII com barras visuais
- ✅ Ordenar por valor decrescente

### Implementação no Código
- **Classe**: `Main.java` - método `exibirRelatorios()`
- **Serviço**: `RelatorioServico.totalPorCategoria()`
- **Gráfico**: `RelatorioServico.gerarGraficoCategoria()`
- **Formatação**: `FormatadorUtil.repeat("█", ...)` para barras

---

## 6. Editar Despesa
**Como** usuário, **quero** editar uma despesa existente, **para que** eu possa corrigir informações erradas sem perder meu histórico.

### Critérios de Aceitação
- ✅ Selecionar despesa por ID
- ✅ Editar descrição, categoria e/ou valor
- ✅ Manter ID e data original
- ✅ Validação de novos valores
- ✅ Confirmação de sucesso

### Implementação no Código
- **Classe**: `Main.java` - método `editarDespesa()`
- **Modelo**: `Despesa.setDescricao()`, `setCategoria()`, `setValor()`
- **Busca**: Iteração sobre lista por ID
- **Validação**: `ValidadorDespesa` para novos dados
- **Persistência**: Salvamento automático após edição

---

## 7. Remover Despesa
**Como** usuário, **quero** remover despesas do meu registro, **para que** eu mantenha apenas os gastos válidos.

### Critérios de Aceitação
- ✅ Selecionar despesa por ID
- ✅ Confirmar remoção antes de deletar
- ✅ Exibir despesa encontrada
- ✅ Mensagem de sucesso/cancelamento
- ✅ Atualizar arquivo de dados

### Implementação no Código
- **Classe**: `Main.java` - método `removerDespesa()`
- **Busca**: Iteração sobre lista para encontrar por ID
- **Confirmação**: Validação de entrada do usuário
- **Remoção**: `List.remove(despesa)`
- **Persistência**: `PersistenciaServico.salvar()`

---

## 8. Filtrar Despesas por Período
**Como** usuário, **quero** filtrar minhas despesas por período específico, **para que** eu possa analisar gastos em semanas ou meses específicos.

### Critérios de Aceitação
- ✅ Opção para últimos 7 dias
- ✅ Opção para últimos 30 dias
- ✅ Opção para mês atual
- ✅ Opção para período customizado (data inicial e final)
- ✅ Exibir quantidade de despesas encontradas

### Implementação no Código
- **Classe**: `Main.java` - método `exibirFiltroData()`
- **Serviço**: `RelatorioServico.filtrarUltimosDias()`, `filtrarMesAtual()`, `filtrarPorPeriodo()`
- **API**: `LocalDate`, `YearMonth` para manipulação de datas
- **Comparação**: `isBefore()`, `isAfter()`, `equals()`

---

## 9. Visualizar Estatísticas Detalhadas
**Como** usuário, **quero** ver estatísticas avançadas dos meus gastos, **para que** eu compreenda melhor meus padrões de consumo.

### Critérios de Aceitação
- ✅ Mostrar total gasto
- ✅ Mostrar quantidade de despesas
- ✅ Calcular valor mínimo
- ✅ Calcular valor máximo
- ✅ Calcular média de gastos
- ✅ Calcular mediana
- ✅ Identificar categoria com maior gasto

### Implementação no Código
- **Classe**: `Main.java` - método `exibirEstatisticas()`
- **Serviço**: `RelatorioServico.calcularEstatisticas()`
- **Cálculos**: Métodos para média, mínimo, máximo, mediana
- **Retorno**: `Map<String, Object>` com todas as estatísticas
- **Formatação**: Exibição de valores com 2 casas decimais

---

## 10. Exportar Dados em CSV
**Como** usuário, **quero** exportar meus dados em formato CSV, **para que** eu possa analisar em planilhas como Excel ou Google Sheets.

### Critérios de Aceitação
- ✅ Gerar arquivo CSV com timestamp
- ✅ Incluir cabeçalhos (ID, Descrição, Categoria, Valor, Data)
- ✅ Escapar aspas em descrições
- ✅ Salvar em diretório `data/exports/`
- ✅ Confirmar caminho do arquivo gerado

### Implementação no Código
- **Classe**: `Main.java` - método `exportarDados()`
- **Serviço**: `ExportadorServico.exportarCSV()`
- **Formato**: CSV padrão com headers
- **Filename**: `despesas_YYYY-MM-dd_HHmmss.csv`
- **Retorno**: Caminho absoluto do arquivo criado

---

## 11. Exportar Dados em JSON
**Como** usuário, **quero** exportar meus dados em formato JSON, **para que** eu possa integrar com APIs ou aplicações web.

### Critérios de Aceitação
- ✅ Gerar arquivo JSON com timestamp
- ✅ Estrutura válida com array de despesas
- ✅ Incluir todos os campos de cada despesa
- ✅ Formatação legível com identação
- ✅ Salvar em diretório `data/exports/`

### Implementação no Código
- **Classe**: `Main.java` - método `exportarDados()`
- **Serviço**: `ExportadorServico.exportarJSON()`
- **Formato**: JSON com identação de 2 espaços
- **Filename**: `despesas_YYYY-MM-dd_HHmmss.json`
- **Estrutura**: `{"despesas": [{...}, {...}]}`

---

## 12. Resumo Financeiro com Status
**Como** usuário, **quero** ver um resumo rápido da minha situação financeira, **para que** eu tenha uma visão geral dos meus gastos.

### Critérios de Aceitação
- ✅ Exibir total de gastos
- ✅ Exibir quantidade de despesas
- ✅ Calcular média por despesa
- ✅ Mostrar status do orçamento
- ✅ Indicar disponível vs. gasto

### Implementação no Código
- **Classe**: `Main.java` - método `exibirResumo()`
- **Cálculos**: Total, quantidade, média
- **Serviço**: `GerenciadorOrcamento.statusOrcamento()`
- **Formatação**: Emojis para status visual
- **Estrutura**: Uso de `Map<String, Object>` para dados

---