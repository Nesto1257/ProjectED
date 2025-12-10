package Eventos;

/**
 * Representa um evento aleatório que pode ocorrer durante a travessia
 * de um corredor no labirinto.
 * Os eventos são gerados aleatoriamente e podem ter efeitos positivos
 * ou negativos para o jogador.
 *
 * @author Grupo ED
 * @version 1.0
 * @see TipoEvento
 */
public class EventoAleatorio {

    /** O tipo do evento */
    private final TipoEvento tipoEvento;

    /**
     * Construtor privado do evento aleatório.
     * Utilizar o método estático {@link #getEventoAleatorio()} para obter uma instância.
     *
     * @param tipoEvento O tipo do evento
     */
    private EventoAleatorio(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    /**
     * Obtém o tipo do evento.
     *
     * @return O tipo do evento como enum
     */
    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    /**
     * Obtém a descrição textual do evento.
     *
     * @return A descrição do evento
     */
    public String getDescricao() {
        return tipoEvento.getDescricao();
    }

    /**
     * Gera e devolve um evento aleatório.
     * O tipo de evento é selecionado aleatoriamente entre todos os tipos disponíveis.
     *
     * @return Um novo evento aleatório
     */
    public static EventoAleatorio getEventoAleatorio() {
        return new EventoAleatorio(TipoEvento.getAleatorio());
    }
}