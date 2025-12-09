package Jogo;

/**
 * Enum que representa os estados possíveis do jogo.
 *
 * Padrão State simplificado:
 * - Encapsula transições de estado válidas
 * - Fornece type safety para estados
 * - Facilita adição de novos estados
 *
 * @author Grupo ED
 * @version 1.0
 */
public enum EstadoJogo {
    /**
     * Estado inicial antes do jogo começar.
     */
    NAO_INICIADO("Não Iniciado", false),

    /**
     * Jogo em curso.
     */
    EM_CURSO("Em Curso", true),

    /**
     * Jogo pausado (para futuras funcionalidades).
     */
    PAUSADO("Pausado", false),

    /**
     * Jogo terminado com vencedor.
     */
    TERMINADO("Terminado", false),

    /**
     * Jogo cancelado/abortado.
     */
    CANCELADO("Cancelado", false);

    private final String descricao;
    private final boolean permiteJogadas;

    EstadoJogo(String descricao, boolean permiteJogadas) {
        this.descricao = descricao;
        this.permiteJogadas = permiteJogadas;
    }

    /**
     * Obtém a descrição do estado.
     *
     * @return Descrição textual do estado
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Verifica se o estado permite jogadas.
     *
     * @return true se jogadas são permitidas neste estado
     */
    public boolean permiteJogadas() {
        return permiteJogadas;
    }

    /**
     * Verifica se é possível transitar para outro estado.
     *
     * @param novoEstado Estado destino
     * @return true se a transição é válida
     */
    public boolean podeTransitarPara(EstadoJogo novoEstado) {
        switch (this) {
            case NAO_INICIADO:
                return novoEstado == EM_CURSO || novoEstado == CANCELADO;
            case EM_CURSO:
                return novoEstado == PAUSADO || novoEstado == TERMINADO || novoEstado == CANCELADO;
            case PAUSADO:
                return novoEstado == EM_CURSO || novoEstado == CANCELADO;
            case TERMINADO:
            case CANCELADO:
                return false; // Estados finais
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return descricao;
    }
}

