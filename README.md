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
├── Main.java                    (Orquestração das 12 HUs)
├── model/Despesa.java          (HU1, HU6, HU7)
├── service/
│   ├── GerenciadorOrcamento.java    (HU2, HU4, HU12)
│   ├── RelatorioServico.java        (HU5, HU8, HU9)
│   └── PersistenciaServico.java     (Persistência geral)
├── ui/Menu.java                 (Interface HUs)
├── exception/
│   ├── DespesaException.java        (Tratamento geral)
│   └── OrcamentoExcedidoException.java (HU4)
├── util/
│   ├── ValidadorDespesa.java        (Validação HUs)
│   ├── FormatadorUtil.java          (Formatação)
│   └── Logger.java                  (Logging de erros)
└── export/ExportadorServico.java   (HU10, HU11)
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
- 📝 [Histórias de Usuário - Trello](https://trello.com/invite/b/68c06a77a8ec1f6901e94b05/ATTIf34ee5911b3fdf4a0f7dfd230ba6ec24691EE067/gestao-de-despesas-user-stories)  
- 🎥 [Screencast - YouTube](https://youtu.be/9CVhGFV0qb8?feature=shared)


---

### 📍 Entrega 4
- 📝 [Histórias de Usuário - Trello](https://trello.com/invite/b/68c06a77a8ec1f6901e94b05/ATTIf34ee5911b3fdf4a0f7dfd230ba6ec24691EE067/gestao-de-despesas-user-stories)  
- 🎥 [Screencast - YouTube](#)  
- 🎨 [Protótipo](#)  
