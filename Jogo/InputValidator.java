package Jogo;

import java.util.Scanner;

/**
 * Classe utilitária para validação e obtenção de inputs do utilizador.
 * Centraliza toda a lógica de leitura e validação de dados introduzidos
 * através da consola.
 *
 * Esta classe implementa o padrão Singleton para o Scanner, garantindo
 * que apenas uma instância é utilizada em toda a aplicação.
 *
 * @author Grupo ED
 * @version 1.0
 */
public class InputValidator {

    /** Instância única do Scanner (Singleton) */
    private static Scanner scanner;

    /**
     * Obtém a instância partilhada do Scanner.
     * Se ainda não existir, cria uma instância.
     *
     * @return A instância do Scanner
     */
    public static Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }

    /**
     * Solicita ao utilizador um número inteiro dentro de um intervalo específico.
     * Repete o pedido até que uma entrada válida seja fornecida.
     *
     * @param mensagem A mensagem a apresentar ao utilizador
     * @param min O valor mínimo permitido (inclusivo)
     * @param max O valor máximo permitido (inclusivo)
     * @return O número inteiro válido introduzido pelo utilizador
     */
    public static int lerInteiro(String mensagem, int min, int max) {
        Scanner sc = getScanner();
        int valor;

        while (true) {
            System.out.print(mensagem);
            try {
                valor = sc.nextInt();
                sc.nextLine(); // Limpar o buffer após ler o inteiro
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println("⚠️ Escolha inválida! Digite um número entre " + min + " e " + max + ".");
            } catch (Exception e) {
                System.out.println("⚠️ Entrada inválida! Digite um número.");
                sc.nextLine();
            }
        }
    }

    /**
     * Lê a próxima linha de texto do input.
     * Aguarda que o utilizador introduza texto e pressione Enter.
     *
     * @return A linha de texto introduzida pelo utilizador
     */
    public static String lerLinha() {
        Scanner sc = getScanner();
        String linha = sc.nextLine();

        // Se a linha estiver vazia, aguardar nova entrada
        while (linha.trim().isEmpty()) {
            linha = sc.nextLine();
        }

        return linha;
    }
}

