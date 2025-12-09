package Eventos;

import Jogo.Jogador;
import Structures.ArrayUnorderedList;

/**
 * Contexto para execução de eventos aleatórios.
 * Contém informações necessárias para os handlers de eventos.
 *
 * Benefícios OOP:
 * - Encapsula dados relacionados
 * - Evita passar muitos parâmetros
 * - Facilita extensão com novos dados
 *
 * @author Grupo ED
 * @version 1.0
 */
public class EventoContexto {

    private final ArrayUnorderedList<Jogador> todosJogadores;
    private final Jogador jogadorAtual;

    /**
     * Construtor do contexto de evento.
     *
     * @param jogadorAtual O jogador que disparou o evento
     * @param todosJogadores Lista de todos os jogadores no jogo
     */
    public EventoContexto(Jogador jogadorAtual, ArrayUnorderedList<Jogador> todosJogadores) {
        this.jogadorAtual = jogadorAtual;
        this.todosJogadores = todosJogadores;
    }

    /**
     * Obtém a lista de todos os jogadores.
     *
     * @return Lista de jogadores
     */
    public ArrayUnorderedList<Jogador> getTodosJogadores() {
        return todosJogadores;
    }

    /**
     * Obtém o jogador atual.
     *
     * @return O jogador que disparou o evento
     */
    public Jogador getJogadorAtual() {
        return jogadorAtual;
    }

    /**
     * Obtém lista de outros jogadores (excluindo o atual).
     *
     * @return Lista de jogadores exceto o atual
     */
    public ArrayUnorderedList<Jogador> getOutrosJogadores() {
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
}

