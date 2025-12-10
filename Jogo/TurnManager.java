package Jogo;

import Structures.CircularArrayQueue;
import Structures.ArrayUnorderedList;
import Exceptions.EmptyCollectionException;

/**
 * Gestor de turnos dos jogadores no jogo Labirinto da Glória.
 * Utiliza uma fila circular para gerir a ordem de jogada, garantindo
 * que cada jogador joga na sua vez e que a sequência se repete.
 *
 * Responsabilidades:
 * <ul>
 *   <li>Manter a ordem de jogada dos participantes</li>
 *   <li>Gerir jogadores impedidos de jogar</li>
 *   <li>Processar jogadas extra</li>
 *   <li>Contabilizar o número total de turnos</li>
 * </ul>
 *
 * @author Grupo ED
 * @version 1.0
 */
public class TurnManager {

    /** Fila circular que mantém a ordem dos turnos */
    private CircularArrayQueue<Jogador> filaTurnos;

    /** Lista de todos os jogadores participantes */
    private ArrayUnorderedList<Jogador> todosJogadores;

    /** Contador de turnos jogados */
    private int contadorTurnos;

    /**
     * Construtor do gestor de turnos.
     * Inicializa a fila circular com todos os jogadores pela ordem
     * em que foram adicionados à lista.
     *
     * @param jogadores A lista de jogadores participantes
     */
    public TurnManager(ArrayUnorderedList<Jogador> jogadores) {
        this.todosJogadores = jogadores;
        this.filaTurnos = new CircularArrayQueue<>();
        this.contadorTurnos = 0;

        // Adicionar todos os jogadores à fila circular
        java.util.Iterator<Jogador> it = jogadores.iterator();
        while (it.hasNext()) {
            filaTurnos.enqueue(it.next());
        }
    }

    /**
     * Obtém o próximo jogador da fila de turnos.
     * O jogador é removido do início da fila e colocado no final,
     * garantindo a rotação circular.
     *
     * @return O jogador do turno actual, ou null se a fila estiver vazia
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
     * Verifica e processa o impedimento de um jogador.
     * Se o jogador estiver impedido, decrementa o contador de turnos
     * de impedimento e não permite que jogue.
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
     * Apresenta o cabeçalho visual do turno atual.
     * Mostra o nome do jogador e a sua posição atual no labirinto.
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
     * Obtém o número total de turnos jogados.
     *
     * @return O contador de turnos
     */
    public int getContadorTurnos() {
        return contadorTurnos;
    }

    /**
     * Obtém a lista de todos os jogadores.
     *
     * @return A lista de jogadores participantes
     */
    public ArrayUnorderedList<Jogador> getTodosJogadores() {
        return todosJogadores;
    }
}

