package Eventos;

/**
 * Modela os eventos aleatórios que podem ocorrer nos corredores.
 *
 * Refatorado para usar TipoEvento (Enum) em vez de constantes int.
 * Mantém compatibilidade com código existente através das constantes legadas.
 *
 * Benefícios OOP:
 * - Usa Enum para type safety
 * - Encapsulamento do estado do evento
 * - Imutabilidade (instância criada é final)
 *
 * @author Grupo ED
 * @version 2.0 - Refatorado com Enum TipoEvento
 */
public class EventoAleatorio {

    // ===== CONSTANTES LEGADAS (para compatibilidade) =====
    // Manter para código existente que usa estas constantes
    public static final int TIPO_EXTRA_TURN = 1;
    public static final int TIPO_TROCA_POSICAO = 2;
    public static final int TIPO_RECUAR = 3;
    public static final int TIPO_IMPEDIR_TURNO = 4;
    public static final int TIPO_TROCAR_TODOS = 5;

    // ===== NOVOS CAMPOS (usando Enum) =====
    private final TipoEvento tipoEvento;

    /**
     * Construtor privado usando TipoEvento.
     *
     * @param tipoEvento O tipo do evento (Enum)
     */
    private EventoAleatorio(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    /**
     * Obtém o tipo do evento como Enum (nova API).
     *
     * @return TipoEvento do evento
     */
    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    /**
     * Obtém o tipo do evento como inteiro (compatibilidade legada).
     *
     * @return Tipo numérico do evento
     * @deprecated Usar getTipoEvento() que retorna o Enum
     */
    @Deprecated
    public int getTipo() {
        switch (tipoEvento) {
            case EXTRA_TURN:
                return TIPO_EXTRA_TURN;
            case TROCA_POSICAO:
                return TIPO_TROCA_POSICAO;
            case RECUAR:
                return TIPO_RECUAR;
            case IMPEDIR_TURNO:
                return TIPO_IMPEDIR_TURNO;
            case TROCAR_TODOS:
                return TIPO_TROCAR_TODOS;
            default:
                return 0;
        }
    }

    /**
     * Obtém a descrição do evento.
     *
     * @return Descrição textual do evento
     */
    public String getDescricao() {
        return tipoEvento.getDescricao();
    }

    /**
     * Obtém o ícone do evento.
     *
     * @return Ícone unicode do evento
     */
    public String getIcone() {
        return tipoEvento.getIcone();
    }

    /**
     * Retorna um evento aleatório.
     * Usa o Enum TipoEvento internamente para seleção.
     *
     * @return Um objeto EventoAleatorio selecionado aleatoriamente
     */
    public static EventoAleatorio getEventoAleatorio() {
        return new EventoAleatorio(TipoEvento.getAleatorio());
    }

    /**
     * Cria um evento de um tipo específico.
     *
     * @param tipo O TipoEvento desejado
     * @return Novo EventoAleatorio do tipo especificado
     */
    public static EventoAleatorio criar(TipoEvento tipo) {
        return new EventoAleatorio(tipo);
    }

    @Override
    public String toString() {
        return tipoEvento.toString();
    }
}