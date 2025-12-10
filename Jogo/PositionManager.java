package Jogo;

import Labirinto.Divisao;
import Structures.ArrayUnorderedList;

/**
 * Gestor de posições dos jogadores no labirinto.
 * Responsável por operações de troca de posição entre jogadores,
 * recuo e baralhar posições.
 *
 * Esta classe separa a lógica de gestão de posições do EventManager,
 * seguindo o princípio Single Responsibility (SRP).
 *
 * @author Grupo ED
 * @version 1.0
 */
public class PositionManager {

    /** Lista de todos os jogadores no jogo */
    private final ArrayUnorderedList<Jogador> todosJogadores;

    /**
     * Construtor do gestor de posições.
     *
     * @param jogadores A lista de todos os jogadores
     */
    public PositionManager(ArrayUnorderedList<Jogador> jogadores) {
        this.todosJogadores = jogadores;
    }

    /**
     * Troca a posição de um jogador com outro.
     * Se for jogador humano, permite escolher; se for bot, escolhe aleatoriamente.
     *
     * @param jogadorAtual O jogador que vai trocar de posição
     */
    public void trocarPosicaoComOutro(Jogador jogadorAtual) {
        ArrayUnorderedList<Jogador> outrosJogadores = obterOutrosJogadores(jogadorAtual);

        if (outrosJogadores.isEmpty()) {
            System.out.println("→ Não há outros jogadores para trocar de posição!");
            return;
        }

        Jogador jogadorEscolhido = escolherJogadorParaTroca(jogadorAtual, outrosJogadores);

        if (jogadorEscolhido != null) {
            executarTroca(jogadorAtual, jogadorEscolhido);
        }
    }

    /**
     * Processa o evento de recuo de um jogador.
     * Como o evento acontece no corredor (antes do movimento ser concluído),
     * o jogador simplesmente não consegue avançar e permanece na posição atual.
     *
     * @param jogador O jogador afetado pelo evento de recuo
     */
    public void recuarJogador(Jogador jogador) {
        Divisao posicaoAtual = jogador.getPosicaoAtual();

        // O evento de recuo no corredor significa que o jogador não consegue avançar
        // Ele permanece na posição atual (o movimento será cancelado pelo GameEngine)
        System.out.println("⬅️  " + jogador.getNome() + " recua de corredor e fica em " + posicaoAtual.getNome());
    }

    /**
     * Baralha as posições de todos os jogadores.
     * Cada jogador recebe a posição de outro jogador (rotação).
     */
    public void baralharTodasPosicoes() {
        System.out.println("\n💥 CAOS TOTAL! Todos os jogadores trocam de posição!");

        int numJogadores = todosJogadores.size();
        if (numJogadores < 2) {
            return;
        }

        // Recolher jogadores e as suas posições atuais
        Jogador[] jogadores = new Jogador[numJogadores];
        Divisao[] posicoes = new Divisao[numJogadores];

        int i = 0;
        java.util.Iterator<Jogador> it = todosJogadores.iterator();
        while (it.hasNext()) {
            Jogador j = it.next();
            jogadores[i] = j;
            posicoes[i] = j.getPosicaoAtual();
            i++;
        }

        // Baralhar as posições com Fisher-Yates
        for (int k = numJogadores - 1; k > 0; k--) {
            int j = (int)(Math.random() * (k + 1));
            Divisao temp = posicoes[k];
            posicoes[k] = posicoes[j];
            posicoes[j] = temp;
        }

        // Atribuir novas posições
        for (int k = 0; k < numJogadores; k++) {
            jogadores[k].moverPara(posicoes[k]);
            System.out.println("→ " + jogadores[k].getNome() + " agora está em " + posicoes[k].getNome());
        }
    }

    /**
     * Obtém a lista de jogadores exceto o jogador atual.
     *
     * @param jogadorAtual O jogador a excluir
     * @return Lista dos outros jogadores
     */
    private ArrayUnorderedList<Jogador> obterOutrosJogadores(Jogador jogadorAtual) {
        ArrayUnorderedList<Jogador> outros = new ArrayUnorderedList<>();
        java.util.Iterator<Jogador> it = todosJogadores.iterator();
        while (it.hasNext()) {
            Jogador j = it.next();
            if (j != jogadorAtual) {
                outros.addToRear(j);
            }
        }
        return outros;
    }

    /**
     * Escolhe o jogador para troca conforme o tipo (humano ou bot).
     *
     * @param jogadorAtual O jogador que está a trocar
     * @param outrosJogadores Lista de jogadores disponíveis
     * @return O jogador escolhido
     */
    private Jogador escolherJogadorParaTroca(Jogador jogadorAtual, ArrayUnorderedList<Jogador> outrosJogadores) {
        if (jogadorAtual instanceof JogadorHumano) {
            return escolherManualmente(outrosJogadores);
        } else {
            return escolherAleatoriamente(outrosJogadores);
        }
    }

    /**
     * Permite ao jogador humano escolher com quem trocar.
     *
     * @param jogadores Lista de jogadores disponíveis
     * @return O jogador escolhido
     */
    private Jogador escolherManualmente(ArrayUnorderedList<Jogador> jogadores) {
        System.out.println("\n👥 Escolha com que jogador deseja trocar de posição:");

        int opcao = 1;
        java.util.Iterator<Jogador> it = jogadores.iterator();
        while (it.hasNext()) {
            Jogador j = it.next();
            System.out.println(opcao + ". " + j.getNome() + " (em " + j.getPosicaoAtual().getNome() + ")");
            opcao++;
        }

        int escolha = InputValidator.lerInteiro("Escolha (1-" + (opcao-1) + "): ", 1, opcao-1);

        int contador = 1;
        java.util.Iterator<Jogador> itEscolha = jogadores.iterator();
        while (itEscolha.hasNext()) {
            Jogador j = itEscolha.next();
            if (contador == escolha) {
                return j;
            }
            contador++;
        }
        return null;
    }

    /**
     * Escolhe aleatoriamente um jogador (para bots).
     *
     * @param jogadores Lista de jogadores disponíveis
     * @return O jogador escolhido aleatoriamente
     */
    private Jogador escolherAleatoriamente(ArrayUnorderedList<Jogador> jogadores) {
        int indexAleatorio = (int)(Math.random() * jogadores.size());
        int contador = 0;
        java.util.Iterator<Jogador> it = jogadores.iterator();
        while (it.hasNext()) {
            Jogador j = it.next();
            if (contador == indexAleatorio) {
                return j;
            }
            contador++;
        }
        return null;
    }

    /**
     * Executa a troca de posição entre dois jogadores.
     *
     * @param jogador1 O primeiro jogador
     * @param jogador2 O segundo jogador
     */
    private void executarTroca(Jogador jogador1, Jogador jogador2) {
        Divisao posicao1 = jogador1.getPosicaoAtual();
        Divisao posicao2 = jogador2.getPosicaoAtual();

        jogador1.moverPara(posicao2);
        jogador2.moverPara(posicao1);


        System.out.println("🔄 " + jogador1.getNome() + " trocou de posição com " + jogador2.getNome() + "!");
        System.out.println("→ " + jogador1.getNome() + " agora está em " + jogador1.getPosicaoAtual().getNome());
        System.out.println("→ " + jogador2.getNome() + " agora está em " + jogador2.getPosicaoAtual().getNome());
    }
}

