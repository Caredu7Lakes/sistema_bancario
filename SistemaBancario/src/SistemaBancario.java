
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ContaBancaria {

    // 1. ATRIBUTOS

    private double saldo;
    private double limiteChequeEspecial;
    private double chequeEspecialUsado;
    private List<String> historicoTransacoes;
    private String cpf;

    // 2. CONSTRUTOR

    public ContaBancaria(double depositoInicial, String cpf) {
        if (!validarCpf(cpf)) {
            System.out.println("Aviso: CPF informado parece ser inválido!");
        }
        this.cpf = cpf;
        this.saldo = Math.max(0, depositoInicial);
        this.historicoTransacoes = new ArrayList<>();

        if (depositoInicial <= 500.0) {
            this.limiteChequeEspecial = 50.0;
        } else {
            this.limiteChequeEspecial = depositoInicial * 0.5;
        }
        this.chequeEspecialUsado = 0.0;

        adicionarHistorico(String.format("Abertura de conta (CPF: %s) com depósito inicial: R$ %.2f", cpf, depositoInicial));
    }

    // 3. MÉTODO VALIDADOR (Pode ser 'static' pois valida apenas o texto recebido)

    public static boolean validarCpf(String cpf) {
        if (cpf == null) {
            return false;
        }
        // Remove pontos e traços

        String cpfLimpo = cpf.replaceAll("\\D", "");

        // Verifica se possui exatamente 11 dígitos

        return cpfLimpo.length() == 11;
    }

    // 4. GETTERS E DEMAIS MÉTODOS
    
    public String getCpf() {
        return cpf;
    }

    public void receberPix(double valor) {
        if (valor <= 0) {
            System.out.println("Valor de Pix inválido!");
            return;
        }
        if (cpf == null || cpf.trim().isEmpty()) {
            System.out.println("CPF inválido para recebimento de Pix!");
            return;
        }

        System.out.printf("Recebendo Pix na conta do CPF %s no valor de R$ %.2f...%n", cpf, valor);
        depositar(valor);
        adicionarHistorico(String.format("Pix Recebido: +R$ %.2f", valor));
    }

    public void aplicarRendimento(double taxaPercentual) {
        if (saldo > 0.0) {
            double rendimento = saldo * (taxaPercentual / 100.0);
            this.saldo += rendimento;
            System.out.printf("Rendimento de %.1f%% aplicado: R$ %.2f%n", taxaPercentual, rendimento);
            adicionarHistorico(String.format("Rendimento aplicado (%.1f%%): +R$ %.2f", taxaPercentual, rendimento));
        } else {
            System.out.println("Rendimento não aplicado: Saldo atual é nulo ou negativo.");
        }
    }

    // Auxiliar para registrar entradas no histórico

    private void adicionarHistorico(String transacao) {
        this.historicoTransacoes.add(transacao);
    }

    // Método para exibir o histórico de transações no console

    public void exibirHistorico() {
        System.out.println("\n=== HISTÓRICO DE TRANSAÇÕES ===");
        if (historicoTransacoes.isEmpty()) {
            System.out.println("Nenhuma transação registrada.");
        } else {
            for (int i = 0; i < historicoTransacoes.size(); i++) {
                System.out.printf("%d. %s%n", (i + 1), historicoTransacoes.get(i));
            }
        }
    }

    public double getSaldo() {
        return saldo;
    }

    public double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    public double getSaldoDisponivelTotal() {
        return saldo + (limiteChequeEspecial - chequeEspecialUsado);
    }

    public boolean estaUsandoChequeEspecial() {
        return chequeEspecialUsado > 0;
    }

    public void consultarSaldo() {
        System.out.printf("Saldo Atual: R$ %.2f%n", saldo);
        System.out.printf("Limite Total do Cheque Especial: R$ %.2f%n", limiteChequeEspecial);
        System.out.printf("Cheque Especial Usado: R$ %.2f%n", chequeEspecialUsado);
        System.out.printf("Disponível Total (Saldo + Limite Restante): R$ %.2f%n", getSaldoDisponivelTotal());
    }

    public void consultarChequeEspecial() {
        System.out.printf("Limite Total de Cheque Especial: R$ %.2f%n", limiteChequeEspecial);
        System.out.printf("Valor Utilizado: R$ %.2f%n", chequeEspecialUsado);
        System.out.printf("Limite Disponível: R$ %.2f%n", limiteChequeEspecial - chequeEspecialUsado);
        if (estaUsandoChequeEspecial()) {
            System.out.println("Status: A conta está UTILIZANDO o cheque especial.");
        } else {
            System.out.println("Status: A conta NÃO está utilizando o cheque especial.");
        }
    }

    public void depositar(double valor) {
        if (valor <= 0) {
            System.out.println("Valor de depósito inválido!");
            return;
        }

        // Se estiver usando cheque especial, o depósito cobre primeiro a taxa de 20% e o uso
        if (chequeEspecialUsado > 0) {
            double taxa = chequeEspecialUsado * 0.20;
            double totalParaQuitar = chequeEspecialUsado + taxa;

            System.out.printf("Cobrança de taxa de 20%% sobre o Cheque Especial usado (R$ %.2f): R$ %.2f%n", 
                              chequeEspecialUsado, taxa);

            if (valor >= totalParaQuitar) {
                valor -= totalParaQuitar;
                chequeEspecialUsado = 0;
                saldo += valor;
                System.out.println("Uso do cheque especial quitado com sucesso!");
            } else {
                if (valor <= taxa) {
                    System.out.println("O valor depositado foi consumido inteiramente pela taxa do cheque especial.");
                } else {
                    double abatedorDivida = valor - taxa;
                    chequeEspecialUsado -= abatedorDivida;
                    System.out.printf("O valor abateu a taxa e reduziu o uso do cheque especial para R$ %.2f%n", 
                                      chequeEspecialUsado);
                }
            }
        } else {
            saldo += valor;
        }

        adicionarHistorico(String.format("Depósito realizado: +R$ %.2f", valor));
        System.out.printf("Depósito realizado com sucesso! Saldo atual: R$ %.2f%n", saldo);
    }

    public boolean sacar(double valor) {
        if (valor <= 0) {
            System.out.println("Valor de saque inválido!");
            return false;
        }

        if (valor > getSaldoDisponivelTotal()) {
            System.out.println("Transação recusada: Saldo e Limite de Cheque Especial insuficientes.");
            return false;
        }

        if (valor <= saldo) {
            saldo -= valor;
        } else {
            double restante = valor - saldo;
            saldo = 0;
            chequeEspecialUsado += restante;
            System.out.println("Atenção: Você entrou no Cheque Especial!");
        }

        adicionarHistorico(String.format("Saque realizado: -R$ %.2f", valor));
        System.out.printf("Saque de R$ %.2f realizado com sucesso.%n", valor);
        return true;
    }

    public void pagarBoleto(double valor) {
        if (valor <= 0) {
            System.out.println("Valor de boleto inválido!");
            return;
        }

        System.out.println("Processando pagamento de boleto...");
        if (sacar(valor)) {
            adicionarHistorico(String.format("Pagamento de boleto: -R$ %.2f", valor));
            System.out.println("Boleto pago com sucesso!");
        } else {
            System.out.println("Falha ao pagar o boleto.");
        }
    }

    // ADICIONAL: Método para enviar Pix utilizando a lógica de saque da conta
    public void enviarPix(double valor) {
        if (valor <= 0) {
            System.out.println("Valor de Pix inválido!");
            return;
        }

        System.out.println("Processando envio de Pix...");
        if (sacar(valor)) {
            adicionarHistorico(String.format("Pix Enviado: -R$ %.2f", valor));
            System.out.println("Pix enviado com sucesso!");
        } else {
            System.out.println("Falha ao enviar Pix.");
        }
    }
}
public class SistemaBancario {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Bem-vindo ao Sistema Bancário ===");
        System.out.print("Informe o valor do depósito inicial para abertura da conta: R$ ");
        double depositoInicial = scanner.nextDouble();
        System.out.print("Informe o CPF do titular da conta: ");
        String cpf = scanner.next();

        ContaBancaria conta = new ContaBancaria(depositoInicial, cpf);

        System.out.println("\nConta criada com sucesso!");
        System.out.printf("Saldo Inicial: R$ %.2f | Limite de Cheque Especial Definido: R$ %.2f | CPF: %s%n",
                          conta.getSaldo(), conta.getLimiteChequeEspecial(), conta.getCpf());

        int opcao = 0;

        // CORREÇÃO: Menu expandido para incluir as opções de Rendimento, Pix e Histórico
        while (opcao != 10) {
            System.out.println("\n---------------- MENU INTERATIVO ----------------");
            System.out.println("1. Consultar Saldo");
            System.out.println("2. Consultar Cheque Especial");
            System.out.println("3. Depositar Dinheiro");
            System.out.println("4. Sacar Dinheiro");
            System.out.println("5. Pagar um Boleto");
            System.out.println("6. Verificar uso do Cheque Especial");
            System.out.println("7. Receber Pix");
            System.out.println("8. Enviar Pix");
            System.out.println("9. Aplicar Rendimento (1%)");
            System.out.println("10. Exibir Histórico de Transações");
            System.out.println("11. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    conta.consultarSaldo();
                    break;
                case 2:
                    conta.consultarChequeEspecial();
                    break;
                case 3:
                    System.out.print("Digite o valor para depósito: R$ ");
                    double valorDeposito = scanner.nextDouble();
                    conta.depositar(valorDeposito);
                    break;
                case 4:
                    System.out.print("Digite o valor para saque: R$ ");
                    double valorSaque = scanner.nextDouble();
                    conta.sacar(valorSaque);
                    break;
                case 5:
                    System.out.print("Digite o valor do boleto: R$ ");
                    double valorBoleto = scanner.nextDouble();
                    conta.pagarBoleto(valorBoleto);
                    break;
                case 6:
                    if (conta.estaUsandoChequeEspecial()) {
                        System.out.println("Aviso: A conta ESTÁ utilizando o limite de cheque especial.");
                    } else {
                        System.out.println("A conta NÃO está utilizando o cheque especial.");
                    }
                    break;
                case 7:
                    System.out.print("Digite o valor do Pix a receber: R$ ");
                    double valorPixRec = scanner.nextDouble();
                    conta.receberPix(valorPixRec);
                    break;
                case 8:
                    System.out.print("Digite o valor do Pix a enviar: R$ ");
                    double valorPixEnv = scanner.nextDouble();
                    conta.enviarPix(valorPixEnv);
                    break;
                case 9:
                    conta.aplicarRendimento(1.0);
                    break;
                case 10:
                    conta.exibirHistorico();
                    break;
                case 11:
                    System.out.println("Obrigado por usar nosso Sistema Bancário. Até logo!");
                    opcao = 10; // Força a saída do laço while
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }

        scanner.close();
    }
}