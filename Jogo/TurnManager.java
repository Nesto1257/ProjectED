package Jogo;

import Structures.CircularArrayQueue;
import Structures.ArrayUnorderedList;
import Exceptions.EmptyCollectionException;

/**
 * Gestão de turnos dos jogadores usando uma fila circular.
 * Responsável por controlar a ordem de jogada e turnos impedidos.
 *
 * @author Grupo ED
 * @version 2.0
 */
public class TurnManager {
    private CircularArrayQueue<Jogador> filaTurnos;
    private ArrayUnorderedList<Jogador> todosJogadores;
    private int contadorTurnos;

    /**
     * Construtor do gestor de turnos.
     *
     * @param jogadores Lista de jogadores participantes
     */
    public TurnManager(ArrayUnorderedList<Jogador> jogadores) {
        this.todosJogadores = jogadores;
        this.filaTurnos = new CircularArrayQueue<>();
        this.contadorTurnos = 0;

        // Inicializar fila circular com todos os jogadores
        java.util.Iterator<Jogador> it = jogadores.iterator();
        while (it.hasNext()) {
            filaTurnos.enqueue(it.next());
        }
    }

    /**
     * Obtém o próximo jogador da fila e reenfileira-o no final.
     *
     * @return O jogador do turno atual, ou null se a fila estiver vazia
     */
    public Jogador obterProximoJogador() {
        try {
            Jogador jogadorAtual = filaTurnos.dequeue();
            filaTurnos.enqueue(jogadorAtual);
            contadorTurnos++;
            return jogadorAtual;
        } catch (EmptyCollectionException e) {
            System.out.println("⚠️ Erro: Fila de turnos vazia.");
            return null;
        }
    }

    /**
     * Verifica se o jogador está impedido de jogar.
     * Se estiver impedido, decrementa o contador e retorna true.
     *
     * @param jogador O jogador a verificar
     * @return true se o jogador está impedido, false caso contrário
     */
    public boolean processarImpedimento(Jogador jogador) {
        if (jogador.getTurnosImpedido() > 0) {
            System.out.println(jogador.getNome() + " está impedido de jogar. Turnos restantes: " + jogador.getTurnosImpedido());
            jogador.setTurnosImpedido(jogador.getTurnosImpedido() - 1);
            return true;
        }
        return false;
    }

    /**
     * Adiciona o jogador novamente à fila para uma jogada extra.
     *
     * @param jogador O jogador que ganhou jogada extra
     */
    public void adicionarJogadaExtra(Jogador jogador) {
        if (jogador.getJogadasExtra() > 0) {
            System.out.println("→ " + jogador.getNome() + " ganhou uma jogada extra!");
            jogador.adicionarJogadasExtra(-1); // Consome 1 jogada extra
            filaTurnos.enqueue(jogador);
        }
    }

    /**
     * Exibe o cabeçalho do turno atual.
     *
     * @param jogador O jogador do turno atual
     */
    public void exibirCabecalhoTurno(Jogador jogador) {
        System.out.println("\n\n╔══════════════════════════════════════════════════════╗");
        System.out.println("  🎮 TURNO: " + String.format("%-40s", jogador.getNome()));
        System.out.println("  📍 Posição: " + String.format("%-37s", jogador.getPosicaoAtual().getNome()));
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }

    /**
     * @return O contador total de turnos jogados
     */
    public int getContadorTurnos() {
        return contadorTurnos;
    }

    /**
     * @return A lista de todos os jogadores
     */
    public ArrayUnorderedList<Jogador> getTodosJogadores() {
        return todosJogadores;
    }
}

