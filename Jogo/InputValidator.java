package Jogo;

import java.util.Scanner;

/**
 * Classe utilitária para validação e obtenção de inputs do utilizador.
 *
 * Benefícios OOP:
 * - Single Responsibility Principle: só trata de inputs
 * - Reutilização: pode ser usado em várias partes do código
 * - Encapsulação: lógica de validação centralizada
 * - Facilita testes: pode ser mockado facilmente
 *
 * @author Grupo ED
 * @version 1.0
 */
public class InputValidator {

    private static Scanner scanner;

    /**
     * Obtém a instância do Scanner (Singleton pattern para Scanner).
     * Evita criar múltiplos scanners para System.in.
     *
     * @return Scanner partilhado
     */
    public static Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }

    /**
     * Solicita um número inteiro dentro de um intervalo.
     * Repete até obter entrada válida.
     *
     * @param mensagem Mensagem a mostrar ao utilizador
     * @param min Valor mínimo permitido (inclusive)
     * @param max Valor máximo permitido (inclusive)
     * @return O número inteiro válido
     */
    public static int lerInteiro(String mensagem, int min, int max) {
        Scanner sc = getScanner();
        int valor;

        while (true) {
            System.out.print(mensagem);
            try {
                valor = sc.nextInt();
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println("⚠️ Escolha inválida! Digite um número entre " + min + " e " + max + ".");
            } catch (Exception e) {
                System.out.println("⚠️ Entrada inválida! Digite um número.");
                sc.nextLine(); // Limpar buffer
            }
        }
    }

    /**
     * Solicita uma string não vazia.
     *
     * @param mensagem Mensagem a mostrar ao utilizador
     * @return A string inserida (não vazia)
     */
    public static String lerString(String mensagem) {
        Scanner sc = getScanner();
        String input;

        while (true) {
            System.out.print(mensagem);
            input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("⚠️ A entrada não pode estar vazia!");
        }
    }

    /**
     * Solicita uma resposta sim/não.
     *
     * @param mensagem Mensagem a mostrar ao utilizador
     * @return true para sim, false para não
     */
    public static boolean lerSimNao(String mensagem) {
        Scanner sc = getScanner();

        while (true) {
            System.out.print(mensagem + " (s/n): ");
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("s") || input.equals("sim")) {
                return true;
            } else if (input.equals("n") || input.equals("nao") || input.equals("não")) {
                return false;
            }
            System.out.println("⚠️ Responda com 's' ou 'n'.");
        }
    }

    /**
     * Lê a próxima linha do input.
     *
     * @return A linha lida
     */
    public static String lerLinha() {
        return getScanner().nextLine();
    }

    /**
     * Fecha o scanner. Deve ser chamado apenas no final do programa.
     */
    public static void fechar() {
        if (scanner != null) {
            scanner.close();
            scanner = null;
        }
    }
}

