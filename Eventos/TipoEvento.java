package Eventos;

import java.util.Random;

/**
 * Enum que representa os tipos de eventos aleatórios no jogo.
 *
 * Benefícios OOP:
 * - Elimina "magic numbers" (constantes int)
 * - Type safety: impossível usar tipos inválidos
 * - Encapsulação: cada tipo contém a sua descrição
 * - Facilita extensão com novos eventos
 *
 * @author Grupo ED
 * @version 1.0
 */
public enum TipoEvento {
    EXTRA_TURN("Ganhou uma jogada extra!", "🎲"),
    TROCA_POSICAO("Troca de posição com um adversário à escolha!", "🔄"),
    RECUAR("Avanço travado! Recua para a última posição estável.", "⬅️"),
    IMPEDIR_TURNO("Fica impedido de jogar durante 2 turnos.", "⛔"),
    TROCAR_TODOS("CAOS! Todos os jogadores trocam de posição!", "🌀");

    private final String descricao;
    private final String icone;
    private static final Random random = new Random();

    TipoEvento(String descricao, String icone) {
        this.descricao = descricao;
        this.icone = icone;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getIcone() {
        return icone;
    }

    /**
     * Retorna um tipo de evento aleatório.
     *
     * @return Um TipoEvento selecionado aleatoriamente
     */
    public static TipoEvento getAleatorio() {
        TipoEvento[] valores = values();
        return valores[random.nextInt(valores.length)];
    }

    @Override
    public String toString() {
        return icone + " " + descricao;
    }
}

