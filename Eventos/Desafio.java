package Eventos;

/**
 * Interface base para todos os desafios e obstáculos encontrados nas Divisoes.
 */
public interface Desafio {

    /**
     * Tenta resolver o desafio com um determinado input.
     * @param input O input do jogador (e.g., escolha da alavanca, resposta ao enigma).
     * @return true se o desafio foi resolvido/ultrapassado com sucesso, false caso contrário.
     */
    boolean tentarResolucao(String input);

    /**
     * Retorna se o desafio está completo e a passagem está livre.
     * @return true se o desafio já foi resolvido e a Divisao está livre; false caso contrário.
     */
    boolean estaCompleto();

    /**
     * Retorna o que o jogador deve fazer para tentar resolver o desafio (e.g., a pergunta do enigma).
     */
    String getDescricao();
}