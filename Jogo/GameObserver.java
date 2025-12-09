package Jogo;

import Labirinto.Divisao;

/**
 * Interface Observer para eventos do jogo.
 *
 * Padrão Observer:
 * - Desacopla o GameEngine das classes que precisam reagir a eventos
 * - Permite adicionar novas funcionalidades sem modificar GameEngine
 * - Exemplos de uso: logging, interface gráfica, estatísticas
 *
 * @author Grupo ED
 * @version 1.0
 */
public interface GameObserver {

    /**
     * Chamado quando um turno inicia.
     *
     * @param jogador O jogador do turno
     * @param numeroTurno Número do turno
     */
    void onTurnoIniciado(Jogador jogador, int numeroTurno);

    /**
     * Chamado quando um jogador se move.
     *
     * @param jogador O jogador que se moveu
     * @param origem Divisão de origem
     * @param destino Divisão de destino
     */
    void onMovimento(Jogador jogador, Divisao origem, Divisao destino);

    /**
     * Chamado quando um desafio é resolvido.
     *
     * @param jogador O jogador que resolveu
     * @param tipoDesafio Tipo do desafio ("Enigma", "Alavanca")
     * @param sucesso true se foi resolvido com sucesso
     */
    void onDesafioResolvido(Jogador jogador, String tipoDesafio, boolean sucesso);

    /**
     * Chamado quando um evento aleatório ocorre.
     *
     * @param jogador O jogador afetado
     * @param descricaoEvento Descrição do evento
     */
    void onEventoAleatorio(Jogador jogador, String descricaoEvento);

    /**
     * Chamado quando o jogo termina.
     *
     * @param vencedor O jogador vencedor (pode ser null se cancelado)
     * @param totalTurnos Total de turnos jogados
     */
    void onJogoTerminado(Jogador vencedor, int totalTurnos);
}
