package Jogo;

import Labirinto.Divisao;
import Labirinto.Labirinto;
import Structures.ArrayUnorderedList;

/**
 * Representa um jogador controlado por um utilizador humano.
 * Esta classe implementa o modo manual do jogo, permitindo que o utilizador
 * escolha os movimentos através de input na consola.
 *
 * O jogador humano visualiza as opções disponíveis e seleciona
 * a divisão para onde pretende mover-se.
 *
 * @author Grupo ED
 * @version 1.0
 */
public class JogadorHumano extends Jogador {

    /**
     * Construtor do jogador humano.
     * Cria um jogador controlado por um utilizador.
     *
     * @param nome O nome do jogador
     * @param pontoInicial A divisão onde o jogador inicia o jogo
     */
    public JogadorHumano(String nome, Divisao pontoInicial) {
        super(nome, pontoInicial);
    }

    /**
     * Solicita ao utilizador que escolha o próximo movimento.
     * Apresenta uma lista numerada com todas as divisões adjacentes
     * disponíveis e aguarda a seleção do utilizador.
     *
     * @param labirinto O labirinto onde o jogo decorre
     * @return A divisão escolhida pelo utilizador, ou null se não houver saídas
     */
    @Override
    public Divisao escolherMovimento(Labirinto labirinto) {
        // Obter as divisões vizinhas da posição atual
        ArrayUnorderedList<Divisao> vizinhos = labirinto.getVizinhos(getPosicaoAtual());

        if (vizinhos.isEmpty()) {
            System.out.println("Não há saídas disponíveis! Está encurralado.");
            return null;
        }

        // Apresentar as opções disponíveis (excluindo a posição atual)
        System.out.println("\nEscolha o seu próximo movimento:");
        int opcao = 1;

        // Criar uma lista para mapear as opções (filtrando a divisão atual)
        ArrayUnorderedList<Divisao> opcoes = new ArrayUnorderedList<>();
        Divisao posicaoAtual = getPosicaoAtual();

        java.util.Iterator<Divisao> it = vizinhos.iterator();
        while (it.hasNext()) {
            Divisao divisao = it.next();
            // Não mostrar a divisão onde o jogador já está
            if (!divisao.equals(posicaoAtual)) {
                System.out.println(opcao + ". " + divisao.getNome() + " (" + divisao.getTipo() + ")");
                opcoes.addToRear(divisao);
                opcao++;
            }
        }

        // Verificar se há opções disponíveis após filtrar
        if (opcoes.isEmpty()) {
            System.out.println("Não há saídas disponíveis! Está encurralado.");
            return null;
        }

        // Obter a escolha válida do utilizador
        int escolha = InputValidator.lerInteiro(
            "\nDigite o número da sua escolha (1-" + (opcao - 1) + "): ",
            1,
            opcao - 1
        );

        // Encontrar e devolver a divisão escolhida
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
