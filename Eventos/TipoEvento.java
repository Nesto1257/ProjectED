package Eventos;

import java.util.Random;

/**
 * Enumeração que define os tipos de eventos aleatórios do jogo.
 * Cada tipo de evento tem uma descrição e um efeito específico no jogo.
 * Tipos de eventos:
 * <ul>
 *   <li>EXTRA_TURN - O jogador ganha uma jogada extra</li>
 *   <li>TROCA_POSICAO - Troca de lugar com outro jogador</li>
 *   <li>RECUAR - O jogador recua para a posição anterior</li>
 *   <li>IMPEDIR_TURNO - O jogador fica impedido de jogar</li>
 *   <li>TROCAR_TODOS - Todos os jogadores trocam de posição</li>
 * </ul>
 *
 * @author Grupo ED
 * @version 1.0
 */
public enum TipoEvento {

    /** O jogador ganha uma jogada extra */
    EXTRA_TURN("Ganhou uma jogada extra!"),

    /** O jogador troca de posição com outro */
    TROCA_POSICAO("Troca de posição com um adversário à escolha!"),

    /** O jogador recua para a posição anterior */
    RECUAR("Avanço travado! Recua para a posição anterior."),

    /** O jogador fica impedido de jogar */
    IMPEDIR_TURNO("Fica impedido de jogar durante 2 turnos."),

    /** Todos os jogadores trocam de posição */
    TROCAR_TODOS("CAOS! Todos os jogadores trocam de posição!");

    /** A descrição do evento */
    private final String descricao;

    /** Gerador de números aleatórios */
    private static final Random random = new Random();

    /**
     * Construtor do enum.
     *
     * @param descricao A descrição do evento
     */
    TipoEvento(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém a descrição do evento.
     *
     * @return A descrição textual do evento
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Seleciona um tipo de evento aleatoriamente.
     *
     * @return Um tipo de evento escolhido aleatoriamente
     */
    public static TipoEvento getAleatorio() {
        TipoEvento[] valores = values();
        return valores[random.nextInt(valores.length)];
    }

    /**
     * Devolve a representação textual do evento.
     *
     * @return A descrição do evento
     */
    @Override
    public String toString() {
        return descricao;
    }
}

