package Jogo;

import Labirinto.Divisao;
import Structures.ArrayUnorderedList;
import Eventos.EventoAleatorio;

/**
 * Gestor de eventos aleatórios no jogo Labirinto da Glória.
 * Responsável por gerar e aplicar eventos que ocorrem durante
 * a travessia dos corredores entre divisões.
 *
 * Delega a gestão de posições ao {@link PositionManager} seguindo
 * o princípio Single Responsibility (SRP).
 *
 * @author Grupo ED
 * @version 2.0
 */
public class EventManager {

    /** Probabilidade de ocorrer um evento (20%) */
    private static final double CHANCE_EVENTO = 0.2;

    /** Gestor de posições dos jogadores */
    private final PositionManager positionManager;

    /**
     * Construtor do gestor de eventos.
     *
     * @param jogadores A lista de todos os jogadores participantes
     */
    public EventManager(ArrayUnorderedList<Jogador> jogadores) {
        this.positionManager = new PositionManager(jogadores);
    }

    /**
     * Aplica um evento aleatório ao jogador durante a travessia de um corredor.
     * Existe uma probabilidade de 20% de ocorrer um evento.
     *
     * @param jogador O jogador que está a atravessar o corredor
     * @param origem A divisão de origem do movimento
     * @param destino A divisão de destino pretendida
     * @return true se o movimento foi cancelado pelo evento, false caso contrário
     */
    public boolean aplicarEventoAleatorio(Jogador jogador, Divisao origem, Divisao destino) {
        if (Math.random() >= CHANCE_EVENTO) {
            return false;
        }

        EventoAleatorio evento = EventoAleatorio.getEventoAleatorio();
        System.out.println("\n🎲 !!! EVENTO NO CORREDOR !!! " + evento.getDescricao());

        return aplicarEfeito(evento, jogador);
    }

    /**
     * Aplica o efeito do evento ao jogador.
     *
     * @param evento O evento a aplicar
     * @param jogador O jogador afetado
     * @return true se o movimento deve ser cancelado, false caso contrário
     */
    private boolean aplicarEfeito(EventoAleatorio evento, Jogador jogador) {
        switch (evento.getTipoEvento()) {
            case EXTRA_TURN:
                jogador.adicionarJogadasExtra(1);
                jogador.registrarEfeito("Ganhou 1 jogada extra");
                System.out.println("→ " + jogador.getNome() + " ganha 1 jogada extra!");
                return false;

            case TROCA_POSICAO:
                positionManager.trocarPosicaoComOutro(jogador);
                jogador.registrarEfeito("Trocou de posição com outro jogador");
                return true;

            case RECUAR:
                positionManager.recuarJogador(jogador);
                jogador.registrarEfeito("Recuou para posição anterior");
                return true;

            case IMPEDIR_TURNO:
                jogador.setTurnosImpedido(2);
                jogador.registrarEfeito("Impedido por 2 turnos");
                System.out.println("→ " + jogador.getNome() + " fica impedido de jogar por 2 turnos!");
                return false;

            case TROCAR_TODOS:
                positionManager.baralharTodasPosicoes();
                jogador.registrarEfeito("Caos total - todos trocaram de posição");
                return true;

            default:
                return false;
        }
    }
}

