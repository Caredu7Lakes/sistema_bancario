<div align="center">

# 🚀 Java Bank CLI

> **Simulação interativa de uma conta bancária real via terminal.**  
> Projeto desenvolvido em Java para consolidar conceitos de Programação Orientada a Objetos (POO), regras de negócio financeiras e manipulação de fluxos no terminal.

[![Java Version](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=java)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)
[![Status](https://img.shields.io/badge/Status-Concluído-brightgreen?style=for-the-badge)](#)

---

</div>

## 💡 Sobre o Projeto

O **Java Bank CLI** não é apenas mais um exercício acadêmico de terminal: o objetivo aqui é **simular o comportamento real de uma conta corrente**. 

A aplicação conta com um motor financeiro dinâmico que gerencia cheque especial, taxas percentuais automáticas para regularização de pendências, validação de dados de entrada e histórico estruturado de transações.

---

## 🔥 Funcionalidades Principais

- 🏦 **Abertura de Conta Dinâmica:** Definição de limite de cheque especial baseado no depósito inicial e validação de CPF.
- 💵 **Gestão de Cheque Especial:** Cobrança automática de taxa de 20% sobre o saldo devedor do limite durante depósitos de quitação.
- 💸 **Operações PIX & Boleto:** Fluxos de envio e recebimento de Pix e pagamento de boletos integrados ao saldo/limite.
- 📈 **Rendimento Automático:** Aplicação de taxa percentual sobre o saldo positivo disponível.
- 📜 **Extrato Detalhado:** Histórico de transações com registro cronológico de movimentações.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java (JDK 17 ou superior)
- **Paradigma:** Programação Orientada a Objetos (POO)
- **Entrada/Saída:** `java.util.Scanner` e `java.util.List`

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos
Antes de começar, você precisará ter instalado em sua máquina:
- [JDK 17+](https://www.oracle.com/java/technologies/downloads/)
- Um terminal de sua preferência ou IDE (IntelliJ IDEA, VS Code, Eclipse)

### Passos

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/seu-usuario/seu-repositorio.git](https://github.com/seu-usuario/seu-repositorio.git)


** Navegue até o diretório do projeto:**


cd seu-repositorio

Compile os arquivos Java:


javac SistemaBancario.java

Execute a aplicação:


java SistemaBancario

🧠 Aprendizados e Arquitetura

O projeto foi estruturado aplicando boas práticas de POO:

Encapsulamento: Proteção do saldo e atributos críticos através de métodos modificadores (depositar, sacar).

Validação de Entrada: Métodos dedicados para consistência de dados antes da alteração de estado dos objetos.

Lógica Financeira Incremental: Tratamento de prioridade entre consumo de saldo próprio vs. saldo do cheque especial.

Feito com ☕ e Java por Carlos Eduardo