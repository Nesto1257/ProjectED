package Jogo;

import Labirinto.Divisao;
import Labirinto.Labirinto;
import Structures.ArrayUnorderedList;

/**
 * Implementa um Jogador controlado por input humano (Modo Manual).
 * Esta classe permite que um utilizador real jogue o jogo através de
 * inputs via console, escolhendo o próximo movimento entre as divisões disponíveis.
 *
 * Refatorado para usar InputValidator (Utility Class - SRP).
 *
 * @author Grupo ED
 * @version 1.1 - Refatorado com InputValidator
 */
public class JogadorHumano extends Jogador {

    /**
     * Construtor do JogadorHumano.
     * Inicializa o jogador humano com um nome e posição inicial.
     *
     * @param nome O nome do jogador
     * @param pontoInicial A divisão inicial onde o jogador começa
     */
    public JogadorHumano(String nome, Divisao pontoInicial) {
        super(nome, pontoInicial);
    }

    /**
     * Solicita ao utilizador que escolha o próximo movimento.
     * Apresenta uma lista numerada de divisões adjacentes disponíveis
     * e aguarda o input do utilizador para selecionar uma delas.
     *
     * @param labirinto O labirinto onde o jogador se encontra
     * @return A Divisao escolhida pelo utilizador, ou null se não houver vizinhos disponíveis
     */
    @Override
    public Divisao escolherMovimento(Labirinto labirinto) {
//        System.out.println("\n====================================");
//        System.out.println("Turno de " + getNome());
//        System.out.println("Posição atual: " + getPosicaoAtual().getNome());
//        System.out.println("====================================");

        ArrayUnorderedList<Divisao> vizinhos = labirinto.getVizinhos(getPosicaoAtual());

        if (vizinhos.isEmpty()) {
            System.out.println("Não há saídas disponíveis! Está encurralado.");
            return null;
        }

        // Mostrar opções disponíveis
        System.out.println("\nEscolha o seu próximo movimento:");
        int opcao = 1;

        // Criar um array temporário para mapear opções
        ArrayUnorderedList<Divisao> opcoes = new ArrayUnorderedList<>();

        java.util.Iterator<Divisao> it = vizinhos.iterator();
        while (it.hasNext()) {
            Divisao divisao = it.next();
            System.out.println(opcao + ". " + divisao.getNome() + " (" + divisao.getTipo() + ")");
            opcoes.addToRear(divisao);
            opcao++;
        }

        // Usar InputValidator para obter escolha válida
        int escolha = InputValidator.lerInteiro(
            "\nDigite o número da sua escolha (1-" + (opcao - 1) + "): ",
            1,
            opcao - 1
        );

        // Obter a divisão escolhida
        int contador = 1;
        java.util.Iterator<Divisao> itEscolha = opcoes.iterator();
        while (itEscolha.hasNext()) {
            Divisao divisao = itEscolha.next();
            if (contador == escolha) {
                System.out.println("\n" + getNome() + " escolheu mover para: " + divisao.getNome());
                return divisao;
            }
            contador++;
        }

        return null;
    }
}
