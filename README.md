# 💰 Gerenciador de Despesas Pessoais

**OrçaFácil**

Um sistema em **Java** para controlar despesas mensais de forma simples e prática.  
Com ele, o usuário pode cadastrar despesas, definir um orçamento mensal e acompanhar se está gastando dentro dos limites.

---

## 🚀 Funcionalidades

- 📌 **Cadastro de despesas** (descrição, categoria, valor e data).  
- 📊 **Relatório de gastos mensais** por categoria.  
- 💡 **Definição de orçamento** e alerta quando o limite for ultrapassado.  
- 💾 **Persistência de dados** (em arquivos CSV/JSON ou banco de dados).  

---

## 🛠️ Tecnologias Utilizadas

- **Java 17+**  
- **Paradigma Orientado a Objetos (POO)**  
- **Arquivos CSV/JSON**
- Interface:
  - **Console (menu interativo)**  

---


## 📂 Estrutura do Projeto

....
```
src/com/orcafacil/
├── Main.java                   
├── model/Despesa.java          
├── service/
│   ├── GerenciadorOrcamento.java    
│   ├── RelatorioServico.java        
│   └── PersistenciaServico.java    
├── ui/Menu.java                 
├── exception/
│   ├── DespesaException.java        
│   └── OrcamentoExcedidoException.java 
├── util/
│   ├── ValidadorDespesa.java       
│   ├── FormatadorUtil.java          
│   └── Logger.java                  
└── export/ExportadorServico.java   
```
---

## 📆 Entregas

### 📍 Entrega 1
- 📝 [Histórias de Usuário - Trello](https://trello.com/invite/b/68c06a77a8ec1f6901e94b05/ATTIf34ee5911b3fdf4a0f7dfd230ba6ec24691EE067/gestao-de-despesas-user-stories)  
- 🎥 [Screencast - YouTube](https://youtu.be/k9gDid1kmO8?si=euRSA99S3K9c86fg)
- 🎨 [Protótipo Lo-Fi - Figma](https://www.figma.com/design/IUs0L0fK1t2KCI7IVJvq7r/POO?node-id=2-12&p=f&m=draw)  

---

### 📍 Entrega 2

- 🪲 GitHub Issues

<img width="1631" height="401" alt="image" src="https://github.com/user-attachments/assets/37a0b9e1-adad-4e5b-9bb5-f23b74cc5745" />

- 📝 [Histórias de Usuário - Trello](https://trello.com/invite/b/68c06a77a8ec1f6901e94b05/ATTIf34ee5911b3fdf4a0f7dfd230ba6ec24691EE067/gestao-de-despesas-user-stories)  
- 🎥 [Screencast - YouTube](https://youtu.be/9CVhGFV0qb8)

---

### 📍 Entrega 3

- 🪲 GitHub Issues

<img width="1244" height="508" alt="image" src="https://github.com/user-attachments/assets/9229b8a3-262e-46b9-bb10-bab328a0ba21" />

- 📝 [Histórias de Usuário - Trello](https://trello.com/invite/b/68c06a77a8ec1f6901e94b05/ATTIf34ee5911b3fdf4a0f7dfd230ba6ec24691EE067/gestao-de-despesas-user-stories)  
- 🎥 [Screencast - YouTube](https://www.youtube.com/watch?v=fUAyHnrhs70)
- 🎥 [Screencast Testes Automatizados - YouTube](https://www.youtube.com/watch?v=OIWfS2sCUW8)

---

### 📍 Entrega 4

- 🪲 GitHub Issues

<img width="1373" height="150" alt="image" src="https://github.com/user-attachments/assets/8228a972-bb63-42da-a32e-ccaba38a9351" />

- 📝 [Histórias de Usuário - Trello](https://trello.com/invite/b/68c06a77a8ec1f6901e94b05/ATTIf34ee5911b3fdf4a0f7dfd230ba6ec24691EE067/gestao-de-despesas-user-stories)

  
- 10. Removedor Despesa
Como usuário, quero remover despesas do meu registro, para que eu mantenha apenas os gastos válidos.

Critérios de Aceitação
✅marcar por ID
✅ Confirme a remoção antes de excluir
✅ Exibir pré-
✅ Mensagem de sucesso/cancelamento
✅ Atualizar arquivo de dados
Implementação no Código
Classe : Main.java- métodoremoverDespesa()
Busca : Iteração sobre lista para encontrar por ID
Confirmação : Validação de entrada do usuário
Remoção :List.remove(despesa)
Persistência :PersistenciaServico.salvar()

11. Filtrar Despesas por Período
Como usuário, quero filtrar minhas despesas por período específico, para que eu possa analisar gastos em semanas ou meses específicos.

Critérios de Aceitação
✅ Opção para últimos 7 dias
✅ Opção para últimos 30 dias
✅ Opção para mês atual
✅ Opção para período customizado (dados inicial e final)
✅ Exibir quantidade de despesas despesas
Implementação no Código
Classe : Main.java- métodoexibirFiltroData()
Serviço : RelatorioServico.filtrarUltimosDias(), filtrarMesAtual(),filtrarPorPeriodo()
API : LocalDate, YearMonthpara manipulação de dados
Comparação : isBefore(), isAfter(),equals()

12. Visualizar estatísticas interessantes
Como usuário, quero ver estatísticas detalhadas dos meus gastos, para que eu entenda melhores meus padrões de consumo.

Critérios de Aceitação
✅ Mostrar gasto total
✅ Mostrar quantidade de despesas
✅ Calcular valor mínimo
✅ Calcular valor máximo
✅ Calcular média de gastos
✅ Calcular mediana
✅ identificar categoria com maior gasto
Implementação no Código
Classe : Main.java- métodoexibirEstatisticas()
serviço :RelatorioServico.calcularEstatisticas()
Cálculos : Métodos para média, mínimo, máximo, mediana
Retorno : Map<String, Object>com todas as estatísticas
Formatação : Exibição de valores com 2 casas decimais

- 🎥 [Screencast - YouTube](https://www.youtube.com/watch?v=fUAyHnrhs70)  
