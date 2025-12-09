package Eventos;

import Jogo.Jogador;
import Labirinto.Divisao;

/**
 * Interface Strategy para processamento de eventos aleatórios.
 *
 * Padrão Strategy:
 * - Encapsula algoritmos intercambiáveis
 * - Permite adicionar novos eventos sem modificar código existente
 * - Segue o Open/Closed Principle (SOLID)
 *
 * @author Grupo ED
 * @version 1.0
 */
public interface EventoHandler {

    /**
     * Aplica o efeito do evento ao jogador.
     *
     * @param jogador O jogador afetado pelo evento
     * @param origem Divisão de origem do jogador
     * @param destino Divisão de destino planejada
     * @param contexto Contexto com informações adicionais (outros jogadores, etc.)
     * @return true se o movimento planejado deve ser cancelado, false caso contrário
     */
    boolean aplicar(Jogador jogador, Divisao origem, Divisao destino, EventoContexto contexto);

    /**
     * Retorna a descrição do evento.
     *
     * @return Descrição textual do evento
     */
    String getDescricao();

    /**
     * Retorna o ícone do evento para exibição.
     *
     * @return Ícone unicode do evento
     */
    String getIcone();
}
